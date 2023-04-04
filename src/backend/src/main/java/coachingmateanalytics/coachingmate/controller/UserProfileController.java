package coachingmateanalytics.coachingmate.controller;
import coachingmateanalytics.coachingmate.common.enums.ResponseCode;
import coachingmateanalytics.coachingmate.common.exceptions.BusinessException;
import coachingmateanalytics.coachingmate.entity.UserPartner;
import coachingmateanalytics.coachingmate.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@Slf4j
@RequestMapping("/user")
public class UserProfileController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/getUserProfile",method=RequestMethod.POST, produces = "application/json")
    public UserPartner getUserProfile(@RequestBody Map<String,String> param){
        UserPartner userPartner=userService.getUserProfile(param.get("username"));
        if (userPartner == null) {
            return null;
        }
        return userPartner;
    }
    @RequestMapping(value = "/updateUserProfile",method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "update UserProfile",notes="userProfile Update")
    public UserPartner updateUserProfile(@RequestBody UserPartner userPartner){
        System.out.println("UserProfileController - "+userPartner);
        UserPartner newUserPartner=userService.updateUserProfile(userPartner);
        return newUserPartner;
    }
    @RequestMapping(value="/getLastRace",method=RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "get Last Race",notes="getProfile get")
    public UserPartner getMLTP(@RequestBody Map<String,String> param){
        System.out.println("UserProfileController - get Last Race"+param.get("username"));
        UserPartner userPartner=userService.getLastRace(param.get("username"));
        return userPartner;
    }
    @RequestMapping(value="/updateLastRace",method=RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "update Last Race",notes="updateLastRace Update")
    public UserPartner updateLastRace(@RequestBody UserPartner userPartner){
        System.out.println("UserProfileController - update Last Race"+userPartner.getUsername());
        UserPartner user=userService.updateLastRace(userPartner);
        return user;
    }
}
