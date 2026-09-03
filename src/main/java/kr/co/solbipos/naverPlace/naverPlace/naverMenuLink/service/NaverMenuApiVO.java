package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service;

/**
 * @Class Name  : NaverMenuApiVO.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴관리 API VO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.28  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class NaverMenuApiVO {

    private static final long serialVersionUID = 8549860636702269840L;

    /** 네.아.로 Unique ID */
    private String uniqueId;
    /** 인증 토큰 */
    private String accessToken;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
