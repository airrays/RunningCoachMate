package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.AppTest;
import coachingmateanalytics.coachingmate.CoachingmateApplication;
import coachingmateanalytics.coachingmate.entity.DashboardStastic;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


/**
 * @Date: 24/9/20 12:04
 * @Description:
 */

public class ActivityDaoImplTest extends  AppTest{
    private static final Logger logger = LoggerFactory.getLogger(ActivityDaoImplTest.class);

    @Autowired
    private ActivityDao activityDao;

    @Test
    public void saveActivity() {
//        Statistic activity = new Activity("3f3d5af3-7847-413e-a9fe-47aeffb6de44", "testData2");
//        activityDao.saveActivity(activity);
    }

    @Test
    public void findAllByAccessToken() {
//        List<Activity> allByAccessToken = activityDao.findAllByAccessToken("3f3d5af3-7847-413e-a9fe-47aeffb6de44");
//        int i = 1;
//        for (Activity activity : allByAccessToken) {
//            logger.info(activity.getAccessToken() + i + " : " + activity.getData());
//        }

//        logger.info("total count : " + allByAccessToken.size());
    }

    @Test
    public void selectActivityByAccessTokenAndDate() {
        System.out.println(activityDao.selectActivityByAccessTokenAndDate("4f527800-61ef-4ab6-8a2b-3fc1fdefbc40",  new Date()));
    }

}