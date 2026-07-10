package kr.co.solbipos.sale.anals.salePerfCompareBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.salePerfCompareBenson.service.SalePerfCompareBensonService;
import kr.co.solbipos.sale.anals.salePerfCompareBenson.service.SalePerfCompareBensonVO;
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
 * @Class Name : SalePerfCompareBensonController.java
 * @Description : 벤슨 > 매출분석 > 대비기간 매출실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/anals/salePerfCompareBenson")
public class SalePerfCompareBensonController {

    private final SessionService sessionService;
    private final SalePerfCompareBensonService salePerfCompareBensonService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SalePerfCompareBensonController(SessionService sessionService, SalePerfCompareBensonService salePerfCompareBensonService) {
        this.sessionService = sessionService;
        this.salePerfCompareBensonService = salePerfCompareBensonService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/salePerfCompareBenson/list.sb", method = RequestMethod.GET)
    public String salePerfCompareBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/anals/salePerfCompareBenson/salePerfCompareBenson";
    }

    /**
     * 대비기간 매출실적 - 전체점포탭 조회
     *
     * @param   salePerfCompareBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 07. 09.
     */
    @RequestMapping(value = "/salePerfCompareBenson/getSalePerfCompareBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareBensonList(SalePerfCompareBensonVO salePerfCompareBensonVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareBensonService.getSalePerfCompareBensonList(salePerfCompareBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareBensonVO);
    }

    /**
     * 대비기간 매출실적 - 전체점포탭 채널별 조회
     *
     * @param   salePerfCompareBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 07. 09.
     */
    @RequestMapping(value = "/salePerfCompareBenson/getSalePerfCompareBensonDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareBensonDtlList(SalePerfCompareBensonVO salePerfCompareBensonVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareBensonService.getSalePerfCompareBensonDtlList(salePerfCompareBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareBensonVO);
    }

    /**
     * 대비기간 매출실적 - 선택점포탭 조회
     *
     * @param   salePerfCompareBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 07. 09.
     */
    @RequestMapping(value = "/salePerfCompareBenson/getSalePerfCompareBensonStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareBensonStoreList(SalePerfCompareBensonVO salePerfCompareBensonVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareBensonService.getSalePerfCompareBensonStoreList(salePerfCompareBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareBensonVO);
    }
}
