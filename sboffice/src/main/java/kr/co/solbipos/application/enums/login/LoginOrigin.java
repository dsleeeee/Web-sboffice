package kr.co.solbipos.application.enums.login;

import kr.co.solbipos.enums.EnumValue;

/**
 * {@link kr.co.solbipos.application.domain.login.LoginHist}<br>
 * {@code loginOrgn} enum type<br>
 * 로그인 시도 웹, 포스 구분
 * 
 * @author 정용길
 */
public enum LoginOrigin implements EnumValue<String> {
    
    /** 웹 로그인 */
    WEB("WEB")
    /** 포스 로그인 */
    , POS("POS")
    /** 기타 */
    , ETC("ETC")
    ;
    
    private final String origin;
    
    LoginOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String getValue() {
        return origin;
    }
}
