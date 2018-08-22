package kr.co.solbipos.application.session.auth.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 로그인 결과 enum type
 * 
 * @author bjcho
 */
public enum LoginResult implements CodeEnum {
    
    /** 로그인 성공 */
    SUCCESS("SUCC")
    /** 로그인 실패 */
    , FAIL("FAIL")
    /** 사용여부 */
    , NOT_USE_ID("USE")
    /** 존재하지 않는 유져 */
    , NOT_EXISTS_ID("NID")
    /** 패스워드 틀림 */
    , PASSWORD_ERROR("ERR")
    /** 패스워드 변경 필요한 상태 */
    , PASSWORD_TEMPORARY("CHG")
    /** 패스워드 유효 기간 지남 */
    , PASSWORD_EXPIRE("EXP")
    /** 로그인횟수 초과 */
    , LOGIN_FAIL_CNT_OVER("CNT")
    ;
    
  private String code;

  LoginResult(String code) {
      this.code = code;
  }
 
  @MappedTypes(LoginResult.class)
  public static class TypeHandler extends CodeEnumTypeHandler<LoginResult> {
      public TypeHandler() {
          super(LoginResult.class);
      }
  }
   
  @Override
  @JsonValue
  public String getCode() {
      return code;
  }}
