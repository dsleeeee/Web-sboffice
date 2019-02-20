package kr.co.solbipos.sale.today.todayDtl.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
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
 * @Class Name : TodayDtlController.java
 * @Description : 매출관리 > 매출현황 > 당일매출상세현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.25  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.01.25
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/today/todayDtl")
public class TodayDtlController {
    private final SessionService sessionService;
    private final TodayDtlService todayDtlService;

    @Autowired
    public TodayDtlController(SessionService sessionService, TodayDtlService todayDtlService) {
        this.sessionService = sessionService;
        this.todayDtlService = todayDtlService;
    }


    /**
     * 당일매출상세현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2019. 02. 15.
     */
    @RequestMapping(value = "/todayDtl/view.sb", method = RequestMethod.GET)
    public String todayDtlView(HttpServletRequest request, HttpServletResponse response, Model model) {
        TodayDtlVO todayDtlVO = new TodayDtlVO();

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = todayDtlService.getPayColList(todayDtlVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = todayDtlService.getDcColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/today/todayDtl/todayDtl";
    }


    /**
     * 당일매출상세현황 - 매출상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayDtlVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 29.
     */
    @RequestMapping(value = "/todayDtl/storePosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePosList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayDtlVO todayDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayDtlService.getStorePosList(todayDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayDtlVO);
    }


    /**
     * 당일매출상세현황 - 매출종합 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayDtlVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 28.
     */
    @RequestMapping(value = "/todayDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayDtlListList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayDtlVO todayDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayDtlService.getTodayDtlList(todayDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayDtlVO);
    }


    /**
     * 당일매출상세현황 - 매출상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   todayDtlVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 29.
     */
    @RequestMapping(value = "/todayDtlDetail/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayDtlDetailListList(HttpServletRequest request, HttpServletResponse response,
        Model model, TodayDtlVO todayDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = todayDtlService.getTodayDtlDetailList(todayDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, todayDtlVO);
    }
}
