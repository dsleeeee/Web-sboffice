package kr.co.solbipos.sys.link.naverPlaceEasyLink.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : NaverPlaceEasyLinkVO.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스 간편연동 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverPlaceEasyLinkVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 네이버 플레이스 플러스 유입경로 [LYNK, NAVER] */
    private String inType;
    /** 간편연동업로드 임시저장 세션ID */
    private String sessionId;
    /** 간편연동업로드 임시저장 순번 */
    private Integer seq;
    /** 간편연동업로드 임시저장 처리된 갯수(청크 시작 offset) */
    private Integer progressCnt;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 네이버 플레이스ID */
    private String placeId;
    /** 업체명 */
    private String businessName;
    /** 사업자번호 */
    private String businessNumber;
    /** 검증결과 */
    private String result;
    /** 사업자번호 */
    private String bizNo;

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getProgressCnt() {
        return progressCnt;
    }

    public void setProgressCnt(Integer progressCnt) {
        this.progressCnt = progressCnt;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
}
