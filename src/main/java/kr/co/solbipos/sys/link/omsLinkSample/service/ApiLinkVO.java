package kr.co.solbipos.sys.link.omsLinkSample.service;


import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ApiLinkVO.java
 * @Description : 시스템관리 > 연동 > OMS연동샘플(TB_CM_API_LINK용 VO)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class ApiLinkVO extends PageVO {

    private static final long serialVersionUID = -7947089589273633175L;

    private int seq;
    private String linkType;
    private String url;
    private String requestMethod;
    private String request;
    private String response;
    private String requestDt;
    private String responseDt;
    private String statusCode;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequestDt() {
        return requestDt;
    }

    public void setRequestDt(String requestDt) {
        this.requestDt = requestDt;
    }

    public String getResponseDt() {
        return responseDt;
    }

    public void setResponseDt(String responseDt) {
        this.responseDt = responseDt;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
