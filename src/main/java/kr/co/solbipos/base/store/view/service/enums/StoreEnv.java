package kr.co.solbipos.base.store.view.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : StoreEnv.java
 * @Description : 복사할 매장환경 종류 enum type
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.29  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.29
 * @version 10
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum StoreEnv implements CodeEnum {

    /** 매장환경 */
    STORE_ENV("01"),
    /** 외식환경 */
    FOOD_ENV("02"),
    /** 포스환경 */
    POS_ENV("03"),
    /** 상품 */
    PROD("04"),
    /** 판매가격 */
    SALE_PRICE("05"),
    /** 포스기능키 */
    POS_FNKEY("06"),
    /** 판매터치키 */
    POS_TOUCHKEY("07"),
    /** 쿠폰분류 */
    COUPON_CLASS("08"),
    /** 상품권 */
    GIFT("09"),
    /** 입금/출금계정 */
    ACCOUNT("10"),
    /** 원산지 */
    RECP_ORIGIN("11"),
    /** 식품 알레르기 */
    FOOD_ALLERGY("12");

    private String code;
    private StoreEnv[] values;

    StoreEnv(String code, StoreEnv... values) {
        this.code = code;
        this.values = values;
    }

    public StoreEnv[] getValues() {
        return values;
    }

    @MappedTypes(StoreEnv.class)
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

    public static StoreEnv getEnum(String code) {
        List<StoreEnv> list = Arrays.asList(
            StoreEnv.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
