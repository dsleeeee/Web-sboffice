package kr.co.solbipos.base.prod.prod.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : HqProdEnvFg.java
 * @Description : 본사신규상품매장생성 enum type
 *                TB_HQ_ENVST
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum HqProdEnvFg implements CodeEnum {

    /** 전매장 */
    ALL("0"),
    /** 생성안함 */
    NO("1");

    private String code;
    private HqProdEnvFg[] values;

    HqProdEnvFg(String code, HqProdEnvFg... values) {
        this.code = code;
        this.values = values;
    }

    public HqProdEnvFg[] getValues() {
        return values;
    }

    @MappedTypes(HqProdEnvFg.class)
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

    public static HqProdEnvFg getEnum(String code) {
        List<HqProdEnvFg> list = Arrays.asList(HqProdEnvFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}