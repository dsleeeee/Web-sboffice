package kr.co.solbipos.base.prod.prod.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @Class Name : ProdNoEnvFg.java
 * @Description : 상품등록 상품코드 채번방식
 *                TB_MS_STORE_ENVST
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.05   이다솜      최초생성
 *
 * @author 솔비포스 백엔드PT 이다솜
 * @since 2019.12.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public enum ProdNoEnvFg implements CodeEnum {

    /** 자동채번 */
    AUTO("0"),
    /** 수동채번 */
    MANUAL("1");

    private String code;
    private ProdNoEnvFg[] values;

    ProdNoEnvFg(String code, ProdNoEnvFg... values) {
        this.code = code;
        this.values = values;
    }

    public ProdNoEnvFg[] getValues() {
        return values;
    }

    @MappedTypes(ProdNoEnvFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ProdNoEnvFg> {
        public TypeHandler() {
            super(ProdNoEnvFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    public static ProdNoEnvFg getEnum(String code) {
        List<ProdNoEnvFg> list = Arrays.asList(ProdNoEnvFg.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }

}
