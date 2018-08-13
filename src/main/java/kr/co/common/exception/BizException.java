package kr.co.common.exception;

import kr.co.common.data.enums.Status;

/**
 * @Class Name : BizException.java
 * @Description : 업무관련 예외
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  이호원      최초생성
 *
 * @author NHN한국사이버결제 KCP 이호원
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
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
     */
    public BizException(Status status, String message) {
        super(message);
        this.status = status;
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

    
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    /**
     * @return the responseURL
     */
    public String getResponseURL() {
        return responseURL;
    }
    /**
     * @param responseURL the responseURL to set
     */
    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }
    
}
