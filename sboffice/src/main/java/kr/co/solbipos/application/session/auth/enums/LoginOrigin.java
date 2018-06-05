package kr.co.solbipos.application.session.auth.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * {@link kr.co.solbipos.application.session.auth.service.LoginHistVO}<br>
 * {@code loginOrgn} enum type<br>
 * 로그인 시도 웹, 포스 구분
 * 
 * @author bjcho
 */
public enum LoginOrigin implements CodeEnum  {

  /** 웹 로그인 */
    WEB("WEB")
    /** 포스 로그인 */
    , POS("POS")
    /** 기타 */
    , ETC("ETC")
    ;

  private String code;

  LoginOrigin(String code) {
      this.code = code;
  }
 
  @MappedTypes(LoginOrigin.class)
  public static class TypeHandler extends CodeEnumTypeHandler<LoginOrigin> {
      public TypeHandler() {
          super(LoginOrigin.class);
      }
  }
   
  @Override
  @JsonValue
  public String getCode() {
      return code;
  }
}
