package kr.co.solbipos.store.manage.pwdmanage.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : PwdChgFg.java
 * @Description : 패스워드 변경 웹, 포스 구분
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.20  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public enum PwdChgFg implements CodeEnum  {

    /** 웹 비밀번호 */
    WEB("WEB")
    /** 포스 비밀번호 */
    , POS("POS")
    ;

  private String code;

  PwdChgFg(String code) {
      this.code = code;
  }
 
  @MappedTypes(PwdChgFg.class)
  public static class TypeHandler extends CodeEnumTypeHandler<PwdChgFg> {
      public TypeHandler() {
          super(PwdChgFg.class);
      }
  }
   
  @Override
  @JsonValue
  public String getCode() {
      return code;
  }
}
