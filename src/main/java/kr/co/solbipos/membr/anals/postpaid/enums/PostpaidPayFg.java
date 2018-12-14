package kr.co.solbipos.membr.anals.postpaid.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 입금수단구분 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum PostpaidPayFg implements CodeEnum {

    /** 신용카드 */
    CREDITCARD("1"),
    /** 현금 */
    CASH("2"),
    /** 계좌이체 */
    TRANSFER("3");

    private String code;

    PostpaidPayFg(String code) {
        this.code = code;
    }

    @MappedTypes(PostpaidPayFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PostpaidPayFg> {
        public TypeHandler() {
            super(PostpaidPayFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
