package coachingmateanalytics.coachingmate.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardStastic {
    String userAccessToken;
    int ttlActivityTimes;
    int ttlRunningTimes;
    int ttlRiddingTimes;
    int ttlSwimmingTimes;
    double ttlActivityTime;
    double ttlRunningTime;
    double ttlRiddingTime;
    double ttlSwimmingTime;
    int[] allActivityTime;
    int[] runningActivityTime;
    int[] swimmingActivityTime;
    int[] riddingActivityTime;
//    RadarActivity[] radarActivities;
    Map<String, ActivityTimeChart> activityTimeChartMap;
    Map<String, RadarActivity> radarActivities;
    List<double[]> hearRateZones;


    @Data
    public class ActivityTimeChart{
        int[] actual;
        int[] except;
    }

    @Data
    public class RadarActivity{
        double time;
        int calories;
        double distance;
        double avgSpeed;
        double peakSpeed;
        int avgHeartRate;

    }
}
