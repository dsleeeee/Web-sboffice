package kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : InStockReportByAcquireVO.java
 * @Description : 국민대 > 매입관리 > 매입처별 입고내역서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class InStockReportByAcquireVO extends PageVO {

    private static final long serialVersionUID = 565705684009600390L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

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
}
