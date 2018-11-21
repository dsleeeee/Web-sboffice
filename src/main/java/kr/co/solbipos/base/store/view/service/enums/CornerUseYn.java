package kr.co.solbipos.base.store.view.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : TerminalEnvFg.java
 * @Description : 코너사용여부 enum type
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.10
 * @version 10
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum CornerUseYn implements CodeEnum {

    /** 코너 미사용 */
    NO_CORNER("0"),
    /** 코너 사용 */
    USE_CORNER("2"),
    /** 포스별 */
    USE_POS("3");

    private String code;
    private CornerUseYn[] values;

    CornerUseYn(String code, CornerUseYn... values) {
        this.code = code;
        this.values = values;
    }

    public CornerUseYn[] getValues() {
        return values;
    }

    @MappedTypes(CornerUseYn.class)
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

    public static CornerUseYn getEnum(String code) {
        List<CornerUseYn> list = Arrays.asList(
            CornerUseYn.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
