package kr.co.solbipos.adi.dclz.dclzmanage.service;

import kr.co.solbipos.adi.dclz.dclzmanage.enums.DclzInFg;
import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DclzManageVO.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DclzManageVO extends PageVO {

    private static final long serialVersionUID = -8695988598318969816L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 사원번호 */
    private String empNo;
    /** 출근일자 */
    private String empInDate;
    /** 포스번호 */
    private String posNo;
    /** 영업일자 */
    private String saleDate;
    /** 출근일시 */
    private String commuteInDt;
    /** 퇴근일시 */
    private String commuteOutDt;
    /** 근무시간 분단위 */
    private Long workTime;
    /** 입력구분 POS, WEB */
    private String inFg;
    /** 입력 구분 이름 */
    private String inFgNm;
    /** 비고 */
    private String remark;


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
     * @return the arrStoreCd
     */
    public String[] getArrStoreCd() {
        return arrStoreCd;
    }
    /**
     * @param arrStoreCd the arrStoreCd to set
     */
    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }
    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }
    /**
     * @param empNo the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    /**
     * @return the empInDate
     */
    public String getEmpInDate() {
        return empInDate;
    }
    /**
     * @param empInDate the empInDate to set
     */
    public void setEmpInDate(String empInDate) {
        this.empInDate = empInDate;
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
     * @return the saleDate
     */
    public String getSaleDate() {
        return saleDate;
    }
    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCommuteInDt() {
        return commuteInDt;
    }

    public void setCommuteInDt(String commuteInDt) {
        this.commuteInDt = commuteInDt;
    }

    public String getCommuteOutDt() {
        return commuteOutDt;
    }

    public void setCommuteOutDt(String commuteOutDt) {
        this.commuteOutDt = commuteOutDt;
    }
    /**
     * @return the workTime
     */
    public Long getWorkTime() {
        return workTime;
    }
    /**
     * @param workTime the workTime to set
     */
    public void setWorkTime(Long workTime) {
        this.workTime = workTime;
    }

    public String getInFg() {
        return inFg;
    }

    public void setInFg(String inFg) {
        this.inFg = inFg;
    }
    /**
     * @return the inFgNm
     */
    public String getInFgNm() {
        return inFgNm;
    }
    /**
     * @param inFgNm the inFgNm to set
     */
    public void setInFgNm(String inFgNm) {
        this.inFgNm = inFgNm;
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
    
}
