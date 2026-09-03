package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderApiVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

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

    /**
     * 메뉴(옵션)목록 조회
     */
    @RequestMapping(value = "/getMenuOptionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = naverMenuLinkService.getMenuOptionList(naverMenuLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, naverMenuLinkVO);
    }

    /**
     * 서브메뉴(옵션) 목록 조회
     */
    @RequestMapping(value = "/getSubMenuOptionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSubMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = naverMenuLinkService.getSubMenuOptionList(naverMenuLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, naverMenuLinkVO);
    }

    /**
     * 상품목록조회
     */
    @RequestMapping(value = "/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(NaverMenuLinkVO naverMenuLinkVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = naverMenuLinkService.getProdList(naverMenuLinkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, naverMenuLinkVO);
    }

    /**
     *  메뉴 연동·해제
     */
    @RequestMapping(value = "/mappingMenuOption.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result mappingMenuOption(@RequestBody List<Map<String, Object>> mappingList, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<Map<String, Object>> list = naverMenuLinkService.mappingMenuOption(mappingList, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 서브메뉴(옵션) 연동·해제
     */
    @RequestMapping(value = "/mappingSubMenuOption.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result mappingSubMenuOption(@RequestBody List<Map<String, Object>> mappingList, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<Map<String, Object>> list = naverMenuLinkService.mappingSubMenuOption(mappingList, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

}
