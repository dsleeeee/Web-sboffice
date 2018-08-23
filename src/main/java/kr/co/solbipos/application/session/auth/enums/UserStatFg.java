package kr.co.solbipos.application.session.auth.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : UserStatFg.java
 * @Description : 사용자상태구분 enum type
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.22  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 08.22
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public enum UserStatFg implements CodeEnum {

    /** 정상 */
    NORMAL("00")
    /** 로그인횟수 초과 */
    , LOGIN_FAIL_CNT_OVER("10")
    /** 비밀번호 만료 */
    , PASSWORD_EXPIRE("20")
    /** 로그인이후 90일경과 */
    , LOGIN_AFTER_90("30")
    /** 로그인이후 180일경과 */
    , LOGIN_AFTER_180("31")
    /** 휴면계정 */
    , DORMANT_ACCOUNT("40")
    /** 일시정지 */
    , SUSPEND_ACCOUNT("50")
    /** 초기비밀번호 */
    , PASSWORD_TEMPORARY("99")
    ;

  private String code;

  UserStatFg(String code) {
      this.code = code;
  }
 
  @MappedTypes(UserStatFg.class)
  public static class TypeHandler extends CodeEnumTypeHandler<UserStatFg> {
      public TypeHandler() {
          super(UserStatFg.class);
      }
  }
   
  @Override
  @JsonValue
  public String getCode() {
      return code;
  }}
