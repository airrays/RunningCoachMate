package coachingmateanalytics.coachingmate.service.serviceImpl;

import coachingmateanalytics.coachingmate.common.enums.ResponseCode;
import coachingmateanalytics.coachingmate.common.exceptions.BusinessException;
import coachingmateanalytics.coachingmate.dao.UserDaoImpl;
import coachingmateanalytics.coachingmate.dao.UserDao;
import coachingmateanalytics.coachingmate.entity.UserPartner;
import coachingmateanalytics.coachingmate.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Date: 24/9/20 00:50
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    

    @Override
    public UserPartner loginCheck(String username, String password) {
        UserPartner userPartner = userDao.findUserByUsername(username);
        if(null == userPartner || !userPartner.getPassword().equals(password)) return null;
        String token = UUID.randomUUID().toString();
        userPartner.setTokenDate(new Date());
        userPartner.setToken(token);
        userDao.updateUser(userPartner);
        return userPartner;
    }
    
    @Override
    public UserPartner getUser(String username) {
        //        if(null == userPartner) return null;
        return userDao.findUserByUsername(username);
    }

    @Override
    public UserPartner getUserByToken(String token) {
        return userDao.selectUserByToken(token);
    }

    @Override
    public Boolean tokenCheck(String token) {
        UserPartner userPartner = userDao.selectUserByToken(token);
        if(userPartner == null)
            return false;
        Date createDate = userPartner.getTokenDate();
        Date currDate = new Date(); //取时间

        Calendar createCalendar = new GregorianCalendar();
        createCalendar.setTime(createDate);
        createCalendar.add(createCalendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        createDate = createCalendar.getTime(); //这个时间就是日期往后推一天的结果
        if(createDate.compareTo(currDate) < 0) {
            return false;
        }
        userPartner.setTokenDate(currDate);
        userDao.updateUser(userPartner);
        return true;
    }

    @Override
    public UserPartner register(String fullname, String username, String password, String email) {
        UserPartner userPartner = userDao.findUserByUsername(username);
        UserPartner userPartner1 = userDao.findUserByEmail(email);
        int maxId=userDao.getMaxUserid();
        if(userPartner != null || userPartner1!= null) return null;
        Random random = new Random();
        UserPartner newUserPartner = new UserPartner();
        newUserPartner.setFullname(fullname);
        newUserPartner.setUsername(username);
        newUserPartner.setPassword(password);
        newUserPartner.setEmail(email);
        newUserPartner.setUserId(maxId+1);	
        userDao.saveUser(newUserPartner);
        return newUserPartner;
    }

    //TODO
    @Override
    public UserPartner updateUserProfile(UserPartner userPartner){
//        UserPartner newUserPartner=new UserPartner();
        return userDao.updateUserProfile(userPartner);
    }
    @Override
    public UserPartner getUserProfile(String username){
        return userDao.findUserByUsername(username);
    }

    @Override
    public UserPartner getLastRace(String username){
        return userDao.getLastRaceByUsername(username);
    }

    @Override
    public UserPartner updateLastRace(UserPartner userPartner){
        return userDao.updateLastRaceByUsername(userPartner);
    }
}
