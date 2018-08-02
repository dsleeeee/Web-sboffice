package kr.co.common.exception;

/**
 * 로그인, 패스워드 관련, url 인증 실패
 * 
 * @author 정용길
 *
 */
public class AuthenticationException extends BizException {
    private static final long serialVersionUID = 8780373647723701193L;

    public AuthenticationException(String message, String responseURL) {
        super(message, responseURL);
    }
}
