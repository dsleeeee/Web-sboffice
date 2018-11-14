package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.solbipos.base.prod.sidemenu.service.SideMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class Name : SideMenuServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideMenuService")
public class SideMenuServiceImpl implements SideMenuService {

    private final SideMenuMapper sideMenuMapper;

    /** Constructor Injection */
    @Autowired
    public SideMenuServiceImpl(SideMenuMapper sideMenuMapper) {
        this.sideMenuMapper = sideMenuMapper;
    }


}
