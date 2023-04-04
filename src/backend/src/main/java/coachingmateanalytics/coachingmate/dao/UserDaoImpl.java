package coachingmateanalytics.coachingmate.dao;


import java.util.List;


import coachingmateanalytics.coachingmate.common.utils.Consts;

import com.mongodb.client.result.UpdateResult;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import coachingmateanalytics.coachingmate.entity.UserPartner;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /***
     * @Date 23:24 22/9/20
     * @Description: test remote mongodb using collection name
     * @param user
     * @return {@link }
     */
    public void saveUser(UserPartner user) {
        mongoTemplate.save(user, Consts.MONGODB_USER_COLLECTIN_NAME); // save user to mongodb 
    }

    public UserPartner findUserByUsername(String username) {
        Query query=new Query(Criteria.where("username").is(username)); // query by username in mongodb 
        UserPartner user =  mongoTemplate.findOne(query , UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // find user by username in mongodb 
        return user;
    }
    
    public UserPartner findUserByEmail(String email) {
        Query query=new Query(Criteria.where("email").is(email)); // query by email in mongodb 
        UserPartner user =  mongoTemplate.findOne(query , UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // find user by email in mongodb 
        return user;
    }


    public int updateUser(UserPartner user) {
        Query query=new Query(Criteria.where("username").is(user.getUsername())); // query by username in mongodb 
        Update update= Update.update("userAccessToken", user.getUserAccessToken()).set("userAccessSecret", user.getUserAccessSecret()); // update userAccessToken and userAccessSecret in mongodb 
        update.set("token", user.getToken()); // update token in mongodb
        update.set("tokenDate", user.getTokenDate());
        UpdateResult result =mongoTemplate.updateMulti(query,update,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // update user in mongodb 
        return (int) result.getMatchedCount(); // return matched count in mongodb 
    }
//    @Override
    public int getMaxUserid() {
    	Query query=new Query(); // query all users in mongodb 
    	query.with(Sort.by(Sort.Direction.DESC, "userId")); // sort users by userId in descending order 
    	List<UserPartner> users = mongoTemplate.find(query, UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // find all users in mongodb 
    	if(users.size()==0) { // if no users in mongodb 
    		return 0; // return 0 
    	}
    	return (int) users.get(0).getUserId(); // return userId of the first user in mongodb 
    }

    @Override
    public void deleteUserByUsername(String username) { // delete user by username in mongodb 
        Query query=new Query(Criteria.where("username").is(username)); // query by username in mongodb 
        mongoTemplate.remove(query,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // delete user by username in mongodb 
    }

    @Override
    public UserPartner selectUserByToken(String token) { // select user by token in mongodb 
        Query query=new Query(Criteria.where("token").is(token)); // query by token in mongodb 
        return mongoTemplate.findOne(query , UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // find user by token in mongodb 
    }

    @Override
    public UserPartner updateUserProfile(UserPartner userPartner){
        Query query=new Query(Criteria.where("username").is(userPartner.getUsername())); // query by username in mongodb
        UserPartner up=mongoTemplate.findOne(query,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME);
        System.out.println("userPartner_0 - UserDaolmpl " + up);
        Update update= new Update(); // update userAccessToken and userAccessSecret in mongodb
        update.set("fullname", userPartner.getFullname()); // update token in mongodb
        update.set("username", userPartner.getUsername());
        update.set("email", userPartner.getEmail());
        update.set("birthday", userPartner.getBirthday());
        update.set("height", userPartner.getHeight());
        update.set("weight", userPartner.getWeight());
        update.set("gender", userPartner.getGender());
        update.set("max_HR", userPartner.getMax_HR());
        update.set("rest_HR", userPartner.getRest_HR());
        update.set("ltHR",userPartner.getLtHR());
        update.set("tp",userPartner.getTp());
        update.set("last_Race_Distance",userPartner.getLast_Race_Distance());
        update.set("last_Race_Time",userPartner.getLast_Race_Time());

        //TODO
        UpdateResult result =mongoTemplate.updateFirst(query,update,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // update user in mongodb
        //return (int) result.getMatchedCount(); // return matched count in mongodb
        System.out.println(result.toString());
        return userPartner;
    }
    @Override
    public UserPartner getLastRaceByUsername(String username){
        Query query=new Query(Criteria.where("username").is(username)); // query by username in mongodb
        UserPartner user =  mongoTemplate.findOne(query , UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME); // find user by username in mongodb
        System.out.println("userPartner_1 - getLastRaceByUsername " + user);
        return user;
    }
    @Override
    public UserPartner updateLastRaceByUsername(UserPartner userPartner){
        Query query=new Query(Criteria.where("username").is(userPartner.getUsername())); // query by username in mongodb
        //UserPartner user=mongoTemplate.findOne(query,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME);
        Update updateDefinition= new Update();
        updateDefinition.set("last_Race_Distance",userPartner.getLast_Race_Distance());
        updateDefinition.set("last_Race_Time",userPartner.getLast_Race_Time());
        //user.setLast_Race_Time(userPartner.getLast_Race_Time());
        UpdateResult result=mongoTemplate.updateFirst(query,updateDefinition,UserPartner.class,Consts.MONGODB_USER_COLLECTIN_NAME);
        System.out.println(result.toString());
        //System.out.println("userPartner_2 - update LastRace " + user);
        return userPartner;
    }
}
