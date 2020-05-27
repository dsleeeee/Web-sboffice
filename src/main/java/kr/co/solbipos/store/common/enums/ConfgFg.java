package kr.co.solbipos.store.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * XML 설정구분
 * TB_HQ_ENVST
 *
 * @author bjcho
 *
 */
public enum ConfgFg implements CodeEnum {

    /** 터치키 */
    TOUCH_KEY("10"),
    /** 테이블구성 */
    TABLE_CONF("20"),
    /** 테이블속성 */
    TABLE_ATTR("30"),
    /** 포스기능키(좌) */
    POS_FN_LEFT("6020"),
    /** 포스기능키(우) */
    POS_FN_RIGHT("6021"),
    /** 배달메뉴 */
    POS_FN_DELIVERY ("6022");

    private String code;

    ConfgFg(String code) {
        this.code = code;
    }

    @MappedTypes(ConfgFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ConfgFg> {
        public TypeHandler() {
            super(ConfgFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

}
