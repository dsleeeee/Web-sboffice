package kr.co.solbipos.sys.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 포함 제외 구분 I:포함, E:제외 enum type<br>
 * TB_WB_AUTHOR_EXCEPT > INCLD_EXCLD_FG
 * 
 * @author bjcho
 *
 */
public enum IncldExcldFg implements CodeEnum {
    
    /** 포함 */
    INCLUDE("I"),
    /** 제외 */
    EXCLUDE("E");
    
    private String code;
  
    IncldExcldFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(IncldExcldFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<IncldExcldFg> {
        public TypeHandler() {
            super(IncldExcldFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
