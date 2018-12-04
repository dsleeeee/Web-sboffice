package kr.co.solbipos.base.store.emp.system.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Class Name : AdminFg.java
 * @Description : 사원정보관리 > 관리자 구분
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.27  김지은     최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.27
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum AdminFg implements CodeEnum {

    /** 사원번호 중복 */
    ADMIN("A"),
    /** 총판 */
    DISTRIBUTORS("P"),
    /** 대리점 */
    AGENCY("C")
    ;

    private String code;

    AdminFg(String code) {
        this.code = code;
    }

    @MappedTypes(AdminFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<AdminFg> {
        public TypeHandler() {
            super(AdminFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
