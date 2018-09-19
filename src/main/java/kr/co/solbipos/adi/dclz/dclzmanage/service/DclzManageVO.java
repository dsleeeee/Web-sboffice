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
    private String empInDt;
    /** 퇴근일시 */
    private String empOutDt;
    /** 근무시간 분단위 */
    private Long workTime;
    /** 입력구분 > 입력구분 공통코드:087 */
    private DclzInFg inFg;
    /** 입력 구분 이름 */
    private String inFgNm;
    /** 비고 */
    private String remark;
    
    
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
    /**
     * @return the empInDt
     */
    public String getEmpInDt() {
        return empInDt;
    }
    /**
     * @param empInDt the empInDt to set
     */
    public void setEmpInDt(String empInDt) {
        this.empInDt = empInDt;
    }
    /**
     * @return the empOutDt
     */
    public String getEmpOutDt() {
        return empOutDt;
    }
    /**
     * @param empOutDt the empOutDt to set
     */
    public void setEmpOutDt(String empOutDt) {
        this.empOutDt = empOutDt;
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
    /**
     * @return the inFg
     */
    public DclzInFg getInFg() {
        return inFg;
    }
    /**
     * @param inFg the inFg to set
     */
    public void setInFg(DclzInFg inFg) {
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
