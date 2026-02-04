package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.Map;

/**
 * @Class Name  : NaverPlaceLinkService.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.27  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.27
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverPlaceLinkService {

    /**
     * 인증 API Access Token 조회
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> getAccessToken(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     * @param naverPlaceLinkVO
     * @param sessionInfoVO
     * @return
     */
    int saveNaverUniqueId(NaverPlaceLinkVO naverPlaceLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 동의여부확인 API 호출
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> getAgreeYn(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 업체목록조회 API 호출
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> getPlaceList(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 업체 수정/등록 API 호출
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> savePlace(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 연동 추가 API
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> mappingPlace(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 연동 해지 API
     * @param naverPlaceLinkVO
     * @return
     */
    Map<String, Object> unMappingPlace(NaverPlaceLinkVO naverPlaceLinkVO);
}
