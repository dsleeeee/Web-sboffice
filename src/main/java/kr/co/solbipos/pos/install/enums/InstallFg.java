package kr.co.solbipos.pos.install.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 설치구분 enum type 009<br>
 * TB_CM_INSTLL > INST_FG
 *
 * @author jekim
 *
 */
public enum InstallFg implements CodeEnum {

    /** 설치의뢰 */
    REQ("0"),
    /** 신규설치 */
    NEW("1"),
    /** 재설치 */
    RE_INST("2");

    private String code;

    InstallFg(String code) {
        this.code = code;
    }

    @MappedTypes(InstallFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<InstallFg> {
        public TypeHandler() {
            super(InstallFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
