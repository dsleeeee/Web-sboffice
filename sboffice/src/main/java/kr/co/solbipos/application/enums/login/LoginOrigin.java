package kr.co.solbipos.application.enums.login;

import kr.co.solbipos.enums.EnumValue;

/**
 * @author 정용길
 *
 */
public enum LoginOrigin implements EnumValue<String> {
    
    WEB("WEB")      /** 웹 로그인 */
    , POS("POS")    /** 포스 로그인 */
    , ETC("ETC");
    
    private final String origin;
    
    LoginOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String getValue() {
        return origin;
    }
}
