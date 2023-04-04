package coachingmateanalytics.coachingmate.service;

import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.ActivityDetails;
import coachingmateanalytics.coachingmate.entity.DashboardStastic;
import coachingmateanalytics.coachingmate.entity.Statistic;

import java.util.List;

/**
 * @Date: 24/9/20 16:03
 * @Description: ActivityService interface for CoachingMate application 
 */
public interface ActivityService {
//    void saveActivity(Statistic activity);
//  List<Activity> findAllByUsername(String username);
    void insertActivity(Activity activity);
    void insertActivityDetail(ActivityDetails activityDetails);
    List<Activity> selectActivityByAccessToken(String accessToken);
    List<ActivityDetails> selectActivityDetailsByAccessToken(String accessToken);
    ActivityDetails selectActivityDetailsByActivityId(String activityId);
    List<Activity> selectActivityByAccessTokenAndType(String accessToken, String type);
    void statisticsActivities(String accessToken);
    DashboardStastic selectDashboardStatisticsByAccessToken(String accessToken);
}
