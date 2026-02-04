package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service;

/**
 * @Class Name : DlvrAgencyLinkVO.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.02.03  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.02.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

public class DlvrAgencyLinkVO {

    private static final long serialVersionUID = 684736301181965505L;

    /** 매장코드 */
    private String storeCd;
    /** api 정보 */
    private String apiInfo;
    /** api url */
    private String apiUrl;
    /** api key */
    private String apiKey;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(String apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
