package coachingmateanalytics.coachingmate.service.serviceImpl;

import coachingmateanalytics.coachingmate.CoachingmateApplication;
import coachingmateanalytics.coachingmate.dao.ActivityDaoImpl;
import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.ActivityDetails;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest(classes = CoachingmateApplication.class)
class ActivityServiceImplTest {

    @Autowired
    ActivityServiceImpl activityService;

    @Test
    void selectActivityByAccessToken() {

        String accessToken = "8b9bc25c-01db-4296-a1ef-f384b85ff226";
        List<Activity> activityList = activityService.selectActivityByAccessToken(accessToken);
        System.out.println(activityList);
    }

    @Test
    void selectActivityDetailsByAccessToken() {

        String accessToken = "8b9bc25c-01db-4296-a1ef-f384b85ff226";
        List<ActivityDetails> activityDetailsList = activityService.selectActivityDetailsByAccessToken(accessToken);
        System.out.println(activityDetailsList);
    }

    @Test
    void selectActivityDetailsByActivityId() {
        String activityId = "8817870209";
        ActivityDetails activityDetails = activityService.selectActivityDetailsByActivityId(activityId);
        assert activityDetails != null;
    }

    @Test
    void insertActivity() {
        Activity activity = new Activity();
        activity.setActivityId(10000000);
        activityService.insertActivity(activity);

    }

    @Test
    void insertActivityDetail() {
        ActivityDetails activityDetails = new ActivityDetails();
        activityDetails.setActivityId(10000000);
        activityService.insertActivityDetail(activityDetails);
    }


    @Test
    public void statisticsActivities(){
        String accessToken = "366f91a6-0279-40bc-866e-3fdc92a8f180";
        activityService.statisticsActivities(accessToken);
    }
}