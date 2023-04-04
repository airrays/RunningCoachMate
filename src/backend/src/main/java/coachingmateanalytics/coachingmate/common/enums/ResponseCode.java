package coachingmateanalytics.coachingmate.common.enums;



public enum ResponseCode {

    SUCCESS(200, "Success"),
    EMAIL_HAS_EXISTED(20001, "Email is already exsited!"),
    PARAM_IS_INVALID(20002, "Param is invalid."),
    USER_IS_NOT_EXISTED(20003, "User is not existed or password is incorrect"),
    USER_HAS_NOT_LOGIN(20004, "User is not login."),
    CHECK_TOKEN_FAIL(20004, "Token verification failed, please log in again."),
    AUTHORITY_AUTHENTICATION_FAILED(20004, "Authority authentication failed"),

    QUESTION_ALREADY_EXIST(30001, "This question is already existed"),

    SYSTEM_ERROR(10000, "System Error, Please Contact Admin.");



    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
