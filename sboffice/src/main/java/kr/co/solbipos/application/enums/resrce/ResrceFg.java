package kr.co.solbipos.application.enums.resrce;

import kr.co.solbipos.enums.EnumValue;

/**
 * 리소스 구분<br>
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author 정용길
 *
 */
public enum ResrceFg implements EnumValue<String> {
    
    /** 리소스 : 메뉴 */
    MENU("W001")
    /** 리소스 : 기능 */
    , FUNC("W002");     
    
    private final String value;
    
    ResrceFg(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
