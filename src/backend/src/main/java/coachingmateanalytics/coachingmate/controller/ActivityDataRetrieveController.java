package coachingmateanalytics.coachingmate.controller;

import java.util.List;

import javax.annotation.Resource;

import coachingmateanalytics.coachingmate.dao.DashboardStatisticsDao;
import coachingmateanalytics.coachingmate.entity.DashboardStastic;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coachingmateanalytics.coachingmate.entity.Activity;
import coachingmateanalytics.coachingmate.entity.ActivityDetails;
import coachingmateanalytics.coachingmate.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 24/9/20 15:51
 * @Description:
 */
@RestController
@EnableAutoConfiguration
@Slf4j
@RequestMapping("/activity")
//@CrossOrigin("http://localhost:3000")
public class ActivityDataRetrieveController {
    @Resource
    ActivityService activityService;

//    @GetMapping("/getAllByUsername")
//    @ApiOperation(
//            value = "retrieve Data By Username",
//            notes = "query all activity data of specific user")
//    public ResponseEntity<List<Activity>> retrieveDataByUser(@ApiParam(required = true, type = "String") @RequestParam("username") String username) {
//        List<Activity> allByUsername = activityService.findAllByUsername(username);
//        return ResponseEntity.ok(allByUsername);
//    }


    @RequestMapping(value = "/getActivityByAccessToken", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "retrieve Data By Username", notes = "query all activity data of specific user")
    public ResponseEntity<List<Activity>> getActivityByAccessToken(@ApiParam(required = true, type = "String")
                                                                       @RequestParam("accessToken") String accessToken) {
        List<Activity> allByaccessToken = activityService.selectActivityByAccessToken(accessToken);
        return ResponseEntity.ok(allByaccessToken);
    }

    @RequestMapping(value = "/getActivityDetailsByAccessToken", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "retrieve Data By Username", notes = "query all activity data of specific user")
    public ResponseEntity<List<ActivityDetails>> getActivityDetailsByAccessToken(@ApiParam(required = true, type = "String")
                                                                                     @RequestParam("accessToken") String accessToken) {
        List<ActivityDetails> activityDetailsList = activityService.selectActivityDetailsByAccessToken(accessToken);
        return ResponseEntity.ok(activityDetailsList);
    }

    @RequestMapping(value = "/getActivityByAccessTokenAndType", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "retrieve Data By Username", notes = "query all activity data of specific user")
    public List<Activity> getActivityByAccessTokenAndType(@ApiParam(required = true, type = "String")
                                                                   @RequestParam("accessToken") String accessToken,
                                                                          @RequestParam("activityType") String activityType) {
//        List<Activity> allByaccessToken = activityService.selectActivityByAccessTokenAndType(accessToken, activityType);
        return activityService.selectActivityByAccessTokenAndType(accessToken, activityType);
    }

    @RequestMapping(value = "/getActivityDetailsByActivityId", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "retrieve Data By Username", notes = "query all activity data of specific user")
    public ActivityDetails getActivityDetailsByActivityId(@RequestParam("activityId") String activityId) {
        return activityService.selectActivityDetailsByActivityId(activityId);
    }

    @RequestMapping(value = "/getDashboardStatisticsByAccessToken", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "retrieve Data By Username", notes = "query all activity data of specific user")
    public DashboardStastic getDashboardStatisticsByAccessToken(@RequestParam("accessToken") String accessToken) {
        activityService.statisticsActivities(accessToken);
        return activityService.selectDashboardStatisticsByAccessToken(accessToken);
    }
}
