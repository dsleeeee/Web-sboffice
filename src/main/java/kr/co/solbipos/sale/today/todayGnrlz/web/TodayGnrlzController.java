package kr.co.solbipos.sale.today.todayGnrlz.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzService;
import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzVO;
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
 * @Class Name : TodayGnrlzController.java
 * @Description : 매출관리 > 매출현황 > 당일매출종합현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.23  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.01.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/today/todayGnrlz")
public class TodayGnrlzController {
    private final SessionService sessionService;
    private final TodayGnrlzService todayGnrlzService;

    @Autowired
    public TodayGnrlzController(SessionService sessionService, TodayGnrlzService todayGnrlzService) {
        this.sessionService = sessionService;
        this.todayGnrlzService = todayGnrlzService;
    }


    /**
     * 당일매출종합현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2019. 01. 23.
     */
    @RequestMapping(value = "/todayGnrlz/view.sb", method = RequestMethod.GET)
    public String todayGnrlzView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/today/todayGnrlz/todayGnrlz";
    }


    /**
     * 당일매출종합현황 - 매출종합 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayGnrlzVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 23.
     */
    @RequestMapping(value = "/todayGnrlz/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayGnrlzList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayGnrlzVO todayGnrlzVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayGnrlzService.getTodayGnrlzList(todayGnrlzVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayGnrlzVO);
    }


    /**
     * 당일매출종합현황 - 결제수단별 매출 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayGnrlzVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 23.
     */
    @RequestMapping(value = "/todayGnrlzPay/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayGnrlzPayList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayGnrlzVO todayGnrlzVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayGnrlzService.getTodayGnrlzPayList(todayGnrlzVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayGnrlzVO);
    }


    /**
     * 당일매출종합현황 - 회원 Point 적립/사용 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayGnrlzVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 23.
     */
    @RequestMapping(value = "/todayGnrlzMember/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayGnrlzMemberList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayGnrlzVO todayGnrlzVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayGnrlzService.getTodayGnrlzMemberList(todayGnrlzVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayGnrlzVO);
    }


    /**
     * 당일매출종합현황 - 상품별 매출현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayGnrlzVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 23.
     */
    @RequestMapping(value = "/todayGnrlzProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayGnrlzProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayGnrlzVO todayGnrlzVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayGnrlzService.getTodayGnrlzProdList(todayGnrlzVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayGnrlzVO);
    }
}
