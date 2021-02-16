package kr.co.solbipos.adi.dclz.dclzmanage.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * {@link kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO}<br>
 * {@code inFg} enum type<br>
 * @Class Name : DclzInFg.java
 * @Description : 근태 입력 구분
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public enum DclzInFg implements CodeEnum {

    /** 전체 */
    ALL("")
    /** 포스 입력 */
    ,POS("010")
    /** 웹 입력 */
    ,WEB("020")
    /**  */
    ,SVR("030");

    private String code;

    DclzInFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(DclzInFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<DclzInFg> {
        public TypeHandler() {
            super(DclzInFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
