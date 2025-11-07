package kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name  : OrderkitRecpOriginVO.java
 * @Description : 오더킷 > 오더킷 > 원산지 정보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.11.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class OrderkitRecpOriginVO extends CmmVO {

    private static final long serialVersionUID = -2460411140372625443L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 출력물코드 */
    private String prtCd;

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

    public String getPrtCd() {
        return prtCd;
    }

    public void setPrtCd(String prtCd) {
        this.prtCd = prtCd;
    }
}
