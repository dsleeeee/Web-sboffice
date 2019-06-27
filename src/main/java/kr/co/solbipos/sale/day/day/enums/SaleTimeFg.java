package kr.co.solbipos.sale.day.day.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import kr.co.solbipos.membr.anals.enums.StatusFg;
import org.apache.ibatis.type.MappedTypes;

/**
 * 세금계산서 발행구분 enum type<br>
 *
 * @author dslee
 *
 */
public enum SaleTimeFg implements CodeEnum {
    /** 심야 */
    NIGHT("0"),
    /** 아침 */
    MORNING("1"),
    /** 점심 */
    LUNCH("2"),
    /** 저녁 */
    EVENING("3");

    private String code;

    SaleTimeFg(String code) {
        this.code = code;
    }

    @MappedTypes(SaleTimeFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<SaleTimeFg> {
        public TypeHandler() {
            super(SaleTimeFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
