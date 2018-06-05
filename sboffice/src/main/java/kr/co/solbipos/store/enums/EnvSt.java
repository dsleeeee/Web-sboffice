package kr.co.solbipos.store.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 환경설정 비교시 사용
 * TB_HQ_ENVST
 * 
 * @author bjcho
 *
 */
public enum EnvSt implements CodeEnum {
    
    /** 일반 */
    ENV_177("177");
    
    private String code;
  
    EnvSt(String code) {
        this.code = code;
    }
    
    @MappedTypes(EnvSt.class)
    public static class TypeHandler extends CodeEnumTypeHandler<EnvSt> {
        public TypeHandler() {
            super(EnvSt.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
}
