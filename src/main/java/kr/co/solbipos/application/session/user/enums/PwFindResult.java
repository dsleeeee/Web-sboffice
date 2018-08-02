package kr.co.solbipos.application.session.user.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 패스워드 otp 결과
 * 
 * @author bjcho
 */
public enum PwFindResult implements CodeEnum  {
    
    /** 유져 정보 없음 */
    EMPTY_USER("EMPTY_USER"),
    /** 데이터 오류 */
    TO_MANY_USER("TO_MANY_USER"),
    /** 인증번호 틀림 */
    OTP_ERROR("OTP_ERROR"),
    /** 인증번호 입력시간 지남 */
    OTP_LIMIT_ERROR("OTP_LIMIT_ERROR"),
    /** 인증번호 일치 */
    OTP_OK("OTP_OK");
    
    private String code;
  
    PwFindResult(String code) {
        this.code = code;
    }
   
    @MappedTypes(PwFindResult.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PwFindResult> {
        public TypeHandler() {
        super(PwFindResult.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
