package kr.co.solbipos.base.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 입력구분 H:본사, S:매장 enum type<br>
 * TB_MS_TOUCH_CLASS, TB_MS_TOUCH > InFg
 * 
 * @author bjcho
 *
 */
public enum InFg implements CodeEnum {
    
    /** 본사 */
    HQ("H"),
    /** 매장 */
    STORE("S");
    
    private String code;
  
    InFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(InFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<InFg> {
        public TypeHandler() {
            super(InFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
