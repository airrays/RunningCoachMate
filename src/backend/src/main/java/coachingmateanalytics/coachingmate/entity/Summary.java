package coachingmateanalytics.coachingmate.entity;

import lombok.Data;

/**
 * Auto-generated: 2022-04-12 8:19
 * Auto-generated: 2022-04-13 10:38:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Summary {

    private long activityId;
    private String activityName;
    private int durationInSeconds;
    private long startTimeInSeconds;
    private int startTimeOffsetInSeconds;
    private String activityType;
    private int averageHeartRateInBeatsPerMinute;
    private double averageRunCadenceInStepsPerMinute;
    private double averageSpeedInMetersPerSecond;
    private double averagePaceInMinutesPerKilometer;
    private String deviceName;
    private double distanceInMeters;
    private int maxHeartRateInBeatsPerMinute;
    private double maxPaceInMinutesPerKilometer;
    private int maxRunCadenceInStepsPerMinute;
    private double maxSpeedInMetersPerSecond;
    private double maxBikeCadenceInRoundsPerMinute;
    private int steps;
    private int activeKilocalories;
    private double averageSwimCadenceInStrokesPerMinute;
    private double startingLatitudeInDegree;
    private double startingLongitudeInDegree;
    private double totalElevationGainInMeters;
    private double totalElevationLossInMeters;
    private double averageBikeCadenceInRoundsPerMinute;

}