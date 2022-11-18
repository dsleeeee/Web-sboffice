package kr.co.solbipos.sale.day.dayStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayStore.service.DayStoreService;
import kr.co.solbipos.sale.day.dayStore.service.DayStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : DayStoreController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(기기별)
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
@RequestMapping("/sale/day/dayStore")
public class DayStoreController {

    private final SessionService sessionService;
    private final DayStoreService dayStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayStoreController(SessionService sessionService, DayStoreService dayStoreService) {
        this.sessionService = sessionService;
        this.dayStoreService = dayStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayStore/list.sb", method = RequestMethod.GET)
    public String dayStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        DayStoreVO dayStoreVO = new DayStoreVO();

        // 지사 콤보박스
        model.addAttribute("branchMomsComboList", convertToJson(dayStoreService.getBranchMomsComboList(dayStoreVO, sessionInfoVO)));

        return "sale/day/dayStore/dayStore";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/dayStore/getDayStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayStoreList(HttpServletRequest request, HttpServletResponse response, Model model, DayStoreVO dayStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayStoreService.getDayStoreList(dayStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayStoreVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/dayStore/getDayStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayStoreVO dayStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayStoreService.getDayStoreExcelList(dayStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayStoreVO);
    }

}