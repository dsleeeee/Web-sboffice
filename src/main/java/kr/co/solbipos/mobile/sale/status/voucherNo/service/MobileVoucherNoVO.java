package kr.co.solbipos.mobile.sale.status.voucherNo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileVoucherNoVO.java
 * @Description : (모바일) 매출현황 > 최종교환권번호
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.16  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.11.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileVoucherNoVO extends PageVO {

    private static final long serialVersionUID = -7812882525323709398L;

    /** 조회매장 */
    private String storeCd;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
}
