package kr.co.solbipos.sys.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 대상 전체 구분 A:전체, P:일부 enum type<br>
 * TB_WB_AUTHOR_GRP_INFO > TargetAllFg
 * 
 * @author bjcho
 *
 */
public enum TargetAllFg implements CodeEnum {
    
    /** 전체 */
    ALL("A"),
    /** 일부 */
    PART("P");
    
    private String code;
  
    TargetAllFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(TargetAllFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TargetAllFg> {
        public TypeHandler() {
            super(TargetAllFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
