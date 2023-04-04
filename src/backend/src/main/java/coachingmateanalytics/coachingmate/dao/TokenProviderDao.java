package coachingmateanalytics.coachingmate.dao;

import coachingmateanalytics.coachingmate.entity.RequestToken;
import coachingmateanalytics.coachingmate.service.OauthConfiguration;
import coachingmateanalytics.coachingmate.common.utils.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 
 * token provider data access object for getting access token and secret from garmin api and saving it to mongodb in requestToken collection 
 * 
 */
@Component
public class TokenProviderDao
{
    private static final Logger logger = LoggerFactory.getLogger(TokenProviderDao.class); // logger for TokenProviderDao 

    @Autowired
    OauthConfiguration oauthConfig;

    @Autowired
    TokenDao tokenDao;


    public String generateRequestTokenSecret(String url) {
        String tokenAndSecret = null;
        try{ // try to generate request token secret 
            RestTemplate restTemplate = oauthConfig.getRestTemplate(); // get restTemplate from oauthConfig 
            ResponseEntity<String> result =  restTemplate.exchange(url, HttpMethod.GET,oauthConfig.httpEntity(), String.class); // exchange request token secret 
            tokenAndSecret = result.getBody().toString(); // get request token secret 
            logger.info("value in tokenSecret is: {}",tokenAndSecret); // log request token secret 
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }

        return tokenAndSecret;

    }

    public String generateAccessTokenSecret(String url, String oauthToken) {
        String accessTokenSecret = null;
        RequestToken reqToken = tokenDao.findByToken(oauthToken);
        try{ // try to get access token secret from url with oauthToken as parameter
            RestTemplate restTemplate = oauthConfig.getRestTemplate(oauthToken, reqToken.getSecret()); // get restTemplate with oauthToken and secret 
            ResponseEntity<String> result =  restTemplate.exchange(url, HttpMethod.POST,oauthConfig.httpEntity(),String.class); // exchange url with post method and httpEntity with string as response type 
            accessTokenSecret = result.getBody().toString(); // get access token secret from url 
            logger.info("value in accessTokenSecret is: {}",accessTokenSecret); // log access token secret 
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        return accessTokenSecret + Consts.VARIABLE_DELIMTER + reqToken.getUsername(); // return access token secret and username 
    }


}
