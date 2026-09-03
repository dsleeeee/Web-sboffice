package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service;

import java.util.List;

/**
 * @Class Name  : NaverOrderApiVO.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 API VO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.13  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverOrderApiVO {

    private static final long serialVersionUID = -7341209876543210987L;

    /** 인증과정에 대한 내부 구분값(고정값) */
    private String code;
    /** 네이버로그인후 기존세션 확인을 위한 임의값 */
    private String state;
    /** 스마플 개인정보 약관 동의 목록 */
    private String projections;
    /** 네.아.로 Unique ID */
    private String uniqueId;
    /** 인증 토큰 */
    private String accessToken;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사용자아이디 */
    private String userId;
    /** 조회 대상 채널 */
    private String channelType;
    /** 페이징 no */
    private int page;
    /** 페이징 사이즈 */
    private int size;
    /** 매장명 */
    private String shopName;
    /** 사업자번호 */
    private String taxNo;
    /** 대표자명 */
    private String ceoName;
    /** 매장 전화번호 */
    private String shopTelNo;
    /** 대표자 전화번호 */
    private String ceoTelNo;
    /** 우편번호 */
    private String postNo;
    /** 지번주소 */
    private String jibun;
    /** 도로명주소 */
    private String roadAddr;
    /** 기본주소 */
    private String addrBase;
    /** 상세주소 */
    private String addrDetail;
    /** POS 매장 ID */
    private String posShopId;
    /** 채널 매장 ID (비즈니스 아이디) */
    private String channelShopId;
    /** 서비스 활성화 목록 */
    private List<ServiceItem> services;

    /** 서비스 활성화 항목 */
    public static class ServiceItem {

        /** 서비스 유형 (TABLE, PICKUP) */
        private String serviceType;
        /** 채널 서비스 ID */
        private Long channelServiceId;
        /** 사용 여부 */
        private Boolean useFlag;

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public Long getChannelServiceId() {
            return channelServiceId;
        }

        public void setChannelServiceId(Long channelServiceId) {
            this.channelServiceId = channelServiceId;
        }

        public Boolean getUseFlag() {
            return useFlag;
        }

        public void setUseFlag(Boolean useFlag) {
            this.useFlag = useFlag;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProjections() {
        return projections;
    }

    public void setProjections(String projections) {
        this.projections = projections;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getShopTelNo() {
        return shopTelNo;
    }

    public void setShopTelNo(String shopTelNo) {
        this.shopTelNo = shopTelNo;
    }

    public String getCeoTelNo() {
        return ceoTelNo;
    }

    public void setCeoTelNo(String ceoTelNo) {
        this.ceoTelNo = ceoTelNo;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getJibun() {
        return jibun;
    }

    public void setJibun(String jibun) {
        this.jibun = jibun;
    }

    public String getRoadAddr() {
        return roadAddr;
    }

    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    public String getAddrBase() {
        return addrBase;
    }

    public void setAddrBase(String addrBase) {
        this.addrBase = addrBase;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getPosShopId() {
        return posShopId;
    }

    public void setPosShopId(String posShopId) {
        this.posShopId = posShopId;
    }

    public String getChannelShopId() {
        return channelShopId;
    }

    public void setChannelShopId(String channelShopId) {
        this.channelShopId = channelShopId;
    }

    public List<ServiceItem> getServices() {
        return services;
    }

    public void setServices(List<ServiceItem> services) {
        this.services = services;
    }
}
