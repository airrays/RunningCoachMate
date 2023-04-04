package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.entity.RequestToken;
import coachingmateanalytics.coachingmate.entity.UserPartner;
import coachingmateanalytics.coachingmate.common.utils.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @Date: 9/9/20 15:40
 * @Description:
 * implements Data Access Object for RequestToken
 */
@Component
public class TokenDao {
    private static final Logger logger = LoggerFactory.getLogger(TokenDao.class); // logger for TokenDao

    @Autowired
    private MongoTemplate mongoTemplate; // inject mongoTemplate

    // It stores request token and secret in the requestToken Repo
    public RequestToken saveRequestToken(String username, long userid, String token, String secret, String garminstatus) {
        RequestToken reqToken = new RequestToken(username,userid, token,secret, "Not Connected"); // create new request token 
        try { // try to save request token
            mongoTemplate.save(reqToken, Consts.MONGODB_TOKEN_COLLECTIN_NAME); // save request token to mongodb in requestToken collection 
            return reqToken;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public RequestToken findByToken(String reqToken){
        Query query = Query.query(Criteria.where("token").is(reqToken)); // query by token 
        RequestToken token = mongoTemplate.findOne(query, RequestToken.class, Consts.MONGODB_TOKEN_COLLECTIN_NAME); // find request token by token 
        return token;
    }


    // It stores user access token with the user name.
    public void saveAccessToken(String token, String secret, String username) {
        Query query = Query.query(Criteria.where("username").is(username)); // query by username 
        Update update = Update.update("userAccessToken", token).set("userAccessSecret", secret); // update userAccessToken and userAccessSecret in the userPartner collection 
        mongoTemplate.updateMulti(query, update, UserPartner.class, Consts.MONGODB_USER_COLLECTIN_NAME); // update userPartner collection 
    }

}


