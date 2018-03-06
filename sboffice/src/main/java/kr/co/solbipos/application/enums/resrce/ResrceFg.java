package kr.co.solbipos.application.enums.resrce;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 리소스 구분<br>
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author bjcho
 *
 */
public enum ResrceFg implements CodeEnum  {
    
    /** 리소스 : 메뉴 */
    MENU("W001")
    /** 리소스 : 기능 */
    , FUNC("W002");
    
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
