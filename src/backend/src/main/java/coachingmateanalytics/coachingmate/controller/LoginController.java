package coachingmateanalytics.coachingmate.controller;

import coachingmateanalytics.coachingmate.common.annotation.AuthCheck;
import coachingmateanalytics.coachingmate.common.enums.ResponseCode;
import coachingmateanalytics.coachingmate.common.exceptions.BusinessException;

import java.util.Map;

import javax.annotation.Resource;

import coachingmateanalytics.coachingmate.entity.UserPartner;
import coachingmateanalytics.coachingmate.service.UserService;
import coachingmateanalytics.coachingmate.service.serviceImpl.ActivityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 12/9/20 16:12
 * @Description: Login Controller for CoachingMate application
 */
@RestController
@EnableAutoConfiguration
@Slf4j
// @CrossOrigin("http://localhost:3000")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    UserService userService;

    @Resource
    ActivityServiceImpl activityService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "login interface", notes = "user login")
//    @AuthCheck
    public UserPartner login(@ApiParam(required = true, type = "String") String username,
                             @ApiParam(required = true, type = "String") String password) {
        UserPartner userPartner = userService.loginCheck(username, password);
        if(userPartner == null) throw new BusinessException(ResponseCode.USER_IS_NOT_EXISTED);
        userPartner.setPassword("");
//        if(userPartner.getUserAccessToken() != null)
//            activityService.statisticsActivities(userPartner.getUserAccessToken());
        return userPartner;
//        if (userPartner == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        return new ResponseEntity<>(userPartner, HttpStatus.OK);
//        UserPartner userPartner = new UserPartner();
//        userPartner.setUsername(username);
//        userPartner.setPassword(password);
//        logger.info("user username " + username + " login successfully");
//        return new ResponseEntity<>(userPartner, HttpStatus.OK);

    }

    /**
     * @param param
     * @return
     * @Discription: get user by token 
     */
    @RequestMapping(value = "/getUserByToken", method = RequestMethod.POST, produces = "application/json")
    public UserPartner getInfo(@RequestBody Map<String, String> param) {
        UserPartner userPartner = userService.getUserByToken(param.get("token")); // get userPartner from userService 
        if (userPartner == null)
            return null;
        userPartner.setPassword(""); // set password to empty
        return userPartner;
    }


    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
    public UserPartner resetPassword(@RequestBody Map<String, String> param) {
        UserPartner userPartner = userService.getUser(param.get("username")); // get userPartner from userService
        if (userPartner == null)
            throw new BusinessException(ResponseCode.USER_IS_NOT_EXISTED);
        userPartner.setPassword(""); // set password to empty
        return userPartner;
    }
}
