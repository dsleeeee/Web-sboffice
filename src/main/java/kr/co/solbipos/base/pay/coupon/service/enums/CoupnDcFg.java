package kr.co.solbipos.base.pay.coupon.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : CouponApplyFg.java
 * @Description : 쿠폰할인구분 enum type
 *                TB_HQ_COUPON, TB_MS_COUPON > COUPN_DC_FG
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

public enum CoupnDcFg implements CodeEnum {

    /** 전체 % 할인 */
    TOT_PER("1"),
    /** 상품 % 할인 */
    PROD_PER("2"),
    /** 전체 금액 할인 */
    TOT_AMT("3"),
    /** 상품 금액 할인 */
    PROD_AMT("4"),
    /** 상품 무료 제공 */
    PROD_FREE("5"),
    /** 전체 상품금액 할인 */
    TOT_PROD_AMT("6");

    private String code;
    private CoupnDcFg[] values;

    CoupnDcFg(String code, CoupnDcFg... values) {
        this.code = code;
        this.values = values;
    }

    public CoupnDcFg[] getValues() {
        return values;
    }

    @MappedTypes(CoupnDcFg.class)
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

    public static CoupnDcFg getEnum(String code) {
        List<CoupnDcFg> list = Arrays.asList(CoupnDcFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
