package coachingmateanalytics.coachingmate.service.serviceImpl;

import coachingmateanalytics.coachingmate.CoachingmateApplication;
import coachingmateanalytics.coachingmate.dao.UserDaoImpl;
import coachingmateanalytics.coachingmate.entity.UserPartner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CoachingmateApplication.class)
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDaoImpl userDao;

    @Test
    void loginSuccess() {
        String username = "user1";
        String password = "user1";
        UserPartner userPartner = userService.loginCheck(username, password);
        assert userPartner != null;
    }
    @Test
    void loginFail() {
        String username = "user1";
        String password = "1";
        UserPartner userPartner = userService.loginCheck(username, password);
        assert userPartner == null;
    }

    @Test
    void getUser() {
        String username = "user1";
        UserPartner userPartner = userService.getUser(username);
        assert userPartner != null;
    }



    @Test
    void getUserByToken() {
        String username = "user1";
        String password = "user1";
        UserPartner userPartner = userService.loginCheck(username, password);
        String token = userPartner.getToken();
        assert userService.getUserByToken(token) != null;
    }

    @Test
    void tokenCheckSuccess() {
        String username = "user1";
        String password = "user1";
        UserPartner userPartner = userService.loginCheck(username, password);
        String token = userPartner.getToken();
        assert userService.tokenCheck(token);
    }
    @Test
    void tokenCheckFail() {
        String username = "user1";
        String password = "user1";
        UserPartner userPartner = userService.loginCheck(username, password);
        String token = userPartner.getToken() + "1";
        assert !userService.tokenCheck(token);
    }

    @Test
    void tokenCheckExpired() throws ParseException {
        String username = "user1";
        String password = "user1";
        UserPartner userPartner = userService.loginCheck(username, password);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        String dateInString = "15-10-2015 10:20:56";

        Date date = sdf.parse(dateInString);
        userPartner.setTokenDate(date);
        userDao.updateUser(userPartner);
        String token = userPartner.getToken();
        assert !userService.tokenCheck(token);
    }

    @Test
    void register() {
        String fullname = "user10087";
        String username = "user10087";
        String password = "user10087";
        String email = "user10087@gmail.com";

        UserPartner user = userService.register(fullname, username, password,email);
        assert user != null;

    }
}