package kr.co.solbipos.application.enums.grid;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 패스워드 변경 시도 결과
 * 
 * @author bjcho
 */
public enum GridDataFg implements CodeEnum  {

    /** 데이터 삽입 */
    INSERT("I"),
    /** 데이터 수정 */
    UPDATE("U"),
    /** 데이터 삭제 */
    DELETE("D");
    
    private String code;
  
    GridDataFg(String code) {
        this.code = code;
    }
   
    @MappedTypes(GridDataFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<GridDataFg> {
        public TypeHandler() {
        super(GridDataFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
