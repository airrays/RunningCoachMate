package coachingmateanalytics.coachingmate.controller;


import coachingmateanalytics.coachingmate.entity.*;
import coachingmateanalytics.coachingmate.service.ActivityService;
import coachingmateanalytics.coachingmate.common.utils.FileUtils;
import coachingmateanalytics.coachingmate.common.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiOperation;


import com.fasterxml.jackson.core.JsonProcessingException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GarminPushController {
    private static final Logger logger = LoggerFactory.getLogger(GarminPushController.class);
    public static final String STORE_PATH="D:/coachingmate/public/garmin_raw/";

    @Autowired
    ActivityService activityService;


    //configure this url to enpoint configuration, and the garmin endpoint will transfer the data to this server
//    @PostMapping("/pushActivity", produces = "application/json")
    @RequestMapping(value = "/pushActivity", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "push data url", notes = "configure this url to endpoint configuration, " +
            "and the garmin endpoint will transfer the data to this server@RequestBody List<Activity> activityList")
    public ResponseEntity<String> acceptPushedActivity(@RequestBody String activities) {
        logger.info("start push data");

        //请求参数打印
        logger.info("Activities: " + activities);

        Gson gson = new GsonBuilder().create();
        ActivityBean activityBean = gson.fromJson(activities, ActivityBean.class);

        for(Activity activity : activityBean.getActivities()){
            if(activity != null) {
                activityService.insertActivity(activity);
            }
        }


//        logger.info("properties: " + properties);
//        logger.info("upload :" + activityList);
//        activityService.insertActivity(activityList);
//        activityService.statisticsActivities(accseeToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Location", "public/garmin_raw");
        return ResponseEntity.accepted().headers(httpHeaders).body("Accept the pushed activities");

    }
    //configure this url to enpoint configuration, and the garmin endpoint will transfer the data to this server
//    @PostMapping("/pushActivity", produces = "application/json")
    @RequestMapping(value = "/pushActivityDetail", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "push data url", notes = "configure this url to endpoint configuration, " +
            "and the garmin endpoint will transfer the data to this server@RequestBody List<Activity> activityList")
    public ResponseEntity<String> acceptPushedActivityDetail(@RequestBody String acceptPushedActivityDetail) {
        logger.info("start push data");

        //请求参数打印
//        logger.info("acceptPushedActivityDetail: " + acceptPushedActivityDetail);
        HttpHeaders httpHeaders = new HttpHeaders();
        try{
            Gson gson = new GsonBuilder().create();
            String accseeToken = "";
            ActivityDetailsBean activityDetailsBean = gson.fromJson(acceptPushedActivityDetail, ActivityDetailsBean.class);
            for(ActivityDetails activityDetails : activityDetailsBean.getActivityDetails()){
                if(activityDetails != null){
                    accseeToken = activityDetails.getUserAccessToken();
                    activityService.insertActivityDetail(activityDetails);
                }
            }

//            activityService.statisticsActivities(accseeToken);

        } catch (Exception e){
            logger.info(String.valueOf(e));
        } finally {

            httpHeaders.set("Location", "public/garmin_raw");
            return ResponseEntity.accepted().headers(httpHeaders).body("Accept the pushed activities");
        }

        //        String activityDetailsListStr = acceptPushedActivityDetail.split(":", 2)[1];
//        activityDetailsListStr = activityDetailsListStr.substring(2, activityDetailsListStr.length() - 3);
//        ActivityDetails details = JsonUtils.string2Obj(activityDetailsListStr, ActivityDetails.class);

//        System.out.println(JsonUtils.string2Obj(activityDetailsListStr, ActivityDetailsBean.class));
//        activityService.insertActivityDetail(activityDetailsList);
//        ActivityDetailsBean activityDetailsBean = JsonUtils.string2Obj(acceptPushedActivityDetail, ActivityDetailsBean.class);
//        System.out.println(activityDetailsBean);
//        logger.info(activityDetailsListStr);
//        String[]  details = activityDetailsListStr.split("},\\{");
//        logger.info(String.valueOf(details));

    }

//@RequestMapping(value = "/pushActivityDetail", method = RequestMethod.POST, produces = "application/json")
//    @ApiOperation(value = "push data url", notes = "configure this url to enpoint configuration, " +
//            "and the garmin endpoint will transfer the data to this server@RequestBody List<Activity> activityList")
//    public ResponseEntity<String> acceptPushedActivityDetail(@RequestBody String acceptPushedActivityDetail) {
//        logger.info("start push data");
//
//        //请求参数打印
//        logger.info("acceptPushedActivityDetail: " + acceptPushedActivityDetail);
//        String activityDetailsListStr = acceptPushedActivityDetail.split(":", 2)[1];
//        activityDetailsListStr = activityDetailsListStr.substring(2, activityDetailsListStr.length() - 3);
////        ActivityDetails details = JsonUtils.string2Obj(activityDetailsListStr, ActivityDetails.class);
//        String[]  details = activityDetailsListStr.split("},\\{");
//        String tmp = details[0];
//        List<ActivityDetails> activityDetailsList = new ArrayList<>();
//        ActivityDetails tmpActivityDetails = null;
//        String tmp2 = "";
//        for(int i = 1; i < details.length; i++){
//            if(details[i].charAt(1) == 'u'){
//
//
//                try{
////                    logger.info("tmp1 is" + tmp.toString() + "|end");
//                    tmp2 = "{" + tmp.toString() + "}";
////                    logger.info("tmp3 is" + tmp2 + "|end");
//                    tmpActivityDetails = JsonUtils.string2Obj(tmp2, ActivityDetails.class);
//                    if(tmpActivityDetails != null)
//                        activityService.insertActivityDetail(tmpActivityDetails);
//                } catch (Exception e){
//                    logger.info(String.valueOf(e));
//                }
//
////                tmp = new StringBuilder("{" + details[i]);
//            } else {
//                tmp += "},{" + details[i];
//            }
//
//        }
//
//        try{
//
//            tmpActivityDetails = JsonUtils.string2Obj("{" + tmp.toString() + "}", ActivityDetails.class);
//            if(tmpActivityDetails != null)
//                activityService.insertActivityDetail(tmpActivityDetails);
//        } catch (Exception e){
//            logger.info(String.valueOf(e));
//        }
//
//
////        System.out.println(JsonUtils.string2Obj(activityDetailsListStr, ActivityDetailsBean.class));
////        activityService.insertActivityDetail(activityDetailsList);
////        ActivityDetailsBean activityDetailsBean = JsonUtils.string2Obj(acceptPushedActivityDetail, ActivityDetailsBean.class);
////        System.out.println(activityDetailsBean);
////        logger.info(activityDetailsListStr);
////        String[]  details = activityDetailsListStr.split("},\\{");
////        logger.info(String.valueOf(details));
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Location", "public/garmin_raw");
//        return ResponseEntity.accepted().headers(httpHeaders).body("Accept the pushed activities");
//
//    }
//
//
//    @RequestMapping(value = "/pushActivity2", method = RequestMethod.POST, produces = "application/json")
//    @ApiOperation(value = "push data url", notes = "configure this url to enpoint configuration, " +
//            "and the garmin endpoint will transfer the data to this server@RequestBody List<Activity> activityList")
//    public ResponseEntity<String> acceptPushedActivity2(@RequestBody String activities) {
//        Gson gson = new GsonBuilder().create();
//        logger.info("start push data");
//
//        String accseeToken = "";
//        String activitylist_str =activities.split(":", 2)[1];
//        activitylist_str = activitylist_str.substring(2, activitylist_str.length() - 3);
////        System.out.println(activitylist_str);
//        String[] activityStrlist = activitylist_str.split("},\\{");
//        List<Activity> activityList = new ArrayList<>();
//        for(String activityStr : activityStrlist){
//            Activity activity = JsonUtils.string2Obj("{" + activityStr + "}", Activity.class);
//            if(activity != null)
//                accseeToken = activity.getUserAccessToken();
//            activityList.add(activity);
//
//        }
//
////        logger.info("properties: " + properties);
////        logger.info("upload :" + activityList);
////        activityService.insertActivity(activityList);
//        logger.info("Activities: " + activities);
//        ActivityBean activityBean = gson.fromJson(activities, ActivityBean.class);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Location", "public/garmin_raw");
//        return ResponseEntity.accepted().headers(httpHeaders).body("Accept the pushed activities");
//    }
//
//configure this url to enpoint configuration, and the garmin endpoint will transfer the data to this server
//@PostMapping("/pushFile")
//@ApiOperation(value = "push data url", notes = "configure this url to enpoint configuration, " +
//        "and the garmin endpoint will transfer the data to this server")
////    public ResponseEntity<String> acceptPushedFile1(@RequestBody MultipartFile file) {
//public ResponseEntity<String> acceptPushedFile1(@RequestBody String info) throws IOException {
//    logger.debug("start push data");
//    logger.info("MultipartFile :" + info);
//    Pattern pattern = Pattern.compile("");
//    Matcher matcher = pattern.matcher(info);
//    String activitylist_str = info.split(":", 2)[1];
//    activitylist_str = activitylist_str.substring(2, activitylist_str.length() - 4);
//    ActivityFile activityFile = JsonUtils.string2Obj("{" + activitylist_str + "}", ActivityFile.class);
//    logger.info("Download File:" + activityFile.getCallbackURL());
//    FileUtils.downloadFile(activityFile.getCallbackURL(), "./test.fit");
//    logger.info("Download Completed");
//    HttpHeaders httpHeaders = new HttpHeaders();
////        try {
//////            byte[] data = IOUtils.toByteArray(file.getInputStream());
//////            String dataHead = new String(data, 0, 47182);
//////            logger.info("Activity Data" + dataHead);
//////            Activity activity = new Activity("3f3d5af3-7847-413e-a9fe-47aeffb6de44", uploadMetaData);
//////            activityService.saveActivity(activity);
//////            JSONObject jsonObject = JSON.parseObject(uploadMetaData);
////            String access_token = jsonObject.getString("oauthToken");
////            FitRecordRepository repository;
////            FitReader reader;
////            FitRecord record;
////            reader = FitReader.getInstance();
////            repository = reader.readInputStream(file.getInputStream());
////            record = repository.getFitRecord("session");
////            //----new added----//
////            int statistic_id = record.getIntValue(0, "statistic_id");
////            int user_id = record.getIntValue(0, "user_id");
////            int serial_number = record.getIntValue(0, "serial_number");
////            int session_id = record.getIntValue(0, "session_id");
////
////            int sport  = record.getIntValue(0, "sport");
////            DateTime start_time = record.getTimeValue(0, "start_time");
////
////            //----new added----//
////            // user_profile
////            int gender = record.getIntValue(0, "gender");
////            int age = record.getIntValue(0, "age");
////            int height = record.getIntValue(0, "height");
////            int weight = record.getIntValue(0, "weight");
////
////            // activity//
////            int type = record.getIntValue(0, "type");
////
////            // session //
////            DateTime timestamp = record.getTimeValue(0, "timestamp");
////            int avg_heart_rate = record.getIntValue(0, "avg_heart_rate");
////            int max_heart_rate = record.getIntValue(0, "max_heart_rate");
////            double swim_stroke = record.getIntValue(0, "swim_stroke");
////            double avg_stance_time_percent =record.getDistanceValue(0, "avg_stance_time_percent");
////
////            //----new added----//
////            double start_position_lat = record.getLatLonValue(0, "start_position_lat");
////            double start_position_long = record.getLatLonValue(0, "start_position_long");
////            double total_elapsed_time = record.getElapsedTimeValue(0, "total_elapsed_time");
////            double total_distance = record.getDistanceValue(0, "total_distance");
////            double total_cycles = record.getDistanceValue(0, "total_cycles");
////            double avg_stroke_count = record.getIntValue(0, "avg_stroke_count");
////            double avg_stroke_distance = record.getDistanceValue(0, "avg_stroke_distance");
////            double total_calories = record.getDistanceValue(0, "total_calories");
////            double avg_speed = record.getSpeedValue(0, "avg_speed");
////            double max_speed = record.getSpeedValue(0, "max_speed");
////            double avg_power = record.getDistanceValue(0, "avg_power");
////            double max_power = record.getDistanceValue(0, "max_power");
////            double total_ascent = record.getDistanceValue(0, "total_ascent");
////            double total_descent = record.getDistanceValue(0, "total_descent");
////            int num_laps = record.getIntValue(0, "num_laps");
////            double training_stress_score = record.getDistanceValue(0, "training_stress_score");
////            double intensity_factor = record.getDistanceValue(0, "intensity_factor");
////            double pool_length = record.getDistanceValue(0, "pool_length");
////            int threshold_power = record.getIntValue(0, "threshold_power");
////            int avg_cadence = record.getIntValue(0, "avg_cadence");
////            int max_cadence = record.getIntValue(0, "max_cadence");
////            double total_fat_calories = record.getDistanceValue(0, "total_fat_calories");
////            int normalized_power = record.getIntValue(0, "normalized_power");
////            int num_active_lengths = record.getIntValue(0, "num_active_lengths");
////            int sub_sport = record.getIntValue(0, "sub_sport");
////
////
////            Statistic statistic = new Statistic(access_token, statistic_id, user_id, sport, start_time.toString(),
////                                                start_position_lat, start_position_long, total_elapsed_time, total_distance, total_cycles,
////                                                avg_stroke_count , avg_stroke_distance, total_calories, avg_speed, max_speed,
////                                                avg_power, max_power, total_ascent, total_descent, num_laps,
////                                                training_stress_score, intensity_factor, pool_length, threshold_power, avg_cadence,
////                                                max_cadence, total_fat_calories, normalized_power, num_active_lengths, sub_sport,
////                                                //----new added---- //
////                                                gender, age, height, weight, type, timestamp.toString(), avg_heart_rate, max_heart_rate, swim_stroke,
////                                                avg_stance_time_percent, serial_number, session_id);
////            logger.info("finishing parse data ");
////            activityService.saveActivity(statistic);
////        } catch (Exception e) {
////            httpHeaders.set("Retry-After", "120");
////            return ResponseEntity.status(503).headers(httpHeaders).body("Failed to process. Reason : " + e.getMessage());
////        }
//    httpHeaders.set("Location", "public/garmin_raw");
//    return ResponseEntity.accepted().headers(httpHeaders).body("Accept the pushed file");
//
//}


}
