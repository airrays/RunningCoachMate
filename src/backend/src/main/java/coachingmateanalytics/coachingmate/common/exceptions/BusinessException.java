package coachingmateanalytics.coachingmate.common.exceptions;

import coachingmateanalytics.coachingmate.common.enums.ResponseCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private Integer statusCode;
    private String message;

    public BusinessException(ResponseCode responseCode){
        this.statusCode = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

}
