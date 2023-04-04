# Constants

# Regression model parameters
POLY_DEGREE = 2
RIDGE_ALPHA = 0.2  # Regression model default temperature and elevation
MELBOURNE_AVG_TEMP = 16
MELBOURNE_AVG_ELEV = 31

# Predictor parameters
# Mongo db consts
MONGO_URI = 'mongodb+srv://wo_bluering:wo_bluering@cluster0.ql33txf.mongodb.net/coachingmatedb'
MONGO_DB_NAME = 'coachingmatedb'
MONGO_COLLECTION_NAME_ACTIVITY_DETAILS = 'activity_details'
MONGO_COLLECTION_NAME_USER = 'user'
MONGO_COLLECTION_NAME_PREDICTION_HISTORY = 'prediction_history'
# Default duration between start-time and end-time to retrieve data
DURATION_DAYS = 90
ACTIVITY_TYPE = 'RUNNING'
# Default path to store source data
ACTIVITY_DETAILS_CSV_PATH = './activity_details/'
SAMPLES_CSV_PATH = './samples/'
PLOTS_PNG_PATH = './plots/'



