package kr.co.solbipos.exception;

import kr.co.solbipos.structure.Result.Status;
import lombok.Data;

/**
 * 업무관련 예외
 * 
 * @author 이호원
 */
@Data
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3209717720718123566L;

    private String responseURL = "";
    
    private Status status;

    public BizException(String message) {
        super(message);
    }

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
