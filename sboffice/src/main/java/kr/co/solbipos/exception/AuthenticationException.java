package kr.co.solbipos.exception;

/**
 * 인증 예외
 * 
 * @author I_hwlee
 */
public class AuthenticationException extends BizException {
    private static final long serialVersionUID = 8780373647723701193L;

    public AuthenticationException(String message, String responseURL) {
        super(message, responseURL);
    }
}
