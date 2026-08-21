package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : NaverMenuLinkVO.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.19  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverMenuLinkVO extends PageVO {

    private static final long serialVersionUID = -1234567890123456789L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;

    public String getHqOfficeCd() { return hqOfficeCd; }
    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

}
