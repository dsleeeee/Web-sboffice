package kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : RtnInstockConfmStoreVO.java
 * @Description : 수불관리 > 반품관리 > 반품본사입고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RtnInstockConfmStoreVO extends PageVO {

    private static final long serialVersionUID = 8385332888335216795L;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 전표번호 YYMM(4)+SEQ(6) */
    private String slipNo;
    /** 매장코드 */
    private String storeCd;
    /** 처리구분 TB_CM_NMCODE(NMCODE_GRP_CD='086') 10:분배확정 20:출고확정 30:입고확정 */
    private String procFg;
    /** 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD='087') 0:일반 1:물량오류 2:이동 */
    private String slipKind;
    /** 전표구분 1:발주 -1:반출 */
    private Integer slipFg;
    /** 거래처코드 */
    private String vendrCd;
    /** 배송기사코드 */
    private String dlvrCd;
    /** 매장명 */
    private String storeNm;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public String getSlipKind() {
        return slipKind;
    }

    public void setSlipKind(String slipKind) {
        this.slipKind = slipKind;
    }

    public Integer getSlipFg() {
        return slipFg;
    }

    public void setSlipFg(Integer slipFg) {
        this.slipFg = slipFg;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getDlvrCd() {
        return dlvrCd;
    }

    public void setDlvrCd(String dlvrCd) {
        this.dlvrCd = dlvrCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }
}
