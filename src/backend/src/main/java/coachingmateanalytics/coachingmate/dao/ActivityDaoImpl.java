package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.ActivityDetails;
import coachingmateanalytics.coachingmate.entity.Statistic;
import coachingmateanalytics.coachingmate.common.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static coachingmateanalytics.coachingmate.common.utils.DateUtils.getEndOfDay;
import static coachingmateanalytics.coachingmate.common.utils.DateUtils.getStartOfDay;

/**
 * @Date: 24/9/20 10:16
 * @Description: implements mongodb Data Access Object for Activity
 */
@Component
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private MongoTemplate mongoTemplate; // inject mongoTemplate

    @Override
    public void saveActivity(Statistic statistic) {
//        mongoTemplate.save(statistic, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME); // save activity to mongodb
    }

    @Override
    public List<Activity> findAllByAccessToken(String accessToken) {
        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken)); // query by accessToken
        return mongoTemplate.find(query, Activity.class, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME); // find all activities by accessToken
    }

    @Override
    public List<ActivityDetails> selectActivityDetailsByAccessToken(String accessToken) {
        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken));
        return mongoTemplate.find(query, ActivityDetails.class, Consts.MONGODB_ACTIVITY_DETAILS_COLLECTIN_NAME); // find all details by accessToken
    }

    @Override
    public void insertActivity(Activity activity) {
        // check duplicate activity file using summaryId
        Query query = Query.query(Criteria.where("summaryId").is(activity.getSummaryId()));
        if (!mongoTemplate.exists(query, Activity.class, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME)) {
            mongoTemplate.save(activity, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME); // save activity to mongodb
        }
    }

    @Override
    public void insertActivityDetails(ActivityDetails activityDetails) {
        // check duplicate activity_details file using summaryId
        Query query = Query.query(Criteria.where("summaryId").is(activityDetails.getSummaryId()));
        if (!mongoTemplate.exists(query, ActivityDetails.class, Consts.MONGODB_ACTIVITY_DETAILS_COLLECTIN_NAME)) {
            mongoTemplate.save(activityDetails, Consts.MONGODB_ACTIVITY_DETAILS_COLLECTIN_NAME); // save activityDetails to mongodb
        }
    }

    @Override
    public ActivityDetails selectActivityDetailsByActivityId(String activityId) {
        Query query = Query.query(Criteria.where("activityId").is(Long.valueOf(activityId)));
        return mongoTemplate.findOne(query, ActivityDetails.class, Consts.MONGODB_ACTIVITY_DETAILS_COLLECTIN_NAME); // find activityDetails by activityId

    }

    @Override
    public List<Activity> selectActivityDetailsByAccessTokenAndType(String accessToken, String type) {
        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken));
        if(!Objects.equals(type, "All"))
            query.addCriteria(Criteria.where("activityType").is(type));
        query.with(Sort.by(Sort.Direction.DESC, "startTimeInSeconds"));
        return mongoTemplate.find(query, Activity.class,Consts.MONGODB_ACTIVITY_COLLECTIN_NAME);
    }

    @Override
    public List<Activity> selectActivityByAccessTokenAndDate(String accessToken, Date date) {
        Date startTime = getStartOfDay(date);
        Date endTime = getEndOfDay(date);

        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken));
        System.out.println(startTime.getTime()/1000);
        System.out.println(endTime.getTime()/1000);
        query.addCriteria(Criteria.where("startTimeInSeconds").gte(startTime.getTime()/1000).lte(endTime.getTime()/1000));

        return mongoTemplate.find(query, Activity.class, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME);
    }

    @Override
    public Map<String, Object> getStatisticsData(String accessToken) {
        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken));
        List<Activity> activityList = mongoTemplate.find(query, Activity.class, Consts.MONGODB_ACTIVITY_COLLECTIN_NAME);
        Map<String, Object> resultMap = new HashMap<>();



        resultMap.put("totalActivities", activityList.size());
        return resultMap;
    }


}
