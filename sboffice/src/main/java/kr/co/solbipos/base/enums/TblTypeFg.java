package kr.co.solbipos.base.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 테이블 유형 구분 enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR, TB_MS_TABLE > TBL_TYPE_FG
 * 
 * @author bjcho
 *
 */
public enum TblTypeFg implements CodeEnum {
    
    /** 사각 */
    SQUARE("1"),
    /** 원탁 */
    CIRCLE("2"),
    /** 포장 */
    TOGO("3"),
    /** 배달 */
    DELIVERY("4"),
    /** 최소 */
    MINIMUM("5");
    
    private String code;
  
    TblTypeFg(String code) {
        this.code = code;
    }
    @MappedTypes(TblTypeFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TblTypeFg> {
        public TypeHandler() {
            super(TblTypeFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
