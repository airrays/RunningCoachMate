package coachingmateanalytics.coachingmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private long userId;
    private Gender gender;
    private Date birthdate;
    private double height;
    private double weight;
    private int restHR;
    private int maxHR;
    private int lactateThresholdHR;

    // threshold pace, directly entered by user
    private double thresholdPaceInput;
    // threshold pace, calculated by Daniel's Table if user enters last race performance
    private double thresholdPaceRace;
    // threshold pace, predicted by real-time mlmodule
    private double thresholdPaceML;

    public UserProfile(long userId) {
        super();
        this.userId = userId;
    }

    public UserProfile(long userId, Gender gender, Date birthdate, double height, double weight, int restHR, int maxHR) {
        super();
        this.userId = userId;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.restHR = restHR;
        this.maxHR = maxHR;
    }


}
