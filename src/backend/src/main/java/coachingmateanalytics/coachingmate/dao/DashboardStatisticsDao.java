package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.common.utils.Consts;
import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.DashboardStastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class DashboardStatisticsDao {
    @Autowired
    private MongoTemplate mongoTemplate; // inject mongoTemplate

    public DashboardStastic selectDashboardStaticByAccessToken(String accessToken){
        Query query = Query.query(Criteria.where("userAccessToken").is(accessToken)); // query by accessToken
        return mongoTemplate.findOne(query, DashboardStastic.class, Consts.MONGODB_DASHBOARD_STATISTICS_COLLECTION_NAME); // find all activiti

    }

    public DashboardStastic insertDashboardStatic(DashboardStastic dashboardStastic){
        Query query = Query.query(Criteria.where("userAccessToken").is(dashboardStastic.getUserAccessToken()));
        mongoTemplate.remove(query, Consts.MONGODB_DASHBOARD_STATISTICS_COLLECTION_NAME);
        return mongoTemplate.save(dashboardStastic, Consts.MONGODB_DASHBOARD_STATISTICS_COLLECTION_NAME); // find all activiti

    }

}
