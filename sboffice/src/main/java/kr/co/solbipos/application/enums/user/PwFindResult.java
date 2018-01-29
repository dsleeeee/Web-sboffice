package kr.co.solbipos.application.enums.user;

import kr.co.solbipos.enums.EnumValue;

/**
 * @author 정용길
 *
 */
public enum PwFindResult implements EnumValue<String> {
    
    /** 유져 정보 없음 */
    EMPTY_USER("EMPTY_USER"),
    /** 인증번호 틀림 */
    OTP_ERROR("OTP_ERROR"),
    /** 인증번호 입력시간 지남 */
    OTP_LIMIT_ERROR("OTP_LIMIT_ERROR"),
    /** 인증번호 일치 */
    OTP_OK("OTP_OK");
    
    private final String result;
    
    PwFindResult(String result) {
        this.result = result;
    }

    @Override
    public String getValue() {
        return result;
    }
}
