package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name  : NaverOrderLinkService.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.13  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverOrderLinkService {

    /**
     * 개발/운영 Api URL 조회
     */
    DefaultMap<Object> getApiUrl(NaverOrderLinkVO naverOrderLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 인증 API Access Token 조회
     */
    Map<String, Object> getAccessToken(String storeCd);

    /**
     * 네.아.로 Unique ID 조회
     */
    String getNaverUniqueId(NaverOrderLinkVO naverOrderLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 동의여부확인 API 호출 (NAVER API)
     */
    Map<String, Object> getAgreeYn(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 업체리스트조회 API 호출
     */
    Map<String, Object> getPlaceList(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 매장등록 API 호출
     */
    Map<String, Object> regPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 매장수정 API 호출
     */
    Map<String, Object> modPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 매장 단건조회 API 호출
     */
    Map<String, Object> getPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 서비스 활성화/비활성화 API 호출
     */
    Map<String, Object> regServiceActive(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);

    /**
     * 매핑해제
     */
    /*Map<String, Object> delUnmapping(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO);*/

}
