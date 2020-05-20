package kr.co.solbipos.base.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 설정 구분 enum type<br>
 * TB_WB_STORE_CONFG_XML > ConfFg
 *
 * @author bjcho
 *
 */
public enum ConfgFg implements CodeEnum {

    /** 터치키 */
    TOUCH_KEY("10"),
    /** 테이블 구성 */
    TABLE_LAYOUT("20"),
    /** 테이블 속성(유형) */
    TABLE_ATTR_TYPE("30"),
    /** 테이블 속성(번호) */
    TABLE_ATTR_NUM("40"),
    /** 포스기능키 : 좌측 */
    FUNC_KEY_LEFT("6020"),
    /** 포스기능키 : 우측 */
    FUNC_KEY_RIGHT("6021"),
    /** 포스기능키 : 배달메뉴 */
    FUNC_KEY_DELIVERY("6022");

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
