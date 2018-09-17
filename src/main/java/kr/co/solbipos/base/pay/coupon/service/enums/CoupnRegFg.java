package kr.co.solbipos.base.pay.coupon.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : CoupnRegFg.java
 * @Description : 쿠폰등록구분 enum type
                    TB_MS_COUPON
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.22  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum CoupnRegFg implements CodeEnum {

    /** 매장 등록 */
    STORE("S"),
    /** 본사 등록 */
    HQ("H");

    private String code;
    private CoupnRegFg[] values;

    CoupnRegFg(String code, CoupnRegFg... values) {
        this.code = code;
        this.values = values;
    }

    public CoupnRegFg[] getValues() {
        return values;
    }

    @MappedTypes(CoupnRegFg.class)
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

    public static CoupnRegFg getEnum(String code) {
        List<CoupnRegFg> list = Arrays.asList(CoupnRegFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
