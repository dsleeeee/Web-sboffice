package kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.MenuSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.MenuSaleMrpizzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MenuSaleMrpizzaController.java
 * @Description : 미스터피자 > 마케팅조회 > 메뉴별판매
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/mrpizza/menuSaleMrpizza")
public class MenuSaleMrpizzaController {

    private final SessionService sessionService;
    private final MenuSaleMrpizzaService menuSaleMrpizzaService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MenuSaleMrpizzaController(SessionService sessionService, MenuSaleMrpizzaService menuSaleMrpizzaService) {
        this.sessionService = sessionService;
        this.menuSaleMrpizzaService = menuSaleMrpizzaService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/mrpizza/menuSaleMrpizza/menuSaleMrpizza";
    }

    /**
     * 메뉴별판매 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param menuSaleMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.07.25
     */
    @RequestMapping(value = "/getMenuSaleMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuSaleMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, MenuSaleMrpizzaVO menuSaleMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = menuSaleMrpizzaService.getMenuSaleMrpizzaList(menuSaleMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, menuSaleMrpizzaVO);
    }
}
