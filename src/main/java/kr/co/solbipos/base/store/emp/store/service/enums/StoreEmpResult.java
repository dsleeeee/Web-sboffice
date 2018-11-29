package kr.co.solbipos.base.store.emp.store.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : StoreEmpResult.java
 * @Description : 기초관리 > 매장관리 > 매장사원정보 등록 결과
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

public enum StoreEmpResult implements CodeEnum {

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
    /** 사원번호 정책이 맞지 않음 */
    EMP_NO_REGEXP("EMP_NO_REGEXP")
    ;

    private String code;

    StoreEmpResult(String code) {
        this.code = code;
    }

    @MappedTypes(StoreEmpResult.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StoreEmpResult> {
        public TypeHandler() {
            super(StoreEmpResult.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
