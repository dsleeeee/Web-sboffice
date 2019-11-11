package kr.co.solbipos.pos.license.instlManage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.install.enums.InstallFg;

/**
 * @Class Name : InstlManageVO.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.15  이다솜      최초생성
 *
 * @author 솔비포스 차세대개발실 이다솜
 * @since 2019.10.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class InstlManageVO extends PageVO {

    /** 소속구분 */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 포스번호 */
    private String posNo;

    /** 순번 */
    private String seqNo;

    /** 설치구분 */
    private InstallFg instFg;

    /** 대리점코드 */
    private String agencyCd;

    /** 대리점명 */
    private String agencyNm;

    /** 설치요청일 */
    private String instReqDt;

    /** 설치요청ID */
    private String instReqId;

    /** 설치일 */
    private String instInsDt;

    /** 설치ID */
    private String instInsId;

    /** 설치사유 */
    private String instReason;

    /** 비고 */
    private String remark;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;


    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the hqOfficeCd
     */

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the hqOfficeNm
     */

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    /**
     * @return the storeCd
     */

    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the storeNm
     */

    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the posNo
     */

    public String getPosNo() {
        return posNo;
    }

    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    /**
     * @return the seqNo
     */

    public String getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * @return the instFg
     */

    public InstallFg getInstFg() {
        return instFg;
    }

    /**
     * @param instFg the instFg to set
     */
    public void setInstFg(InstallFg instFg) {
        this.instFg = instFg;
    }

    /**
     * @return the agencyCd
     */

    public String getAgencyCd() {
        return agencyCd;
    }

    /**
     * @param agencyCd the agencyCd to set
     */
    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    /**
     * @return the agencyNm
     */

    public String getAgencyNm() {
        return agencyNm;
    }

    /**
     * @param agencyNm the agencyNm to set
     */
    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    /**
     * @return the instReqDt
     */

    public String getInstReqDt() {
        return instReqDt;
    }

    /**
     * @param instReqDt the instReqDt to set
     */
    public void setInstReqDt(String instReqDt) {
        this.instReqDt = instReqDt;
    }

    /**
     * @return the instReqId
     */

    public String getInstReqId() {
        return instReqId;
    }

    /**
     * @param instReqId the instReqId to set
     */
    public void setInstReqId(String instReqId) {
        this.instReqId = instReqId;
    }

    /**
     * @return the instInsDt
     */

    public String getInstInsDt() {
        return instInsDt;
    }

    /**
     * @param instInsDt the instInsDt to set
     */
    public void setInstInsDt(String instInsDt) {
        this.instInsDt = instInsDt;
    }

    /**
     * @return the instInsId
     */

    public String getInstInsId() {
        return instInsId;
    }

    /**
     * @param instInsId the instInsId to set
     */
    public void setInstInsId(String instInsId) {
        this.instInsId = instInsId;
    }

    /**
     * @return the instReason
     */

    public String getInstReason() {
        return instReason;
    }

    /**
     * @param instReason the instReason to set
     */
    public void setInstReason(String instReason) {
        this.instReason = instReason;
    }

    /**
     * @return the remark
     */

    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }
}
