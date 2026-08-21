package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name  : NaverOrderLinkMapper.java
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
@Mapper
@Repository
public interface NaverOrderLinkMapper {

    /** 개발/운영 Api URL 조회 */
    DefaultMap<Object> getApiUrl(NaverOrderLinkVO naverOrderLinkVO);

    /** 네.아.로 Unique ID 조회 */
    String getNaverUniqueId(NaverOrderLinkVO naverOrderLinkVO);

    /** 네이버 동의여부 저장 */
    int saveNaverAgreement(NaverOrderLinkVO naverOrderLinkVO);
}
