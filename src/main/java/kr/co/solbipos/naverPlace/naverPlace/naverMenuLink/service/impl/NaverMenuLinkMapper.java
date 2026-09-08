package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : NaverMenuLinkMapper.java
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
@Mapper
@Repository
public interface NaverMenuLinkMapper {

    /** 개발/운영 Api URL 조회 */
    DefaultMap<Object> getApiUrl(NaverMenuLinkVO naverMenuLinkVO);

    /** (네이버 주문연동용) 네.아.로 Unique ID 조회 */
    String getNaverUniqueId(NaverMenuLinkVO naverMenuLinkVO);

    /** 네이버 메뉴(옵션)목록 사용여부 초기화 (동기화 전, 전체 N 처리) */
    int resetMenuOptionUseYn(NaverMenuLinkVO naverMenuLinkVO);

    /** 네이버 메뉴(옵션)목록 저장 */
    int saveMenuOptionList(List<NaverMenuLinkVO> list);

    /** 네이버 메뉴(옵션)목록 조회 */
    List<DefaultMap<Object>> getMenuOptionList(NaverMenuLinkVO naverMenuLinkVO);

    /** 상품목록조회 */
    List<DefaultMap<Object>> getProdList(NaverMenuLinkVO naverMenuLinkVO);

    /** 상품명 조회 */
    String getProdNm(NaverMenuLinkVO naverMenuLinkVO);
}
