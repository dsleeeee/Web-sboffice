package kr.co.solbipos.pos.license.instlManage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.install.enums.InstallFg;

/**
 * @Class Name : InstlManageVO.java
 * @Description : 포스관리 > 라이선스관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class InstlManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 업체코드 */
    private String agencyCd;

    /** 업체명 */
    private String agencyNm;

    /** 전체기간체크 */
    private boolean chkDt;

    /** 매장코드 */
    private String storeCd;

    /** 포스번호 */
    private String posNo;

    /** 사용여부 */
    private String useYn;

    /** 업체구분 */
    private String agencyFg;

    /** 소속코드 */
    private String orgnCd;

    /** 소속구분 */
    private String orgnFg;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장명 */
    private String storeNm;

    /** 순번 */
    private String seqNo;

    /** 설치구분 */
    private InstallFg instFg;

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

    /** 대리점코드(검색용) */
    private String srchAgencyCd;

    /** 대리점명(검색용) */
    private String srchAgencyNm;

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public InstallFg getInstFg() {
        return instFg;
    }

    public void setInstFg(InstallFg instFg) {
        this.instFg = instFg;
    }

    public String getInstReqDt() {
        return instReqDt;
    }

    public void setInstReqDt(String instReqDt) {
        this.instReqDt = instReqDt;
    }

    public String getInstReqId() {
        return instReqId;
    }

    public void setInstReqId(String instReqId) {
        this.instReqId = instReqId;
    }

    public String getInstInsDt() {
        return instInsDt;
    }

    public void setInstInsDt(String instInsDt) {
        this.instInsDt = instInsDt;
    }

    public String getInstInsId() {
        return instInsId;
    }

    public void setInstInsId(String instInsId) {
        this.instInsId = instInsId;
    }

    public String getInstReason() {
        return instReason;
    }

    public void setInstReason(String instReason) {
        this.instReason = instReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
    }

    public String getSrchAgencyNm() {
        return srchAgencyNm;
    }

    public void setSrchAgencyNm(String srchAgencyNm) {
        this.srchAgencyNm = srchAgencyNm;
    }
}
