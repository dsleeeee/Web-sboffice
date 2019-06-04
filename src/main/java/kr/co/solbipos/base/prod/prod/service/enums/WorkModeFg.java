package kr.co.solbipos.base.prod.prod.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : WorkModeFg.java
 * @Description : 상품등록 본사통제구분 enum type
 *                TB_HQ_ENVST
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.06.06  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT  이다솜
 * @since 2019.06.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum WorkModeFg implements CodeEnum {

    /** 상품정보수정 */
    MOD_PROD("1"),
    /** 신규상품등록 */
    REG_PROD("2"),
    /** 매장등록 */
    REG_STORE("3");

    private String code;
    private WorkModeFg[] values;

    WorkModeFg(String code, WorkModeFg... values) {
        this.code = code;
        this.values = values;
    }

    public WorkModeFg[] getValues() {
        return values;
    }

    @MappedTypes(WorkModeFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PayTypeFg> {
        public TypeHandler() {
            super(PayTypeFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    public static WorkModeFg getEnum(String code) {
        List<WorkModeFg> list = Arrays.asList(WorkModeFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
