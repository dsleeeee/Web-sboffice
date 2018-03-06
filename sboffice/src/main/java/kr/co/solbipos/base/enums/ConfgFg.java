package kr.co.solbipos.base.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 설정 구분 enum type<br>
 * TB_WB_STORE_CONFG_XML > ConfFg
 * 
 * @author bjcho
 *
 */
public enum ConfgFg implements CodeEnum {
    
    /** 터치키_분류 */
    TOUCH_CLASS("1"),
    /** 터치키 */
    TOUCH("2"),
    /** 테이블 그룹 */
    TABLE_GROUP("3"),
    /** 테이블 */
    TABLE("4"),
    /** 테이블 속성 */
    TABLE_ATTR("5");
    
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
