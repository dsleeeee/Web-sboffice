package kr.co.solbipos.common.popup.selectDcfg.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SelectDcfgVO.java
 * @Description : (공통) 할인구분 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SelectDcfgVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 할인유형코드 */
    private String dcCd;

    /** 할인유형코드 */
    private String arrDcCd[];

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

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getDcCd() {
        return dcCd;
    }

    public void setDcCd(String dcCd) {
        this.dcCd = dcCd;
    }

    public String[] getArrDcCd() {
        return arrDcCd;
    }

    public void setArrDcCd(String[] arrDcCd) {
        this.arrDcCd = arrDcCd;
    }
}