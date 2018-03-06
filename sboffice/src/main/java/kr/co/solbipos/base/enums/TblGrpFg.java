package kr.co.solbipos.base.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 테이블 그룹 구분 enum type<br>
 * TB_MS_TABLE_GROUP > TBL_GRP_FG
 * 
 * @author bjcho
 *
 */
public enum TblGrpFg implements CodeEnum {
    
    /** 일반 */
    NORMAL("1"),
    /** 포장 */
    TOGO("2"),
    /** 배달 */
    DELIVERY("3");
    
    private String code;
  
    TblGrpFg(String code) {
        this.code = code;
    }
    @MappedTypes(TblGrpFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TblGrpFg> {
        public TypeHandler() {
            super(TblGrpFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
