package coachingmateanalytics.coachingmate.entity;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class UserPartner {
    private long userId;
    private String username;
    private String fullname;
    private String email;
    private String token;
    private String userAccessToken;
    private Date tokenDate;
    private String userAccessSecret;
    private String password;

    private String gender;
    private String birthday;
    private String height;
    private String weight;
    private String rest_HR;
    private String max_HR;
    private String ltHR;
    private String tp;
    private String tp_ML;
    private String last_Race_Distance, last_Race_Time;
    private String tp_Race;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserAccessSecret() {
        return userAccessSecret;
    }

    public void setUserAccessSecret(String userAccessSecret) {
        this.userAccessSecret = userAccessSecret;
    }

    public UserPartner() {
        super();
    }

    public UserPartner(String username, String userAccessToken) {
        super();
        this.username = username;
        this.userAccessToken = userAccessToken;

    }
    public UserPartner(String username, String userAccessToken, String userAccessSecret) {
        super();
        this.username = username;
        this.userAccessToken = userAccessToken;
        this.userAccessSecret = userAccessSecret;

    }

    public UserPartner(long userId, String username, String userAccessToken, String userAccessSecret, String password, String fullname, String email) {
        this.userId = userId;
        this.username = username;
        this.userAccessToken = userAccessToken;
        this.userAccessSecret = userAccessSecret;
        this.password = password;
        this.fullname=fullname;
        this.email=email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getFullname() {
    	return fullname;
    }

    public void setFullname(String fullname) {
    	this.fullname = fullname;
    }

    public String getEmail() {
    	return email;
    }

    public void setEmail(String email) {
    	this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRest_HR() {
        return rest_HR;
    }

    public void setRest_HR(String rest_HR) {
        this.rest_HR = rest_HR;
    }

    public String getMax_HR() {
        return max_HR;
    }

    public void setMax_HR(String max_HR) {
        this.max_HR = max_HR;
    }

    public String getLtHR() {
        return ltHR;
    }

    public void setLtHR(String ltHR) {
        this.ltHR = ltHR;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTp_ML() {
        return tp_ML;
    }

    public void setTp_ML(String tp_ML) {
        this.tp_ML = tp_ML;
    }

    public String getLast_Race_Distance() {
        return last_Race_Distance;
    }

    public void setLast_Race_Distance(String last_Race_Distance) {
        this.last_Race_Distance = last_Race_Distance;
    }

    public String getLast_Race_Time() {
        return last_Race_Time;
    }

    public void setLast_Race_Time(String last_Race_Time) {
        this.last_Race_Time = last_Race_Time;
    }

    public String getTp_Race() {
        return tp_Race;
    }

    public void setTp_Race(String tp_Race) {
        this.tp_Race = tp_Race;
    }



    @Override
    public String toString()
    {
        return "UserPartner [userId=" + userId + ", userName=" + username + ",fullName=\" + fullname + \",email=\" + email + \", userAccessToken=" + userAccessToken
                + ", userAccessSecret=" + userAccessSecret + ", Height= "+height+ ", rest_HR= "+rest_HR+", max_HR= "+getMax_HR()+", lactateThresholdHR= "+ltHR+ "]";
    }

}