package kr.co.solbipos.membr.anals.prepaid.enums;

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
public enum PrepaidFg implements CodeEnum {

    /** 충전 */
    CHARGE("1"),
    /** 충전취소 */
    CANCEL("2"),
    /** 사용 */
    USE("3"),
    /** 사용취소 */
    USE_CANCEL("4");

    private String code;

    PrepaidFg(String code) {
        this.code = code;
    }

    @MappedTypes(PrepaidFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PrepaidFg> {
        public TypeHandler() {
            super(PrepaidFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
