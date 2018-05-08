package kr.co.common.exception;

import kr.co.common.data.enums.Status;

/**
 * 
 * 
 * @author 정용길
 *
 */
public class JsonException extends BizException {
    private static final long serialVersionUID = 8780373647723701193L;
    
    /**
     * @param status
     * @param message
     */
    public JsonException(Status status, String message) {
        super(status, message);
    }
    
    /**
     * @param status
     * @param message
     * @param responseURL
     */
    public JsonException(Status status, String message, String responseURL) {
        super(status, message, responseURL);
    }
}
