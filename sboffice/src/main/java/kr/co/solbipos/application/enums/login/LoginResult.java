package kr.co.solbipos.application.enums.login;

import kr.co.solbipos.enums.EnumValue;

/**
 * @author 정용길
 *
 */
public enum LoginResult implements EnumValue<String> {
    
    SUCCESS("SUCC")
    , FAIL("FAIL")
    , LOCK("LOCK")
    , NOT_EXISTS_ID("NID")
    , PASSWORD_ERROR("ERR")
    , PASSWORD_CHANGE("CHG")
    , PASSWORD_EXPIRE("EXP")
    ;
    
    private final String result;
    
    LoginResult(String result) {
        this.result = result;
    }

    @Override
    public String getValue() {
        return result;
    }
}
