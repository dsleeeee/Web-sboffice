package kr.co.solbipos.application.enums.user;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 패스워드 변경 시도 결과
 * 
 * @author bjcho
 */
public enum PwChgResult implements CodeEnum  {
    
    /** 비밀번호 일치 하지 않음 */
    PASSWORD_NOT_MATCH("PASSWORD_NOT_MATCH"),
    /** 변경 패스워드가 기존 패스워드와 동일함. */
    PASSWORD_NEW_OLD_MATH("PASSWORD_NEW_OLD_MATH"),
    /** 패스워드 정책이 맞지 않음 */
    PASSWORD_REGEXP("PASSWORD_REGEXP"),
    /** 변경 패스워드가 2개가 서로 맞지 않음 */
    NEW_PASSWORD_NOT_MATCH("NEW_PASSWORD_NOT_MATCH"),
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
    /** 체크 오류 */
    CHECK_NOK("CHECK_NOK"),
    /** 체크 완료 */
    CHECK_OK("CHECK_OK");
    
    private String code;
  
    PwChgResult(String code) {
        this.code = code;
    }
   
    @MappedTypes(PwChgResult.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PwChgResult> {
        public TypeHandler() {
        super(PwChgResult.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
