package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.impl;

import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name  : NaverMenuLinkServiceImpl.java
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
@Service("naverMenuLinkService")
@Transactional
public class NaverMenuLinkServiceImpl implements NaverMenuLinkService {

    private final NaverMenuLinkMapper naverMenuLinkMapper;

    @Autowired
    public NaverMenuLinkServiceImpl(NaverMenuLinkMapper naverMenuLinkMapper) {
        this.naverMenuLinkMapper = naverMenuLinkMapper;
    }

}
