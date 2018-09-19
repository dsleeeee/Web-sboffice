package kr.co.common.exception;

import kr.co.common.data.enums.CodeType;
import kr.co.common.data.enums.Status;

/**
 * @Class Name : BizException.java
 * @Description : 업무관련 예외
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  이호원      최초생성
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
    /** 코드 구분 (코드, 환경변수 Exception 처리시 사용) */
    private CodeType codeType;
    /** 코드 (공통코드 또는 환경변수코드) */
    private String codeCd;

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
     * @param codeType
     * @param codeCd
     * @param responseURL
     */
    public BizException(CodeType codeType, String codeCd, String responseURL) {
        //super(code);
        this.codeCd = codeCd;
        this.responseURL = responseURL;
        this.codeType = codeType;
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
    /**
     * @return the codeType
     */
    public CodeType getCodeType() { return codeType; }
    /**
     * @param codeType the codeType to set
     */
    public void setCodeType(CodeType codeType) { this.codeType = codeType; }

    /**
     * @return the codeCd
     */
    public String getCodeCd() { return codeCd; }

    /**
     * @param codeCd the codeCd to set
     */
    public void setCodeCd(String codeCd) { this.codeCd = codeCd; }
}
