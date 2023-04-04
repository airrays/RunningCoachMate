package coachingmateanalytics.coachingmate.common.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Saul
 * @Date: 19:46 13/9/20
 * @Description: 用户登录检查拦截
 */
public class SessionIntercepter implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SessionIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
//        logger.info("intercepter for all request");
        String uri = request.getRequestURI();
        String token = request.getHeader("X-Token");
//        if(token != "1"){
//            throw new BusinessException(ResponseCode.AUTHORITY_AUTHENTICATION_FAILED);
//        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "True");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                "x-token, User, UserId, token, Authorization");
        response.setContentType("application/json;charset=UTF-8");

        return true;
    }
}
