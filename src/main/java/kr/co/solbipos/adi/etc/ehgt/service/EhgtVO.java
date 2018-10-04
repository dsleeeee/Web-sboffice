package kr.co.solbipos.adi.etc.ehgt.service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import kr.co.common.validate.Save;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : EhgtVO.java
 * @Description : 부가서비스 > 환율 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EhgtVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 본사코드 or 가맹점코드 */
    private String orgnCd;
    /** 영업일자 */
    private String saleDate;
    /** 통화코드 */
    private String crncyCd;
    /** 통화금액 */
    private long crncyAmt;
    /** 한화금액 */
    @Min(groups = {Save.class}, value=0, message = "{ehgt.krwAmt}{cmm.input.fail}")
    @Max(groups = {Save.class}, value=100000, message = "{ehgt.krwAmt}{cmm.input.fail}")
    private float krwAmt;

    /** 검색조건-시작일자 */
    @DateTimeFormat(pattern="yyyyMMdd")
    private String startDt;
    /** 검색조건-종료일자 */
    @DateTimeFormat(pattern="yyyyMMdd")
    private String endDt;
    /** 기능키 관련 프로시져 실행 결과 */
    private String result;


    /**
     * @return the orgnCd
     */
    public String getOrgnCd() {
      return orgnCd;
    }
    /**
     * @param orgnCd the orgnCd to set
     */
    public void setOrgnCd(String orgnCd) {
      this.orgnCd = orgnCd;
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
     * @return the crncyCd
     */
    public String getCrncyCd() {
      return crncyCd;
    }
    /**
     * @param crncyCd the crncyCd to set
     */
    public void setCrncyCd(String crncyCd) {
      this.crncyCd = crncyCd;
    }
    /**
     * @return the crncyAmt
     */
    public long getCrncyAmt() {
      return crncyAmt;
    }
    /**
     * @param crncyAmt the crncyAmt to set
     */
    public void setCrncyAmt(long crncyAmt) {
      this.crncyAmt = crncyAmt;
    }
    /**
     * @return the krwAmt
     */
    public float getKrwAmt() {
      return krwAmt;
    }
    /**
     * @param krwAmt the krwAmt to set
     */
    public void setKrwAmt(float krwAmt) {
      this.krwAmt = krwAmt;
    }
    /**
     * @return the startDt
     */
    public String getStartDt() {
        return startDt;
    }
    /**
     * @param startDt the startDt to set
     */
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    /**
     * @return the endDt
     */
    public String getEndDt() {
        return endDt;
    }
    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
}
