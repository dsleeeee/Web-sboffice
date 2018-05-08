package kr.co.solbipos.pos.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 매장 상태 enum type 009<br>
 * TB_MS_STORE > SYS_STAT_FG
 * 
 * @author bjcho
 *
 */
public enum SysStatFg implements CodeEnum {
    
    /** 전체 */
    ALL("all"),
    /** 오픈 */
    OPEN("1"),
    /** 폐점 */
    CLOSE("2"),
    /** 중지 */
    STOP("3"),
    /** 데모 */
    DEMO("9");
    
    private String code;
  
    SysStatFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(SysStatFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<SysStatFg> {
        public TypeHandler() {
            super(SysStatFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
}
