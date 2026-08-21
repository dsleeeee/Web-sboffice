package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class Name  : NaverMenuLinkController.java
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
@Controller
@RequestMapping(value = "/naverPlace/naverPlace/naverMenuLink")
public class NaverMenuLinkController {

    private final SessionService sessionService;
    private final NaverMenuLinkService naverMenuLinkService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public NaverMenuLinkController(SessionService sessionService, NaverMenuLinkService naverMenuLinkService) {
        this.sessionService = sessionService;
        this.naverMenuLinkService = naverMenuLinkService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "naverPlace/naverPlace/naverMenuLink/naverMenuLink";
    }

}
