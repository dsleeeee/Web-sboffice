package kr.co.solbipos.adi.mony.drawhist.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
* @Class Name : DrawHistVO.java
* @Description : 부가서비스 > 금전처리 > 돈통오픈기록
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.03  김태수      최초생성
*
* @author NHN한국사이버결제 KCP 김태수
* @since 2018.08.03
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class DrawHistVO extends CmmVO{

    private static final long serialVersionUID = 1L;

    private OrgnFg orgnFg;
    private String storeCd;
    private String startDt;
    private String endDt;
    
    public OrgnFg getOrgnFg() { return orgnFg; }

    public void setOrgnFg(OrgnFg orgnFg) { this.orgnFg = orgnFg; }
    
    public String getStoreCd() {
        return storeCd;
    }
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    
    public String getStartDt() {
        return startDt;
    }
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    public String getEndDt() {
        return endDt;
    }
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
}
