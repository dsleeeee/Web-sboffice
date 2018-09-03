package kr.co.common.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 단독매장 00000:단독매장 enum type<br>
 * 프로젝트 전체에서 사용
 *  
 * @author bjcho
 *
 */
public enum SoloHq implements CodeEnum {
    
    /** 단독매장 */
    SOLO("00000");

    
    private String code;
  
    SoloHq(String code) {
        this.code = code;
    }
    
    @MappedTypes(SoloHq.class)
    public static class TypeHandler extends CodeEnumTypeHandler<SoloHq> {
        public TypeHandler() {
            super(SoloHq.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
