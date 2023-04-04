package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.AppTest;
import coachingmateanalytics.coachingmate.entity.UserPartner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class UserDaoTest extends AppTest {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    private UserDao userDao;

    @Test
    public void testSaveUser() throws Exception {
        UserPartner user = new UserPartner();
        user.setUsername("Jack");
        user.setUserId(898329389);
        user.setPassword("8888888");
        userDao.saveUser(user);
    }



    @Test
    public void findUserByUsername(){
        UserPartner user= userDao.findUserByUsername("Saul");
        logger.info("find user is : "+ user);
    }

    @Test
    public void findUserByUsername2(){
        UserPartner user= userDao.findUserByUsername("user1");
        logger.info("find user is : "+ user);
    }


    @Test
    public void updateUser(){
        UserPartner user = new UserPartner();
        user.setUsername("Jack");
        user.setUserId(898329389);
        user.setPassword("8888888");
        user.setUserAccessToken("3f3d5af3-7847-413e-a9fe-47aeffb6de44");
        user.setUserAccessSecret("secret");
        int i = userDao.updateUser(user);
        logger.info("i = " + i);
        UserPartner jack = userDao.findUserByUsername("Jack");
        logger.info("updated user is : "+ jack);
    }


    @Test
    public void deleteUserByUsername(){
        userDao.deleteUserByUsername("Jack");
    }



}
