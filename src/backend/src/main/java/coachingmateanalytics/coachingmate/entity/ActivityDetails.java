package coachingmateanalytics.coachingmate.entity;

import java.util.List;

/**
 * Auto-generated: 2022-04-12 8:19:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 * @Discription: ActivityDetails entity class for CoachingMate application
 */
public class ActivityDetails {

    private String userId;
    private String userAccessToken;
    private String summaryId;
    private long activityId;
    private Summary summary;
    private List<Samples> samples;
    private List<Laps> laps;


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

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSamples(List<Samples> samples) {
        this.samples = samples;
    }

    public List<Samples> getSamples() {
        return samples;
    }

    public void setLaps(List<Laps> laps) {
        this.laps = laps;
    }

    public List<Laps> getLaps() {
        return laps;
    }

    @Override
    public String toString() { // toString() method is used to print the object's content in the console for debugging purpose only 
        return "ActivityDetails{" +
                "userId='" + userId + '\'' +
                ", userAccessToken='" + userAccessToken + '\'' +
                ", summaryId='" + summaryId + '\'' +
                ", activityId=" + activityId +
                ", summary=" + summary +
                ", samples=" + samples +
                ", laps=" + laps +
                '}';
    }
}