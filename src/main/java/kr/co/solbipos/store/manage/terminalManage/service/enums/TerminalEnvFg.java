package kr.co.solbipos.store.manage.terminalManage.service.enums;

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
 * @ 2018.08.10  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum TerminalEnvFg implements CodeEnum {

    /** 코너-미사용 */
    NO_CORNER("0"),
    /** 매장단위승인*/
    USE_STORE("1"),
    /** 코너개별승인 */
    USE_CORNER("2"),
    /** 포스별승인 */
    USE_POS("3");

    private String code;
    private TerminalEnvFg[] values;

    TerminalEnvFg(String code, TerminalEnvFg... values) {
        this.code = code;
        this.values = values;
    }

    public TerminalEnvFg[] getValues() {
        return values;
    }

    @MappedTypes(TerminalEnvFg.class)
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

    public static TerminalEnvFg getEnum(String code) {
        List<TerminalEnvFg> list = Arrays.asList(TerminalEnvFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
