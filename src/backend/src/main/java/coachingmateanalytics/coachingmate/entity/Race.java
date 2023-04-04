package coachingmateanalytics.coachingmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    private String userId;
    private String userAccessToken;
    private long activityId;
    private String activityName;
    private String activityType;
    private long startTimeInSeconds;
    private int startTimeOffsetInSeconds;

    private int durationInSeconds;
    private double distanceInMeters;
    private double averageSpeedInMetersPerSecond;
    private double averagePaceInMinutesPerKilometer;
    private int averageHeartRateInBeatsPerMinute;

    private boolean manual;



}
