package kr.co.solbipos.membr.info.regist.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 사용 여부 Y:사용, N:미사용 enum type<br>
 * 프로젝트 전체에서 사용
 *
 * @author bjcho
 *
 */
public enum WeddingYn implements CodeEnum {

    /** 기혼 */
    Y("Y"),
    /** 미혼 */
    N("N");

    private String code;

    WeddingYn(String code) {
        this.code = code;
    }

    @MappedTypes(WeddingYn.class)
    public static class TypeHandler extends CodeEnumTypeHandler<WeddingYn> {
        public TypeHandler() {
            super(WeddingYn.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
