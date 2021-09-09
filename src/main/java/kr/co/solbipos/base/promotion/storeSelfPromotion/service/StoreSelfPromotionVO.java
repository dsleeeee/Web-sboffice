package kr.co.solbipos.base.promotion.storeSelfPromotion.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreSelfPromotionVO.java
 * @Description : 기초관리 - 프로모션관리 - 매장자체프로모션현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.07  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .09. 07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreSelfPromotionVO extends PageVO {

    private static final long serialVersionUID = -4042206289823098930L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 (조회용) */
    private String srchStoreCd;
    /** 다중매장코드 array */
    private String arrStoreCd[];
    /** 조회 시작 일자 */
    private String startDate;
    /** 조회 종료 일자 */
    private String endDate;
    /** 프로모션코드 */
    private String promotionCd;
    /** 혜택순번 */
    private String beneSeq;

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

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getBeneSeq() {
        return beneSeq;
    }

    public void setBeneSeq(String beneSeq) {
        this.beneSeq = beneSeq;
    }
}
