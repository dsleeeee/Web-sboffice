package kr.co.solbipos.base.price.salePriceResve.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveService;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : SalePriceResveController.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/salePriceResve")
public class SalePriceResveController {

    private final SessionService sessionService;
    private final SalePriceResveService salePriceResveService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceResveController(SessionService sessionService, SalePriceResveService salePriceResveService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceResveService = salePriceResveService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 가격예약(본사판매가) 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));

        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
            model.addAttribute("coercionFg", "0");
        }

        // 내일날짜
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, +1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(cal.getTime());
        model.addAttribute("tomorrowDate", dateStr);

        return "base/price/salePriceResve/hqSalePriceResve";
    }

    /**
     * 가격예약(본사판매가) 리스트 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/getHqSalePriceResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceResveList(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.getHqSalePriceResveList(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

    /**
     * 가격예약(본사판매가) 추가
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/saveHqSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.saveHqSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(본사판매가) 수정
     * @param salePriceResveVOs
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/modHqSalePriceResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modHqSalePriceResve(@RequestBody SalePriceResveVO[] salePriceResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceResveService.modHqSalePriceResve(salePriceResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 가격예약(본사판매가) 상품가격정보 조회
     * @param salePriceResveVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.05
     */
    @RequestMapping(value = "/hqSalePriceResve/searchHqSalePriceInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result searchHqSalePriceInfo(SalePriceResveVO salePriceResveVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceResveService.searchHqSalePriceInfo(salePriceResveVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceResveVO);
    }

}
