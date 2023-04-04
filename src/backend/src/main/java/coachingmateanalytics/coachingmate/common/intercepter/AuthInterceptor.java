package coachingmateanalytics.coachingmate.common.intercepter;

import coachingmateanalytics.coachingmate.common.annotation.AuthCheck;
import coachingmateanalytics.coachingmate.common.enums.ResponseCode;
import coachingmateanalytics.coachingmate.common.exceptions.BusinessException;
import coachingmateanalytics.coachingmate.service.UserService;
import coachingmateanalytics.coachingmate.service.serviceImpl.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.Objects;

@Configuration
public class AuthInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //token 校验
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "True");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                "x-token, User, UserId, token, Authorization");
        response.setContentType("application/json;charset=UTF-8");

        AuthCheck authCheck;
        UserService userService = new UserServiceImpl();
        if (handler instanceof HandlerMethod) {
            authCheck = ((HandlerMethod) handler).getMethodAnnotation(AuthCheck.class);
        } else {
            return true;
        }

        String authToken = request.getHeader("X-Token");
        if(authCheck != null)
            if(userService.tokenCheck(authToken))
                throw new BusinessException(ResponseCode.AUTHORITY_AUTHENTICATION_FAILED);



        return true;
    }
}