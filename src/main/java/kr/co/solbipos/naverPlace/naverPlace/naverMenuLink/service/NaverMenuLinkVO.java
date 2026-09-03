package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : NaverMenuLinkVO.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.19  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverMenuLinkVO extends PageVO {

    private static final long serialVersionUID = -1234567890123456789L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 네.아.로 Unique ID */
    private String uniqueId;
    /** API 정보 구분값 */
    private String apiInfo;
    /** API URL 컬럼 구분값 */
    private String apiUrl;
    /** API KEY 컬럼 구분값 */
    private String apiKey;
    /** 링크 매장코드 */
    private String posShopId;
    /** 네이버 옵션 ID */
    private String optionId;
    /** 옵션(상품)명 */
    private String name;
    /** 사용 여부 (Y/N) */
    private String useYn;
    /** 네이버 메뉴 연동 여부 (Y:연동, N:미연동, "":전체) */
    private String naverMenuLinkYn;
    /** 상품 맵핑코드(링크 포스 상품코드) */
    private String agencyKey;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 등록일 검색기간 전체 체크 여부 */
    private boolean chkDt;

    public String getHqOfficeCd() { return hqOfficeCd; }
    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }

    public String getApiInfo() { return apiInfo; }
    public void setApiInfo(String apiInfo) { this.apiInfo = apiInfo; }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getPosShopId() { return posShopId; }
    public void setPosShopId(String posShopId) { this.posShopId = posShopId; }

    public String getOptionId() { return optionId; }
    public void setOptionId(String optionId) { this.optionId = optionId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getNaverMenuLinkYn() { return naverMenuLinkYn; }
    public void setNaverMenuLinkYn(String naverMenuLinkYn) { this.naverMenuLinkYn = naverMenuLinkYn; }

    public String getAgencyKey() {
        return agencyKey;
    }

    public void setAgencyKey(String agencyKey) {
        this.agencyKey = agencyKey;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }
}
