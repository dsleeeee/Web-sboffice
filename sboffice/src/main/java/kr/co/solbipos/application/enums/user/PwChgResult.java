package kr.co.solbipos.application.enums.user;

import kr.co.solbipos.enums.EnumValue;

/**
 * @author 정용길
 *
 */
public enum PwChgResult implements EnumValue<String> {
    
    /** 비밀번호 일치 하지 않음 */
    PASSWORD_NOT_MATCH("PASSWORD_NOT_MATCH"),
    /** 비정상적인 접근 */
    UUID_NOT_MATCH("UUID_NOT_MATCH"),
    /** UUID 유효지간 지남 */
    UUID_LIMIT_ERROR("UUID_LIMIT_ERROR"),
    /** 유져 정보가 없음 */
    EMPTY_USER("EMPTY_USER"),
    /** id 정보가 맞지 않음 */
    ID_NOT_MATCH("ID_NOT_MATCH"),
    /** 잠긴 상태 유져 */
    LOCK_USER("LOCK_USER"),
    /** 체크 완료 */
    CHECK_OK("CHECK_OK");
    
    private final String result;
    
    PwChgResult(String result) {
        this.result = result;
    }

    @Override
    public String getValue() {
        return result;
    }
}
