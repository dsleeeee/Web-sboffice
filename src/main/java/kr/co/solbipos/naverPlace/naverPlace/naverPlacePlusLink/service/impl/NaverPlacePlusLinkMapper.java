package kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name  : NaverPlacePlusLinkMapper.java
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
@Mapper
@Repository
public interface NaverPlacePlusLinkMapper {

    /**
     * 개발/운영 Api URL 조회
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    DefaultMap<Object> getApiUrl(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    int saveNaverState(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네이버 로그인 state 값으로 기존 세션정보 조회
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    DefaultMap getNaverState(NaverPlacePlusApiVO naverPlacePlusApiVO);

    /**
     * 네.아.로 Unique ID 저장
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    int saveNaverUniqueId(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네이버 플레이스 연동매장 조회
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    DefaultMap<String> getNaverStore(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네.아.로 Unique ID 조회
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    String getNaverUniqueId(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네이버 동의여부 저장
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    int saveNaverAgreement(NaverPlacePlusLinkVO naverPlacePlusLinkVO);

    /**
     * 네이버 플레이스 연동매장 수정
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    int updateNaverStore(NaverPlacePlusLinkVO naverPlacePlusLinkVO);
}
