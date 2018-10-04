package kr.co.solbipos.membr.anals.credit.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 입금구분 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum CreditInFg implements CodeEnum {

    /** 입금 */
    DEPOSIT("1"),
    /** 취소 */
    CANCEL("2");

    private String code;

    CreditInFg(String code) {
        this.code = code;
    }

    @MappedTypes(CreditInFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<CreditInFg> {
        public TypeHandler() {
            super(CreditInFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
