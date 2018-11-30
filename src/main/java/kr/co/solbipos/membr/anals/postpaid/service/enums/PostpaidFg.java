package kr.co.solbipos.membr.anals.postpaid.service.enums;

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
public enum PostpaidFg implements CodeEnum {

    /** 입금 */
    DEPOSIT("1"),
    /** 취소 */
    CANCEL("2");

    private String code;

    PostpaidFg(String code) {
        this.code = code;
    }

    @MappedTypes(PostpaidFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PostpaidFg> {
        public TypeHandler() {
            super(PostpaidFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
