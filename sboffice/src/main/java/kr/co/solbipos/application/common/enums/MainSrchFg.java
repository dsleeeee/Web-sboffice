package kr.co.solbipos.application.common.enums;

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
public enum MainSrchFg implements CodeEnum  {
    
    /** TYPE1 : 일별(1주), 요일별(1개월), 월별(1년)*/
    TYPE1("1")
    /** TYPE2 : 오늘, 1주일, 1개월 */
    , TYPE2("2");
    private String code;
  
    MainSrchFg(String code) {
        this.code = code;
    }
   
    @MappedTypes(MainSrchFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<MainSrchFg> {
        public TypeHandler() {
            super(MainSrchFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
