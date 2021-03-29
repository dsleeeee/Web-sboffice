package kr.co.solbipos.mobile.application.session.auth.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : LoginFg.java
 * @Description : 로그인 구분 enum type
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021. 03. 23
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum LoginFg implements CodeEnum {

    /** 모바일 */
    MOBILE("M")
    /** 웹 */
    , WEB("W")
    ;

    private String code;

    LoginFg(String code) {
        this.code = code;
    }

    @MappedTypes(LoginFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<LoginFg> {
        public TypeHandler() {
            super(LoginFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
