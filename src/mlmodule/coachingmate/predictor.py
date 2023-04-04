from coachingmate.consts import *
from coachingmate.dateconverter import *
from coachingmate.model import RegressionModel
from pymongo import MongoClient
import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


class TPPredictor:
    """
    This class implements a predictor, predicting user's threshold pace i.e. TP.
    The TPPredictor requests activity data for a specified user over a period of time,
    build a RegressionModel to learn and predict the user's TP.

    """

    def __init__(self, user_access_token, user_lthr, *, start_time=None, end_time=None):
        """
        The constructor of TPPredictor class.

        :param user_access_token: Specified user need to predict
        :type user_access_token: str
        :param user_lthr: The lactate threshold heart rate of the user in bpm
        :type user_lthr: int
        :param start_time: The start date of activities that will be used for prediction.
            It should be in a string format '%Y-%m-%d'.
            Optional, if not specified, the default will be set to DURATION_DAYS=90 days before the end_time
        :type start_time: str
        :param end_time: The end date of activities that will be used for prediction.
            It should be in a string format '%Y-%m-%d'.
            Optional, if not specified, it will be set to current local date
        :type end_time: str
        """

        # Set variables
        self.user_access_token = user_access_token
        self.user_lthr = user_lthr
        assert isinstance(user_lthr, int), 'User LTHR shoud be an integer.'
        # Set start time, end time, and check invalid input.
        self.end_time = unix_now() if end_time is None else str2unix(end_time)
        self.start_time = unix_days_before(self.end_time, DURATION_DAYS) if start_time is None else str2unix(start_time)
        assert self.start_time < self.end_time, 'Start time should be less than end time.'

        self.model = RegressionModel(degree=POLY_DEGREE, alpha=RIDGE_ALPHA)
        self.activity_details = None
        self.samples = None

    def run(self):
        """
        Run the predictor.

        :return: A dictionary containing predicted TP and the model scores evaluated by cross validation.
            Also contain a list of heartRate and a list of estimated speed.
        :rtype: dict
        """

        self.request_activity_details()
        self.resampling()
        prediction = self.train_and_predict()
        # self.save_activity_details()
        self.save_samples()
        self.save_plots()
        return prediction

    def request_activity_details(self):
        """
        Request activity details from MongoDB, by user access token, activity type and activity date.

        :return: None
        """

        # Connect to mongodb, query activity details documents.
        client = MongoClient(MONGO_URI)
        db = client[MONGO_DB_NAME]
        collection = db[MONGO_COLLECTION_NAME_ACTIVITY_DETAILS]
        query = dict()
        query['userAccessToken'] = self.user_access_token
        query['summary.startTimeInSeconds'] = {'$gte': self.start_time, '$lte': self.end_time}
        query['summary.activityType'] = {'$regex': ACTIVITY_TYPE}
        print('Mongodb Query: {}'.format(query))
        docs = list(collection.find(query))
        print("Get {} activity details".format(len(docs)))

        # Convert the documents from dictionary format to pandas.DataFrame.
        self.activity_details = list()
        meta = ['summaryId', 'activityId',
                ['summary', 'activityName'],
                ['summary', 'activityType'],
                ['summary', 'startTimeInSeconds'],
                ['summary', 'startTimeOffsetInSeconds'],
                ['summary', 'durationInSeconds'],
                ['summary', 'distanceInMeters'],
                ['summary', 'averageHeartRateInBeatsPerMinute'],
                ['summary', 'averageSpeedInMetersPerSecond'],
                ['summary', 'averagePaceInMinutesPerKilometer'],
                ['summary', 'averageRunCadenceInStepsPerMinute'],
                ['summary', 'maxHeartRateInBeatsPerMinute'],
                ['summary', 'maxSpeedInMetersPerSecond'],
                ['summary', 'maxPaceInMinutesPerKilometer'],
                ['summary', 'maxRunCadenceInStepsPerMinute'],
                ['summary', 'averageSwimCadenceInStrokesPerMinute'],
                ['summary', 'averageBikeCadenceInRoundsPerMinute'],
                ['summary', 'maxBikeCadenceInRoundsPerMinute'],
                ['summary', 'steps'],
                ['summary', 'activeKilocalories'],
                ['summary', 'startingLatitudeInDegree'],
                ['summary', 'startingLongitudeInDegree'],
                ['summary', 'totalElevationGainInMeters'],
                ['summary', 'totalElevationLossInMeters']]
        for doc in docs:
            if doc['samples']:
                self.activity_details.append(pd.json_normalize(doc, record_path='samples', meta=meta))
        print("Get {} activity details with samples".format(len(self.activity_details)))

    def resampling(self):
        """
        Resample instances from the list of activity details.

        :return: None
        """

        if self.activity_details is None:
            print('No activity details. Please run request_activity_details first.')
            return

        df_resampled_lst = []
        for df in self.activity_details:
            summary_id = df.at[0, 'summaryId']
            activity_id = df.at[0, 'activityId']
            activity_name = df.at[0, 'summary.activityName']
            df = df[['startTimeInSeconds', 'timerDurationInSeconds', 'clockDurationInSeconds',
                     'elevationInMeters', 'airTemperatureCelcius', 'heartRate', 'speedMetersPerSecond',
                     'totalDistanceInMeters']]

            valid_points = []
            idx = 0
            while idx < df.index[-1]:
                start_timer = df.at[idx, 'timerDurationInSeconds']
                mask = (df.timerDurationInSeconds >= start_timer) & (df.timerDurationInSeconds < start_timer + 120)
                sub_df = df.iloc[df[mask].index.values]
                end_timer = sub_df.timerDurationInSeconds.max()
                min_speed = sub_df.speedMetersPerSecond.min()
                max_speed = sub_df.speedMetersPerSecond.max()
                min_hr = sub_df.heartRate.min()
                max_hr = sub_df.heartRate.max()
                if (min_speed != 0.0) and (max_speed - min_speed) / min_speed <= 0.05 \
                        and (max_hr - min_hr) < 20 and (end_timer - start_timer) > 60:
                    idx = sub_df.index[-1] + 1
                    valid_points.append(sub_df)
                else:
                    idx += 1

            if valid_points:
                df_resampled = pd.concat(valid_points, ignore_index=True)
                df_resampled['summaryId'] = summary_id
                df_resampled['activityId'] = activity_id
                df_resampled['activityName'] = activity_name
                df_resampled_lst.append(df_resampled)

        if df_resampled_lst:
            self.samples = pd.concat(df_resampled_lst, ignore_index=True)

            # remove rows with heartRate=0
            self.samples.drop(self.samples[self.samples['heartRate'] == 0].index, inplace=True)
            self.samples.reset_index(drop=True, inplace=True)

            # remove heartRate outliers by IQR
            percentile25 = self.samples['heartRate'].quantile(0.25)
            percentile75 = self.samples['heartRate'].quantile(0.75)
            iqr = percentile75 - percentile25
            upper_limit = percentile75 + 1.5 * iqr
            lower_limit = percentile25 - 1.5 * iqr
            print('HeartRate min {} max {} mean {}'.format(self.samples['heartRate'].min(),
                                                           self.samples['heartRate'].max(),
                                                           self.samples['heartRate'].mean()))
            print('HeartRate lower bound {} percentile25 {} percentile75 {} upper bound {}'.format(lower_limit,
                                                                                                   percentile25,
                                                                                                   percentile75,
                                                                                                   upper_limit))
            # self.samples = self.samples[self.samples['heartRate'] <= upper_limit]
            self.samples = self.samples[self.samples['heartRate'] >= lower_limit]

            # remove elevationInMeters outliers by IQR
            percentile25 = self.samples['elevationInMeters'].quantile(0.25)
            percentile75 = self.samples['elevationInMeters'].quantile(0.75)
            iqr = percentile75 - percentile25
            upper_limit = percentile75 + 1.5 * iqr
            lower_limit = percentile25 - 1.5 * iqr
            print('elevationInMeters min {} max {} mean {}'.format(self.samples['elevationInMeters'].min(),
                                                                   self.samples['elevationInMeters'].max(),
                                                                   self.samples['elevationInMeters'].mean()))
            print('elevationInMeters lower bound {} percentile25 {} percentile75 {} upper bound {}'.format(lower_limit,
                                                                                                           percentile25,
                                                                                                           percentile75,
                                                                                                           upper_limit))
            self.samples = self.samples[self.samples['elevationInMeters'] <= upper_limit]
            self.samples = self.samples[self.samples['elevationInMeters'] >= lower_limit]

            # remove airTemperatureCelcius outliers by IQR
            percentile25 = self.samples['airTemperatureCelcius'].quantile(0.25)
            percentile75 = self.samples['airTemperatureCelcius'].quantile(0.75)
            iqr = percentile75 - percentile25
            upper_limit = percentile75 + 1.5 * iqr
            lower_limit = percentile25 - 1.5 * iqr
            print('airTemperatureCelcius min {} max {} mean {}'.format(self.samples['airTemperatureCelcius'].min(),
                                                                       self.samples['airTemperatureCelcius'].max(),
                                                                       self.samples['airTemperatureCelcius'].mean()))
            print('airTemperatureCelcius lower bound {} percentile25 {} percentile75 {} upper bound {}'.format(lower_limit,
                                                                                                           percentile25,
                                                                                                           percentile75,
                                                                                                           upper_limit))
            self.samples = self.samples[self.samples['airTemperatureCelcius'] <= upper_limit]
            self.samples = self.samples[self.samples['airTemperatureCelcius'] >= lower_limit]

    def train_and_predict(self):
        """
        Train the model and predict TP by user's LTHR.

        :return: A dictionary containing predicted TP and the model scores evaluated by cross validation.
            Also contain a list of heartRate and a list of estimated speed.
        :rtype: dict
        """

        if self.samples is None:
            print('Unable to build regression model. No available samples.')
            return None
        X = self.samples[['heartRate', 'airTemperatureCelcius', 'elevationInMeters']].to_numpy()
        y = self.samples['speedMetersPerSecond'].to_numpy()
        scores = self.model.train_and_score(X, y)
        prediction = dict()
        prediction['userAccessToken'] = self.user_access_token
        prediction['startTimeInSeconds'] = self.start_time
        prediction['startTimeInDate'] = unix2localtime(self.start_time)
        prediction['endTimeInSeconds'] = self.end_time
        prediction['endTimeInDate'] = unix2localtime(self.end_time)
        prediction['ltHR'] = self.user_lthr
        prediction['tp_ML'] = 1000 / (60 * self.model.predict_one(self.user_lthr))
        prediction['train_r2_avg'] = np.average(scores['train_r2'])
        prediction['train_mse_avg'] = np.average(scores['train_mse'])
        prediction['test_r2_avg'] = np.average(scores['test_r2'])
        prediction['test_mse_avg'] = np.average(scores['test_mse'])
        prediction['heartRate'] = list(range(self.samples['heartRate'].min(), self.samples['heartRate'].max() + 1, 1))
        prediction['speedMetersPerSecond'] = self.model.predict_multi_by_hr(np.array(prediction['heartRate'])).tolist()
        return prediction

    def save_activity_details(self, path=None):
        """
        Save all activity details as csv files at specified path.

        :param path: The path to store activity details. Optional, default='./activity_details/'
        :type path: str
        :return: None
        """

        if self.activity_details is None or len(self.activity_details) == 0:
            print('No activity details.')
            return

        if path is None:
            path = ACTIVITY_DETAILS_CSV_PATH
        for df in self.activity_details:
            pathname = path + df.at[0, 'summaryId'] + '-' \
                       + unix2str(df.at[0, 'summary.startTimeInSeconds']) + '-' \
                       + df.at[0, 'summary.activityName'] + '.csv'
            while not os.path.exists(pathname):
                try:
                    df.to_csv(pathname)
                    print('Create file', pathname)
                except OSError as e:
                    print(e)
                    os.makedirs(path)
                    print('create directory', path)
                    continue

    def save_samples(self, path=None):
        """
        Save the resampled instances as a csv file at specified path.

        :param path: The path to store samples. Optional, default='./samples/'
        :type path: str
        :return: None
        """

        if path is None:
            path = SAMPLES_CSV_PATH
        if self.samples is not None:
            pathname = path + self.user_access_token + ' from ' + unix2str(self.start_time) + ' to ' + unix2str(
                self.end_time) + '.csv'
            while not os.path.exists(pathname):
                try:
                    self.samples.to_csv(pathname)
                    print('Create file', pathname)
                except OSError as e:
                    print(e)
                    os.makedirs(path)
                    print('create directory', path)
                    continue
        else:
            print('No samples.')

    def save_plots(self, path=None):
        """
        Plots sample points and model predictions, and save the figure as a png file at specified path.
        Only draw the relationship between feature heart rate and the target output speed.
        Temperature and elevation are set to the default values.

        :param path: The path to store samples. Optional, default='./plots/'
        :type path: str
        :return: None
        """

        if path is None:
            path = PLOTS_PNG_PATH
        if self.samples is None:
            print('Unable to build regression model. No available samples.')
            return

        # Scatter plots
        hr = self.samples['heartRate'].to_numpy()
        speed = self.samples['speedMetersPerSecond'].to_numpy()
        # Model prediction plots
        X_hr = np.linspace(hr.min(), hr.max(), num=1000)
        y = self.model.predict_multi_by_hr(X_hr)
        # Plot
        plt.figure()
        plt.plot(X_hr, y, 'k-', label='Prediction')
        plt.scatter(hr, speed, color='b', marker='.', label='All samples')

        # Save the plot figure to specified path
        pathname = path + self.user_access_token + ' from ' + unix2str(self.start_time) + ' to ' + unix2str(
            self.end_time) + '.png'
        while not os.path.exists(pathname):
            try:
                plt.savefig(pathname)
                print('Create file', pathname)
            except OSError as e:
                print(e)
                os.makedirs(path)
                print('create directory', path)
                continue

        # # Scatter plots
        # # Extract samples which have default tempand elev and plot them in red dots, other samples plot in blue dots
        # df_red = self.samples[(self.samples['airTemperatureCelcius'] >= round(MELBOURNE_AVG_TEMP * 0.9))
        #                       & (self.samples['airTemperatureCelcius'] <= round(MELBOURNE_AVG_TEMP * 1.1))
        #                       & (self.samples['elevationInMeters'] >= round(MELBOURNE_AVG_ELEV * 0.9))
        #                       & (self.samples['elevationInMeters'] <= round(MELBOURNE_AVG_ELEV * 1.1))]
        # hr_red = df_red['heartRate'].to_numpy()
        # speed_red = df_red['speedMetersPerSecond'].to_numpy()
        # df_blue = self.samples[(self.samples['airTemperatureCelcius'] < round(MELBOURNE_AVG_TEMP * 0.9))
        #                        | (self.samples['airTemperatureCelcius'] > round(MELBOURNE_AVG_TEMP * 1.1))
        #                        | (self.samples['elevationInMeters'] < round(MELBOURNE_AVG_ELEV * 0.9))
        #                        | (self.samples['elevationInMeters'] > round(MELBOURNE_AVG_ELEV * 1.1))]
        # hr_blue = df_blue['heartRate'].to_numpy()
        # speed_blue = df_blue['speedMetersPerSecond'].to_numpy()
        # # Model prediction plots
        # hr = self.samples['heartRate'].to_numpy()
        # X_hr = np.linspace(hr.min(), hr.max(), num=1000)
        # y = self.model.predict_multi_by_hr(X_hr)
        # # Plot
        # plt.figure()
        # plt.plot(X_hr, y, 'k-', label='Prediction')
        # plt.scatter(hr_red, speed_red, color='r', marker='.', label='Samples with default temp and elev')
        # plt.scatter(hr_blue, speed_blue, color='b', marker='.', label='All samples')
        # plt.show()
