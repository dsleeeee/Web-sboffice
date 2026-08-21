package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service;

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
}
