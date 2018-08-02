package kr.co.solbipos.base.common.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 설정 구분 enum type<br>
 * TB_WB_STORE_CONFG_XML > ConfFg
 * 
 * @author bjcho
 *
 */
public enum ConfgFg implements CodeEnum {
    
    /** 터치키 */
    TOUCH_KEY("1"),
    /** 테이블 구성 */
    TABLE_LAYOUT("2"),
    /** 테이블 속성 */
    TABLE_ATTR("3");
    
    private String code;
  
    ConfgFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(ConfgFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ConfgFg> {
        public TypeHandler() {
            super(ConfgFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
