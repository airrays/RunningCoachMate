package coachingmateanalytics.coachingmate.dao;


import coachingmateanalytics.coachingmate.entity.UserPartner;

/**
 * @Discription: implements mongodb Data Access Object for UserPartner in mongodb database for CoachingMate application 
 */
public interface UserDao  {
	int getMaxUserid();
     void saveUser(UserPartner user);
     UserPartner findUserByUsername(String username);
     UserPartner findUserByEmail(String email);
     int updateUser(UserPartner user);
     void deleteUserByUsername(String username);
     UserPartner selectUserByToken(String token); // select user by token
     UserPartner updateUserProfile(UserPartner userPartner);
     UserPartner getLastRaceByUsername(String username);
     UserPartner updateLastRaceByUsername(UserPartner userPartner);
}