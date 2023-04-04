from coachingmate.consts import *
from coachingmate.predictor import TPPredictor
from coachingmate.dateconverter import *
from coachingmate.model import RegressionModel
from pymongo import MongoClient


def run_ml_module(end_time):
    # user access token
    # 540a644b-ad6d-4226-915d-3f67a5e95dd7
    # 9c177184-c23b-4d53-9835-9436077ef72d
    # 20cbef0c-7e36-4907-98f7-a09dd2eecffb

    # Connect to mongodb, query all users
    client = MongoClient(MONGO_URI)
    db = client[MONGO_DB_NAME]
    user_coll = db[MONGO_COLLECTION_NAME_USER]
    prediction_coll = db[MONGO_COLLECTION_NAME_PREDICTION_HISTORY]

    users = list(user_coll.find())
    for user in users:
        if user.get('userAccessToken') and (user.get('ltHR') or user.get('max_HR')):
            if user.get('ltHR'):
                user_ltHR = int(float(user.get('ltHR')))
            else:
                user_ltHR = int(float(user.get('max_HR')) * 0.9)
            print('userId: {}, userAccessToken: {}, ltHR: {}, max_HR: {}, user_ltHR: {}'.format(user.get('userId'),
                                                                                                user.get('userAccessToken'),
                                                                                                user.get('ltHR'),
                                                                                                user.get('max_HR'),
                                                                                                user_ltHR))
            # Predict user TP
            tp_predictor = TPPredictor(user.get('userAccessToken'), user_ltHR, end_time=end_time)
            prediction = tp_predictor.run()
            # print(prediction)
            if prediction is not None:
                # Insert this prediction into prediction_history collection
                prediction['userId'] = user.get('userId')
                prediction_coll.insert_one(prediction)
                print('prediction inserted into mongodb')
                # Update the predicted TP in user collection
                user_coll.update_one({'userId': user.get('userId')},
                                     {'$set': {'tp_ML': str(prediction['tp_ML']), 'test_r2_avg': str(prediction['test_r2_avg'])}})
                print('Update tp_ML in user collection, tp_ML {}, test_r2_avg {}'.format(prediction['tp_ML'],
                                                                                         prediction['test_r2_avg']))


if __name__ == '__main__':
    # Run mlmodule to predict TP for each userId every Monday
    while True:
        localtime = datetime.now()
        if localtime.isoweekday() == 1:
            print('Time log: current local time,', localtime, 'Day of Week', localtime.isoweekday())
            run_ml_module(localtime2str(localtime))
        # sleep 1 day
        time.sleep(86400)


    # # run historical data
    # start_loop_unix = unix_days_before(unix_now(), 100)
    # while start_loop_unix < unix_now():
    #     localtime = unix2localtime(start_loop_unix)
    #     if localtime.isoweekday() == 1:
    #         print('Time log: current local time,', localtime, 'Day of Week', localtime.isoweekday())
    #         run_ml_module(localtime2str(localtime))
    #     start_loop_unix = start_loop_unix + 86400

