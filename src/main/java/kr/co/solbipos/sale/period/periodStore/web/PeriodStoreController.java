package kr.co.solbipos.sale.period.periodStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.period.periodStore.service.PeriodStoreService;
import kr.co.solbipos.sale.period.periodStore.service.PeriodStoreVO;
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
 * @Class Name : PeriodStoreController.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출(매장합산)
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
@RequestMapping("/sale/period/periodStore")
public class PeriodStoreController {

    private final SessionService sessionService;
    private final PeriodStoreService periodStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PeriodStoreController(SessionService sessionService, PeriodStoreService periodStoreService) {
        this.sessionService = sessionService;
        this.periodStoreService = periodStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/periodStore/list.sb", method = RequestMethod.GET)
    public String periodStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/period/periodStore/periodStore";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/periodStore/getPeriodStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodStoreList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodStoreVO periodStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodStoreService.getPeriodStoreList(periodStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodStoreVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/periodStore/getPeriodStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodStoreVO periodStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodStoreService.getPeriodStoreExcelList(periodStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodStoreVO);
    }

}