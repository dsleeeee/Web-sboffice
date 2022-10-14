package kr.co.solbipos.sale.month.monthStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.month.monthStore.service.MonthStoreService;
import kr.co.solbipos.sale.month.monthStore.service.MonthStoreVO;
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
 * @Class Name : MonthStoreController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(매장합산)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/month/monthStore")
public class MonthStoreController {

    private final SessionService sessionService;
    private final MonthStoreService monthStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthStoreController(SessionService sessionService, MonthStoreService monthStoreService) {
        this.sessionService = sessionService;
        this.monthStoreService = monthStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/monthStore/list.sb", method = RequestMethod.GET)
    public String monthStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/month/monthStore/monthStore";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/monthStore/getMonthStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthStoreList(HttpServletRequest request, HttpServletResponse response, Model model, MonthStoreVO monthStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthStoreService.getMonthStoreList(monthStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthStoreVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/monthStore/getMonthStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthStoreVO monthStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthStoreService.getMonthStoreExcelList(monthStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthStoreVO);
    }

}