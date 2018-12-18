package kr.co.solbipos.membr.anals.prepaid.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 충전수단구분 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum PrepaidPayFg implements CodeEnum {

    /** 신용카드 */
    CREDITCARD("1"),
    /** 현금 */
    CASH("2"),
    /** 계좌이체 */
    TRANSFER("3");

    private String code;

    PrepaidPayFg(String code) {
        this.code = code;
    }

    @MappedTypes(PrepaidPayFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PrepaidPayFg> {
        public TypeHandler() {
            super(PrepaidPayFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
