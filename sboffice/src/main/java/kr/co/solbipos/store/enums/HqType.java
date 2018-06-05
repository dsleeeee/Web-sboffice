package kr.co.solbipos.store.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 본사, 매장 타입 (일반, 데모)
 * TB_HQ_OFFICE
 * 
 * @author bjcho
 *
 */
public enum HqType implements CodeEnum {
    
    /** 일반 */
    COMM("C"),
    /** 데모 */
    DEMO("D");
    
    private String code;
  
    HqType(String code) {
        this.code = code;
    }
    
    @MappedTypes(HqType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<HqType> {
        public TypeHandler() {
            super(HqType.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
}
