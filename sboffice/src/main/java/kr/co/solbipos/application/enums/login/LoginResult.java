package kr.co.solbipos.application.enums.login;

import kr.co.solbipos.enums.EnumValue;

/**
 * 로그인 결과 enum type
 * 
 * @author 정용길
 */
public enum LoginResult implements EnumValue<String> {
    
    /** 로그인 성공 */
    SUCCESS("SUCC")
    /** 로그인 실패 */
    , FAIL("FAIL")
    /** 유져 잠금 상태 */
    , LOCK("LOCK")
    /** 존재하지 않는 유져 */
    , NOT_EXISTS_ID("NID")
    /** 패스워드 틀림 */
    , PASSWORD_ERROR("ERR")
    /** 패스워드 변경 필요한 상태 */
    , PASSWORD_CHANGE("CHG")
    /** 패스워드 유효 기간 지남 */
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
