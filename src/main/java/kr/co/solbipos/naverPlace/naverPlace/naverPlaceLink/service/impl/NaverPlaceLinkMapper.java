package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name  : NaverPlaceLinkMapper.java
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
@Mapper
@Repository
public interface NaverPlaceLinkMapper {

    /**
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlaceLinkVO
     * @return
     */
    int saveNaverState(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 네이버 로그인 state 값으로 기존 세션정보 조회
     *
     * @param naverPlaceApiVO
     * @return
     */
    DefaultMap getNaverState(NaverPlaceApiVO naverPlaceApiVO);

    /**
     * 네.아.로 Unique ID 저장
     *
     * @param naverPlaceLinkVO
     * @return
     */
    int saveNaverUniqueId(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 네.아.로 Unique ID 조회
     *
     * @param naverPlaceLinkVO
     * @return
     */
    String getNaverUniqueId(NaverPlaceLinkVO naverPlaceLinkVO);

    /**
     * 네이버 동의여부 저장
     *
     * @param naverPlaceLinkVO
     * @return
     */
    int saveNaverAgreement(NaverPlaceLinkVO naverPlaceLinkVO);

}
