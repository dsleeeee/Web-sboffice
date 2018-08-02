package kr.co.solbipos.application.common.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 리소스 구분<br>
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author bjcho
 *
 */
public enum ResrceFg implements CodeEnum  {
    
    /** 리소스 : 메뉴 */
    MENU("M")
    /** 리소스 : 기능 */
    , FUNC("F");
    
  private String code;

  ResrceFg(String code) {
      this.code = code;
  }
 
  @MappedTypes(ResrceFg.class)
  public static class TypeHandler extends CodeEnumTypeHandler<ResrceFg> {
      public TypeHandler() {
          super(ResrceFg.class);
      }
  }
   
  @Override
  @JsonValue
  public String getCode() {
      return code;
  }
}
