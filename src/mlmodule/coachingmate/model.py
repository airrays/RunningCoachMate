import numpy as np
from numpy import ndarray
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import Ridge
from sklearn.pipeline import Pipeline
from sklearn.metrics import r2_score
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import cross_validate
from sklearn.model_selection import KFold
from coachingmate.consts import *


class RegressionModel:
    """
    This class implements a polynomial regression model.

    """

    def __init__(self, *, degree=POLY_DEGREE, alpha=RIDGE_ALPHA):
        """
        The constructor of RegressionModel class.

        :param degree: Specify the maximal degree of the polynomial features, default: POLY_DEGREE=2
        :type degree: int
        :param alpha: Regularization parameter of the regression estimator, alpha must be a non-negative float, default: RIDGE_ALPHA=0.2
        :type alpha: float
        """

        assert degree > 0, 'Degree must be a positive integer'
        assert alpha >= 0, 'Alpha must be a non-negative float'
        self.model = Pipeline([('poly', PolynomialFeatures(degree=int(degree))),
                               ('regression', Ridge(alpha=alpha, fit_intercept=False))])
        # set average temperature and elevation in Melbourne as default
        self.default_temp = MELBOURNE_AVG_TEMP
        self.default_elev = MELBOURNE_AVG_ELEV
        self.avg_temp = 0
        self.avg_elev = 0
        self.scores = dict()

    def train_and_score(self, X, y):
        """
        Fit data to train the model, evaluating R square score and mean squared error by cross-validation.

        :param X: The data to fit, array-like of shape (n_samples, n_features),
            features should be in the order of [hr, temperature, elevation]
        :type X: ndarray
        :param y: The target output, array-like of shape (n_samples,)
        :type y: ndarray
        :return: A dict of arrays containing the score arrays for each scorer.
            Keys are 'train_r2', 'train_mse', 'test_r2', 'test_mse'
        :rtype: dict
        """

        self.avg_temp = np.mean(X[:, 1])
        self.avg_elev = np.mean(X[:, -1])
        # self.default_temp = np.mean(X[:, 1])
        # self.default_elev = np.mean(X[:, -1])
        self.model.fit(X, y)
        # print(self.model.named_steps['regression'].coef_)
        scores = cross_validate(self.model, X, y, cv=KFold(n_splits=5, shuffle=True),
                                scoring=('r2', 'neg_mean_squared_error'), return_train_score=True)
        self.scores['train_r2'] = scores['train_r2']
        self.scores['test_r2'] = scores['test_r2']
        self.scores['train_mse'] = scores['train_neg_mean_squared_error']
        self.scores['test_mse'] = scores['test_neg_mean_squared_error']
        return self.scores

    def get_scores(self):
        """
        Get R square score and mean squared error of the model

        :return: A dict of arrays containing the score arrays for each scorer.
            Keys are 'train_r2', 'train_mse', 'test_r2', 'test_mse'
        :rtype: dict
        """

        return self.scores

    def predict_one(self, hr, *, temp=None, elev=None):
        """
        Predict speed of a single instance using specified heart rate, temperature and elevation

        :param hr: Heart rate in bpm
        :type hr: int
        :param temp: Air temperature in celcius. Optional, if not specified, it will be set to default value
        :type temp: float
        :param elev: Elevation in metres. Optional, if not specified, it will be set to default value
        :type elev: float
        :return: Speed in metres per second
        :rtype: float
        """

        if temp is None:
            temp = self.default_temp
        if elev is None:
            elev = self.default_temp
        X = np.array([hr, temp, elev]).reshape(1, -1)
        return self.model.predict(X)[0]

    def predict_multi_by_hr(self, hr_arr):
        """
        Predict multiple instances using specified heart rates.
        The temperature and elevation will be set to the default values

        :param hr_arr: An 1d array containing heart rates, shape (n_samples,)
        :type hr_arr: ndarray
        :return: An 1d array containing predicted speed, shape (n_samples,)
        :rtype: ndarray
        """

        hr_arr = hr_arr.reshape(-1, 1)
        num = hr_arr.shape[0]
        X = np.concatenate([hr_arr, np.array([self.default_temp, self.default_elev] * num).reshape(-1, 2)], axis=1)
        return self.model.predict(X)

    def predict_multi_by_hr_temp(self, hr_temp_arr):
        """
        Predict multiple instances using specified heart rates and temperatures.
        The elevation will be set to the default value

        :param hr_temp_arr: An 2d array containing heart rates and temperatures, shape (n_samples, 2),
            features should be in the order of [hr, temperature]
        :type hr_temp_arr: ndarray
        :return: An 1d array containing predicted speed, of shape (n_samples,)
        :rtype: ndarray
        """

        hr_temp_arr = hr_temp_arr.reshape(-1, 2)
        num = hr_temp_arr.shape[0]
        X = np.concatenate([hr_temp_arr, np.array([self.default_elev] * num).reshape(-1, 1)], axis=1)
        return self.model.predict(X)

    def predict_multi_by_hr_elev(self, hr_elev_arr):
        """
        Predict multiple instances using specified heart rates and elevations.
        The temperature will be set to the default value

        :param hr_elev_arr: An 2d array containing heart rates and elevations, shape (n_samples, 2),
            features should be in the order of [hr, elevation]
        :type hr_elev_arr: ndarray
        :return: An 1d array containing predicted speed, of shape (n_samples,)
        :rtype: ndarray
        """

        hr_elev_arr = hr_elev_arr.reshape(-1, 2)
        hr_arr = hr_elev_arr[:, 0].reshape(-1, 1)
        elev_arr = hr_elev_arr[:, -1].reshape(-1, 1)
        num = hr_elev_arr.shape[0]
        X = np.concatenate([hr_arr, np.array([self.default_temp] * num).reshape(-1, 1), elev_arr], axis=1)
        return self.model.predict(X)

    def predict_multi_by_hr_temp_elev(self, hr_temp_elev_arr):
        """
        Predict multiple instances using specified heart rates, temperatures and elevations.

        :param hr_temp_elev_arr: An 2d array containing heart rates, temperatures and elevations of shape (n_samples, 3),
            features should be in the order of [hr, temperature, elevation]
        :type hr_temp_elev_arr: ndarray
        :return: An 1d array containing predicted speed of shape (n_samples,)
        :rtype: ndarray
        """

        X = hr_temp_elev_arr.reshape(-1, 3)
        return self.model.predict(X)
