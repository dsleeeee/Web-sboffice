package kr.co.solbipos.membr.anals.prepaid.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 충전구분 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum PrepaidInFg implements CodeEnum {

    /** 충전 */
    CHARGE("1"),
    /** 취소 */
    CANCEL("2");

    private String code;

    PrepaidInFg(String code) {
        this.code = code;
    }

    @MappedTypes(PrepaidInFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PrepaidInFg> {
        public TypeHandler() {
            super(PrepaidInFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
