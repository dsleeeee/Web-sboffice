package kr.co.solbipos.exception;

import kr.co.solbipos.structure.Result.Status;

/**
 * 
 * 
 * @author 정용길
 *
 */
public class JsonCallException extends BizException {
    private static final long serialVersionUID = 8780373647723701193L;
    
    public JsonCallException(Status status, String message, String responseURL) {
        super(status, message, responseURL);
    }
}
