package kr.co.solbipos.membr.info.regist.service.enums;

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
public enum CreditPayFg implements CodeEnum {

    /** 신용카드 */
    CREDITCARD("1"),
    /** 현금 */
    CASH("2"),
    /** 계좌이체 */
    TRANSFER("3");

    private String code;

    CreditPayFg(String code) {
        this.code = code;
    }

    @MappedTypes(CreditPayFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<CreditPayFg> {
        public TypeHandler() {
            super(CreditPayFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
