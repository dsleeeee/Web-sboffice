package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : NaverPlaceLinkVO.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.27  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.27
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverPlaceLinkVO extends PageVO {

    private static final long serialVersionUID = 6356311704414395872L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 네이버 UNIQUE Id*/
    private String uniqueId;
    /** 최종응답시간 */
    private String lastResponseDt;
    /** 이메일 주소 */
    private String mpNo;
    /** 동의목록 */
    private String agreementType;
    /** etc01 */
    private String etc01;
    /** etc02 */
    private String etc02;
    /** etc03 */
    private String etc03;
    /** etc04 */
    private String etc04;
    /** etc05 */
    private String etc05;
    /** etc06 */
    private String etc06;
    /** etc07 */
    private String etc07;
    /** etc08 */
    private String etc08;
    /** etc09 */
    private String etc09;
    /** etc10 */
    private String etc10;
    /** 네이버로그인후 기존세션 확인을 위한 임의값 */
    private String state;
    /** 사용자아이디 */
    private String userId;

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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLastResponseDt() {
        return lastResponseDt;
    }

    public void setLastResponseDt(String lastResponseDt) {
        this.lastResponseDt = lastResponseDt;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getEtc01() {
        return etc01;
    }

    public void setEtc01(String etc01) {
        this.etc01 = etc01;
    }

    public String getEtc02() {
        return etc02;
    }

    public void setEtc02(String etc02) {
        this.etc02 = etc02;
    }

    public String getEtc03() {
        return etc03;
    }

    public void setEtc03(String etc03) {
        this.etc03 = etc03;
    }

    public String getEtc04() {
        return etc04;
    }

    public void setEtc04(String etc04) {
        this.etc04 = etc04;
    }

    public String getEtc05() {
        return etc05;
    }

    public void setEtc05(String etc05) {
        this.etc05 = etc05;
    }

    public String getEtc06() {
        return etc06;
    }

    public void setEtc06(String etc06) {
        this.etc06 = etc06;
    }

    public String getEtc07() {
        return etc07;
    }

    public void setEtc07(String etc07) {
        this.etc07 = etc07;
    }

    public String getEtc08() {
        return etc08;
    }

    public void setEtc08(String etc08) {
        this.etc08 = etc08;
    }

    public String getEtc09() {
        return etc09;
    }

    public void setEtc09(String etc09) {
        this.etc09 = etc09;
    }

    public String getEtc10() {
        return etc10;
    }

    public void setEtc10(String etc10) {
        this.etc10 = etc10;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
