package kr.co.solbipos.adi.mony.status.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
* @Class Name : DrawHistVO.java
* @Description : 부가서비스 > 금전처리 > 금전현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.09.09  김태수      최초생성
*
* @author NHN한국사이버결제 KCP 김태수
* @since 2018.09.09
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class StatusVO extends CmmVO{

    private static final long serialVersionUID = -7484938662440568127L;

    private OrgnFg orgnFg;
    private String hqOfficeCd;
    private String startDate;
    private String endDate;
    private String storeCd;
    private String arrStoreCd[];
    private String accntFg;
    private String accntCd;


    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDae() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getAccntFg() {
        return accntFg;
    }

    public void setAccntFg(String accntFg) {
        this.accntFg = accntFg;
    }

    public String getAccntCd() {
        return accntCd;
    }

    public void setAccntCd(String accntCd) {
        this.accntCd = accntCd;
    }
}
