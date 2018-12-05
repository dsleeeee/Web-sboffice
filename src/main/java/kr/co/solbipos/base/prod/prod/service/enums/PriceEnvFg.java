package kr.co.solbipos.base.prod.prod.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : PriceEnvFg.java
 * @Description : 판매가 본사통제구분 enum type
 *                TB_HQ_ENVST
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.05  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum PriceEnvFg implements CodeEnum {

    /** 본사통제 */
    HQ("1"),
    /** 매장통제 */
    STORE("2");

    private String code;
    private PriceEnvFg[] values;

    PriceEnvFg(String code, PriceEnvFg... values) {
        this.code = code;
        this.values = values;
    }

    public PriceEnvFg[] getValues() {
        return values;
    }

    @MappedTypes(PriceEnvFg.class)
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

    public static PriceEnvFg getEnum(String code) {
        List<PriceEnvFg> list = Arrays.asList(PriceEnvFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
