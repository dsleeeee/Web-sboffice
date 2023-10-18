package kr.co.solbipos.common.popup.selectTable.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SelectTableController.java
 * @Description : (공통) 테이블 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SelectTableVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 array */
    private String arrStoreCd[];

    /** 콤보박스 테이블 코드 */
    private String tableCd;

    /** 그리드 코드 테이블 코드 array */
    private String arrTableCd[];

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

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getTableCd() {
        return tableCd;
    }

    public void setTableCd(String tableCd) {
        this.tableCd = tableCd;
    }

    public String[] getArrTableCd() {
        return arrTableCd;
    }

    public void setArrTableCd(String[] arrTableCd) {
        this.arrTableCd = arrTableCd;
    }
}