package kr.co.solbipos.membr.anals.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 세금계산서 발행구분 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum StatusFg implements CodeEnum {

    /** 발행요청 */
    REQEUST("1"),
    /** 완료 */
    COMPLETE("2");

    private String code;

    StatusFg(String code) {
        this.code = code;
    }

    @MappedTypes(StatusFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StatusFg> {
        public TypeHandler() {
            super(StatusFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
