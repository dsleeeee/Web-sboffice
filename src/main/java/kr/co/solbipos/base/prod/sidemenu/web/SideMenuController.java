package kr.co.solbipos.base.prod.sidemenu.web;

import kr.co.solbipos.base.prod.sidemenu.service.SideMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Class Name : SideMenuController.java
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
@Controller
@RequestMapping(value = "/base/prod/sideMenu/")
public class SideMenuController {

    private final SideMenuService sideMenuService;

    /** Constructor Injection */
    @Autowired
    public SideMenuController(SideMenuService sideMenuService) {
        this.sideMenuService = sideMenuService;
    }



}
