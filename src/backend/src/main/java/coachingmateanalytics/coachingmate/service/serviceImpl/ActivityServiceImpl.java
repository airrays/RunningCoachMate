package coachingmateanalytics.coachingmate.service.serviceImpl;

import coachingmateanalytics.coachingmate.dao.ActivityDao;
import coachingmateanalytics.coachingmate.dao.DashboardStatisticsDao;
import coachingmateanalytics.coachingmate.dao.UserDao;
import coachingmateanalytics.coachingmate.entity.*;
import coachingmateanalytics.coachingmate.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Date: 24/9/20 16:05
 * @Description: activity service impl implements activity service interface and
 * implements methods in activity service interface
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    UserDao userDao;

    @Autowired
    DashboardStatisticsDao dashboardStatisticsDao;
//    @Override
//    public void saveActivity(Statistic activity) {
//        activityDao.saveActivity(activity);
//    }

//    @Override
//    public List<Activity> findAllByAccessToken(String username) {
//        UserPartner user = userDao.findUserByUsername(username);
//        if(user == null)
//            throw new BusinessException(ResponseCode.USER_IS_NOT_EXISTED);
//        return activityDao.findAllByAccessToken(user.getUserAccessToken());
//    }

    @Override
    public List<Activity> selectActivityByAccessToken(String accessToken) {
        return activityDao.findAllByAccessToken(accessToken);
    }

    @Override
    public List<ActivityDetails> selectActivityDetailsByAccessToken(String accessToken) {
        return activityDao.selectActivityDetailsByAccessToken(accessToken);
    }

    @Override
    public ActivityDetails selectActivityDetailsByActivityId(String activityId) {
        return activityDao.selectActivityDetailsByActivityId(activityId);
    }

    @Override
    public List<Activity> selectActivityByAccessTokenAndType(String accessToken, String type) {
        return activityDao.selectActivityDetailsByAccessTokenAndType(accessToken, type);
    }

    @Override
    public void insertActivity(Activity activity) {
        if (activity != null)
            activityDao.insertActivity(activity);
    }

    @Override
    public void insertActivityDetail(ActivityDetails activityDetails) {
        if (activityDetails != null)
            activityDao.insertActivityDetails(activityDetails);
    }

    @Override
    public void statisticsActivities(String accessToken) {
        DashboardStastic dashboardStastic = new DashboardStastic();
        dashboardStastic.setUserAccessToken(accessToken);
        List<Activity> activityList = activityDao.findAllByAccessToken(accessToken);
        dashboardStastic.setTtlActivityTimes(activityList.size());
        int runningCount = 0;
        int swimmingCount = 0;
        int riddingCount = 0;
        double runningTime = 0;
        double swimmingTime = 0;
        double riddingTime = 0;
        int ttlRunningHeartRate = 0;
        int ttlSwimmingHeartRate = 0;
        int ttlRiddingHeartRate = 0;
        int maxRunningHeartRate = 0;
        int maxSwimmingHeartRate = 0;
        int maxRiddingHeartRate = 0;
        double ttlRunningDistance = 0;
        double ttlRiddingDistance = 0;
        double ttlSwimmingDistance = 0;
        int ttlRunningCalories = 0;
        int ttlSwimmingCalories = 0;
        int ttlRiddingCalories = 0;
        double peakRunningSpeed = 0.0;
        double peakSwimmingSpeed = 0.0;
        double peakRiddingSpeed = 0.0;

        DashboardStastic.RadarActivity runningRadarActivity = dashboardStastic.new RadarActivity();
        DashboardStastic.RadarActivity riddingRadarActivity = dashboardStastic.new RadarActivity();
        DashboardStastic.RadarActivity swimmingRadarActivity = dashboardStastic.new RadarActivity();

        for (Activity activity : activityList) {
            long activityId = activity.getActivityId();
            ActivityDetails activityDetails = activityDao.selectActivityDetailsByActivityId(Long.toString(activityId));
            switch (activity.getActivityType()) {
                case "RUNNING":
                    runningCount++;
                    runningTime += activity.getDurationInSeconds() / 60;
                    ttlRunningHeartRate += (activity.getAverageHeartRateInBeatsPerMinute() *
                            activity.getDurationInSeconds()) / 60;
                    if (maxRunningHeartRate < activity.getMaxHeartRateInBeatsPerMinute())
                        maxRunningHeartRate = activity.getMaxHeartRateInBeatsPerMinute();
                    ttlRunningDistance += activity.getDistanceInMeters();
                    ttlRunningCalories += activity.getActiveKilocalories();
                    if (peakRunningSpeed < activity.getMaxSpeedInMetersPerSecond())
                        peakRunningSpeed = activity.getMaxSpeedInMetersPerSecond();
                    break;
                case "OPEN_WATER_SWIMMING":
                    swimmingCount++;
                    swimmingTime += activity.getDurationInSeconds() / 60;
                    ttlSwimmingHeartRate += (activity.getAverageHeartRateInBeatsPerMinute() *
                            activity.getDurationInSeconds()) / 60;
                    if (maxSwimmingHeartRate < activity.getMaxHeartRateInBeatsPerMinute())
                        maxSwimmingHeartRate = activity.getMaxHeartRateInBeatsPerMinute();
                    ttlSwimmingDistance += activity.getDistanceInMeters();
                    ttlSwimmingCalories += activity.getActiveKilocalories();
                    if (peakSwimmingSpeed < activity.getMaxSpeedInMetersPerSecond())
                        peakSwimmingSpeed = activity.getMaxSpeedInMetersPerSecond();
                    break;
                default:
                    riddingCount++;
                    riddingTime += activity.getDurationInSeconds() / 60;
                    ttlRiddingHeartRate += (activity.getAverageHeartRateInBeatsPerMinute() *
                            activity.getDurationInSeconds()) / 60;
                    if (maxRiddingHeartRate < activity.getMaxHeartRateInBeatsPerMinute())
                        maxRiddingHeartRate = activity.getMaxHeartRateInBeatsPerMinute();
                    ttlRiddingDistance += activity.getDistanceInMeters();
                    ttlRiddingCalories += activity.getActiveKilocalories();
                    if (peakRiddingSpeed < activity.getMaxSpeedInMetersPerSecond())
                        peakSwimmingSpeed = activity.getMaxSpeedInMetersPerSecond();
                    break;
            }

        }
        if (runningTime == 0) {
            runningRadarActivity.setAvgHeartRate(0);
            runningRadarActivity.setAvgSpeed(0);
        } else {
            runningRadarActivity.setAvgHeartRate((int) (ttlRunningHeartRate / runningTime));
            runningRadarActivity.setAvgSpeed(ttlRunningDistance / runningTime);
        }

        runningRadarActivity.setTime(runningTime);
        runningRadarActivity.setDistance(ttlRunningDistance);

        runningRadarActivity.setCalories(ttlRunningCalories);
        runningRadarActivity.setPeakSpeed(peakRunningSpeed);

        if (riddingTime == 0) {
            riddingRadarActivity.setAvgHeartRate(0);
            riddingRadarActivity.setAvgSpeed(0);
        } else {
            riddingRadarActivity.setAvgHeartRate((int) (ttlRiddingHeartRate / riddingTime));
            riddingRadarActivity.setAvgSpeed(ttlRiddingDistance / riddingTime);
        }
        riddingRadarActivity.setTime(riddingTime);
        riddingRadarActivity.setDistance(ttlRiddingDistance);
        riddingRadarActivity.setCalories(ttlRiddingCalories);
        riddingRadarActivity.setPeakSpeed(peakRiddingSpeed);


        if (swimmingTime == 0) {
            swimmingRadarActivity.setAvgHeartRate(0);
            swimmingRadarActivity.setAvgSpeed(0);
        } else {

            swimmingRadarActivity.setAvgHeartRate((int) (ttlSwimmingHeartRate / swimmingTime));
            swimmingRadarActivity.setAvgSpeed(ttlSwimmingDistance / swimmingTime);
        }
        swimmingRadarActivity.setTime(swimmingTime);
        swimmingRadarActivity.setDistance(ttlSwimmingDistance);
        swimmingRadarActivity.setCalories(ttlSwimmingCalories);
        swimmingRadarActivity.setPeakSpeed(peakSwimmingSpeed);
        dashboardStastic.setTtlRunningTimes(runningCount);
        dashboardStastic.setTtlSwimmingTimes(swimmingCount);
        dashboardStastic.setTtlRiddingTimes(riddingCount);
        Map<String, DashboardStastic.RadarActivity> radarMap = new HashMap<>();
        radarMap.put("RUNNING", runningRadarActivity);
        radarMap.put("OPEN_WATER_SWIMMING", swimmingRadarActivity);
        radarMap.put("ROAD_BIKING", riddingRadarActivity);
        dashboardStastic.setRadarActivities(radarMap);
        dashboardStastic.setTtlActivityTime(runningTime + swimmingTime + riddingTime);
        dashboardStastic.setTtlRunningTime(runningTime);
        dashboardStastic.setTtlSwimmingTime(swimmingTime);
        dashboardStastic.setTtlRiddingTime(riddingTime);


        int[] allActualTimeList = {0, 0, 0, 0, 0, 0, 0};
        int[] runningActualTimeList = {0, 0, 0, 0, 0, 0, 0};
        int[] riddingActualTimeList = {0, 0, 0, 0, 0, 0, 0};
        int[] swimmingActualTimeList = {0, 0, 0, 0, 0, 0, 0};
        List<double[]> heartRateZoneList = new ArrayList<>();
        long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
        Date today = new Date();
        for (int i = 0; i < 7; i++) {
            Date date = new Date(today.getTime() - i * MILLIS_IN_A_DAY);
            double[] heartRateZone = {0.0, 0.0, 0.0, 0.0, 0.0};
            List<Activity> activityList1 = activityDao.selectActivityByAccessTokenAndDate(accessToken, date);
            int runningActualTime = 0;
            int riddingActualTime = 0;
            int swimmingActualTime = 0;

            for (Activity activity1 : activityList1) {
                switch (activity1.getActivityType()) {
                    case "RUNNING":
                        runningActualTime += activity1.getDurationInSeconds();
                        break;
                    case "OPEN_WATER_SWIMMING":
                        swimmingActualTime += activity1.getDurationInSeconds();
                        break;
                    default:
                        riddingActualTime += activity1.getDurationInSeconds();
                        break;
                }
                long activityId1 = activity1.getActivityId();
                int durationInSeconds = activity1.getDurationInSeconds();

                ActivityDetails activityDetails1 = activityDao.selectActivityDetailsByActivityId(String.valueOf(activityId1));
                if (activityDetails1 != null) {
                    List<Samples> samplesList = activityDetails1.getSamples();
                    double unit = ((double) durationInSeconds / (double) samplesList.size()) / (double) 60;
                    for (Samples samples : samplesList) {
                        int heartRate = samples.getHeartRate();
                        if (heartRate >= 160) {
                            heartRateZone[4] += unit;
                        } else if (heartRate < 160 && heartRate >= 142) {
                            heartRateZone[3] += unit;
                        } else if (heartRate < 142 && heartRate >= 125) {
                            heartRateZone[2] += unit;
                        } else if (heartRate < 125 && heartRate >= 107) {
                            heartRateZone[1] += unit;
                        } else if (heartRate < 107 && heartRate >= 89) {
                            heartRateZone[0] += unit;
                        }
                    }
                }

            }
            runningActualTimeList[6 - i] = runningActualTime/60;
            riddingActualTimeList[6 - i] = riddingActualTime/60;
            swimmingActualTimeList[6 - i] = swimmingActualTime/60;
            allActualTimeList[6 - i] = (runningActualTime + riddingActualTime + swimmingActualTime)/60;
            heartRateZoneList.add(heartRateZone);
        }
        DashboardStastic.ActivityTimeChart allActivityTimeChart = dashboardStastic.new ActivityTimeChart();
        DashboardStastic.ActivityTimeChart runningTimeChart = dashboardStastic.new ActivityTimeChart();
        DashboardStastic.ActivityTimeChart swimmingTimeChart = dashboardStastic.new ActivityTimeChart();
        DashboardStastic.ActivityTimeChart riddingTimeChart = dashboardStastic.new ActivityTimeChart();

        allActivityTimeChart.setActual(allActualTimeList);
        runningTimeChart.setActual(runningActualTimeList);
        swimmingTimeChart.setActual(swimmingActualTimeList);
        riddingTimeChart.setActual(riddingActualTimeList);
        int[] excepted = {90, 90, 90, 90, 90, 90, 90};
        allActivityTimeChart.setExcept(excepted);
        runningTimeChart.setExcept(excepted);
        swimmingTimeChart.setExcept(excepted);
        riddingTimeChart.setExcept(excepted);
        Map<String, DashboardStastic.ActivityTimeChart> map1 = new HashMap<>();
        map1.put("AllTime", allActivityTimeChart);
        map1.put("RunningTime", runningTimeChart);
        map1.put("SwimmingTime", swimmingTimeChart);
        map1.put("RiddingTime", riddingTimeChart);
        dashboardStastic.setActivityTimeChartMap(map1);
        dashboardStastic.setHearRateZones(heartRateZoneList);
        dashboardStatisticsDao.insertDashboardStatic(dashboardStastic);

    }

    @Override
    public DashboardStastic selectDashboardStatisticsByAccessToken(String accessToken) {
        return dashboardStatisticsDao.selectDashboardStaticByAccessToken(accessToken);
    }

}
