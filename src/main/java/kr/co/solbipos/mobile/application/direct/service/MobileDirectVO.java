package kr.co.solbipos.mobile.application.direct.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileDirectVO.java
 * @Description : (모바일) QR > 원하는 페이지 오픈
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.26  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileDirectVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 코드 */
    private String accessCd;

    public String getAccessCd() {
        return accessCd;
    }

    public void setAccessCd(String accessCd) {
        this.accessCd = accessCd;
    }
}