package kr.co.solbipos.application.enums.resrce;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 리소스 기능 구분 enum type<br>
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author bjcho
 *
 */
public enum FuncFg implements CodeEnum  {
    
    /** 기능 : 조회 */
    SELECT("F001")
    /** 기능 : 등록 */
    , INSERT("F002")
    /** 기능 : 수정 */
    , UPDATE("F003")
    /** 기능 : 삭제 */
    , DELETE("F004");
    
    private String code;
  
    FuncFg(String code) {
        this.code = code;
    }
   
    @MappedTypes(FuncFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<FuncFg> {
        public TypeHandler() {
            super(FuncFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
