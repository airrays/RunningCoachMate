package coachingmateanalytics.coachingmate.dao;


import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.ActivityDetails;
import coachingmateanalytics.coachingmate.entity.Statistic;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Date: 24/9/20 10:14
 * @Description:
 * implements mongodb Data Access Object for Activity and ActivityDetails (ActivityDetails is a sub-collection of Activity) in mongodb database for CoachingMate application 
 */
public interface ActivityDao { // activity data access object
    void saveActivity(Statistic activity);
    List<Activity> findAllByAccessToken(String accessToken);
    List<ActivityDetails> selectActivityDetailsByAccessToken(String accessToken);
    void insertActivity(Activity activity);
    void insertActivityDetails(ActivityDetails activityDetails);
    ActivityDetails selectActivityDetailsByActivityId(String activityId);

    List<Activity> selectActivityDetailsByAccessTokenAndType(String accessToken, String type);
    List<Activity> selectActivityByAccessTokenAndDate(String accessToken, Date date);
    Map<String, Object> getStatisticsData(String accessToken);
}
