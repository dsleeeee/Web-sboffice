package kr.co.solbipos.base.pay.coupon.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : PayTypeFg.java
 * @Description : 권종유형구분 enum type
 *                TB_HQ_PAY_METHOD_CLASS, TB_MS_PAY_METHOD_CLASS > PAY_TYPE_FG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.10  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum PayTypeFg implements CodeEnum {

    /** 상품권 */
    GIFT("0"),
    /** 식권 */
    FOOD_TICKET("1"),
    /** 쿠폰 */
    COUPON("3");

    private String code;
    private PayTypeFg[] values;

    PayTypeFg(String code, PayTypeFg... values) {
        this.code = code;
        this.values = values;
    }

    public PayTypeFg[] getValues() {
        return values;
    }

    @MappedTypes(PayTypeFg.class)
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

    public static PayTypeFg getEnum(String code) {
        List<PayTypeFg> list = Arrays.asList(PayTypeFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
