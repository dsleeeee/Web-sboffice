package kr.co.solbipos.exception;

/**
 * 업무관련 예외
 * 
 * @author 이호원
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3209717720718123566L;

    private String responseURL = "";

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, String responseURL) {
        super(message);
        this.responseURL = responseURL;
    }

    public String getResponseURL() {
        return responseURL;
    }
}
