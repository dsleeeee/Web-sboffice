package kr.co.solbipos.exception;

import kr.co.solbipos.enums.Status;
import lombok.Data;

/**
 * 업무관련 예외
 * 
 * @author 이호원
 */
@Data
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3209717720718123566L;
    
    /** 응답 유형 */
    private Status status;
    
    /** 리턴 URL */
    private String responseURL = "";

    /**
     * @param message
     */
    public BizException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param responseURL
     */
    public BizException(String message, String responseURL) {
        super(message);
        this.responseURL = responseURL;
    }

    /**
     * @param status
     * @param message
     * @param responseURL
     */
    public BizException(Status status, String message, String responseURL) {
        super(message);
        this.responseURL = responseURL;
        this.status = status;
    }
}
