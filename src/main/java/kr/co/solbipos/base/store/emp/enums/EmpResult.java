package kr.co.solbipos.base.store.emp.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : EmpCheckResult.java
 * @Description : 사원정보관리 사원 등록시 유효성 체크
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 * @ 2018.11.26  김지은     angular 방식으로 변경 및 로직 수정
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum EmpResult implements CodeEnum {

    /** 사원번호 중복 */
    EMP_NO_DUPLICATE("EMP_NO_DUPLICATE"),
    /** 웹 사용자 ID 중복 */
    USER_ID_DUPLICATE("USER_ID_DUPLICATE"),
    /** 처리 성공 */
    SUCCESS("SUCCESS"),
    /** 처리 실패 */
    FAIL("FAIL"),
    /** 비밀번호 일치 하지 않음 */
    PASSWORD_NOT_MATCH("PASSWORD_NOT_MATCH"),
    /** 변경 패스워드가 기존 패스워드와 동일함. */
    PASSWORD_NOT_CHANGED("PASSWORD_NOT_CHANGED"),
    /** 패스워드 정책이 맞지 않음 */
    PASSWORD_REGEXP("PASSWORD_REGEXP"),
    /** 웹 사용자ID 정책이 맞지 않음 */
    USER_ID_REGEXP("USER_ID_REGEXP"),
    /** 사용자 아이디는 8자 이상 12자 이하로 입력해주세요. */
    USER_ID_LENGHTH_REGEXP("USER_ID_LENGHTH_REGEXP"),
    /** 한글을 사용할 수 없습니다. */
    USER_ID_CANNOT_USE_HANGEUL("USER_ID_CANNOT_USE_HANGEUL"),
    /** 아이디는 영문자가 반드시 포함되어야 합니다. */
    USER_ID_MUST_CONTAIN_ENG_CAHR("USER_ID_MUST_CONTAIN_ENG_CAHR"),
    /** 아이디는 영문과 숫자만 가능합니다. */
    USER_ID_ONLY_ENG_NUM_CHAR("USER_ID_ONLY_ENG_NUM_CHAR"),
    /** 사원번호 정책이 맞지 않음 */
    EMP_NO_REGEXP("EMP_NO_REGEXP")
    ;

    private String code;

    EmpResult(String code) {
        this.code = code;
    }

    @MappedTypes(EmpResult.class)
    public static class TypeHandler extends CodeEnumTypeHandler<EmpResult> {
        public TypeHandler() {
            super(EmpResult.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
