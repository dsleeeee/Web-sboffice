package kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name  : NaverPlacePlusLinkService.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 플러스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.02.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.02.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverPlacePlusLinkService {

    /**
     * 인증 API Access Token 조회
     *
     * @return
     */
    Map<String, Object> getAccessToken();

    /**
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    int saveNaverState(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    String saveNaverUniqueId(NaverPlacePlusApiVO naverPlacePlusApiVO);

    /**
     * 네이버 플레이스 연동매장 조회
     *
     * @param naverPlacePlusLinkVO
     * @param sessionInfoVO
     * @return
     */
    DefaultMap<String> getNaverStore(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO);

    /**
    * 네.아.로 Unique ID 조회
    * @param naverPlacePlusLinkVO
    * @param sessionInfoVO
    * @return
    */
    String getNaverUniqueId(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 동의여부확인 API 호출
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    Map<String, Object> getAgreeYn(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    List<Map<String, Object>> getPlaceList(NaverPlacePlusApiVO naverPlacePlusApiVO);

    /**
     * 연동 조회 API 호출
     *
     * @param naverPlacePlusApiVO
     * @param sessionInfoVO
     * @return
     */
    Map<String, Object> getNaverLinkYn(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 연동 추가 API
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    Map<String, Object> mappingPlace(NaverPlacePlusApiVO naverPlacePlusApiVO);

    /**
     * 연동 해지 API
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    Map<String, Object> unMappingPlace(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO);
}
