package coachingmateanalytics.coachingmate.entity;


/**
 * Auto-generated: 2022-04-12 17:28:58
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 * @Discription: Activity File entity class for CoachingMate application
 */
public class ActivityFile {

    private String userId;
    private String userAccessToken;
    private String summaryId;
    private String fileType;
    private String callbackURL;
    private long startTimeInSeconds;
    private long activityId;
    private String activityName;
    private boolean manual;

    /**
     * Getter & Setter
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }
    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }
    public String getSummaryId() {
        return summaryId;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileType() {
        return fileType;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }
    public String getCallbackURL() {
        return callbackURL;
    }

    public void setStartTimeInSeconds(long startTimeInSeconds) {
        this.startTimeInSeconds = startTimeInSeconds;
    }
    public long getStartTimeInSeconds() {
        return startTimeInSeconds;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }
    public long getActivityId() {
        return activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getActivityName() {
        return activityName;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }
    public boolean getManual() {
        return manual;
    }

    @Override
    public String toString() { // -toString() method is used to print the object's content in the console for debugging purpose only
        return "ActivityFile{" +
                "userId='" + userId + '\'' +
                ", userAccessToken='" + userAccessToken + '\'' +
                ", summaryId='" + summaryId + '\'' +
                ", fileType='" + fileType + '\'' +
                ", callbackURL='" + callbackURL + '\'' +
                ", startTimeInSeconds=" + startTimeInSeconds +
                ", activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", manual=" + manual +
                '}';
    }
}