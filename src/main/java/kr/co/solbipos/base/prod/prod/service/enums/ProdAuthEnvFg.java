package kr.co.solbipos.base.prod.prod.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : ProdAuthEnvFg.java
 * @Description : 상품생성설정 enum type
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

public enum ProdAuthEnvFg implements CodeEnum {

    /** 본사/매장생성 */
    ALL("0"),
    /** 본사생성 */
    HQ("1"),
    /** 매장생성 */
    STORE("2");

    private String code;
    private ProdAuthEnvFg[] values;

    ProdAuthEnvFg(String code, ProdAuthEnvFg... values) {
        this.code = code;
        this.values = values;
    }

    public ProdAuthEnvFg[] getValues() {
        return values;
    }

    @MappedTypes(ProdAuthEnvFg.class)
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

    public static ProdAuthEnvFg getEnum(String code) {
        List<ProdAuthEnvFg> list = Arrays.asList(ProdAuthEnvFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}