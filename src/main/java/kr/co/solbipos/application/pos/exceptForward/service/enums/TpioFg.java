package kr.co.solbipos.application.pos.exceptForward.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 기타출고 > 대체유형 enum type<br>
 * TB_NEOE_SPOS_EGR - FG_TPIO
 *
 * @author 김지은
 *
 */
public enum TpioFg implements CodeEnum {
    /** 폐기 */
    DISPOSAL("003"),
    /** 재고실사(출고) */
    INVENTORY("004"),
    /** 무상출고 */
    FREE_FORWARDING("005");

    String code;

    TpioFg(String code) {
        this.code = code;
    }

    @MappedTypes(TpioFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TpioFg> {
        public TypeHandler() {
            super(TpioFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
