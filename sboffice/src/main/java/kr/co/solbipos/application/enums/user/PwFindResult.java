package kr.co.solbipos.application.enums.user;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 패스워드 otp 결과
 * 
 * @author bjcho
 */
public enum PwFindResult implements CodeEnum  {
    
    /** 유져 정보 없음 */
    EMPTY_USER("EMPTY_USER"),
    /** 인증번호 틀림 */
    OTP_ERROR("OTP_ERROR"),
    /** 인증번호 입력시간 지남 */
    OTP_LIMIT_ERROR("OTP_LIMIT_ERROR"),
    /** 인증번호 일치 */
    OTP_OK("OTP_OK");
    
    private String code;
    private PwFindResult[] value;
  
    PwFindResult(String code, PwFindResult... values) {
        this.code = code;
        this.value = values;
    }
  
    public PwFindResult[] getValues() {
        return value;
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
