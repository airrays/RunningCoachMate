package coachingmateanalytics.coachingmate.service;


import coachingmateanalytics.coachingmate.entity.UserPartner;

/**
 * @Auther: Saul
 * @Date: 23/9/20 16:21
 * @Description:
 */
public interface UserService {
    UserPartner loginCheck(String username, String password);
    UserPartner register(String fullname, String username, String password, String email);
    UserPartner getUser(String username);
    UserPartner getUserByToken(String token);
    Boolean tokenCheck(String token);
    //TODO
    UserPartner updateUserProfile(UserPartner userPartner);
    UserPartner getUserProfile(String username);
    UserPartner getLastRace(String username);
    UserPartner updateLastRace(UserPartner userPartner);
}
