package kr.co.solbipos.sale.anals.mCoupnHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.mCoupnHistory.service.MCoupnHistoryService;
import kr.co.solbipos.sale.anals.mCoupnHistory.service.MCoupnHistoryVO;
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
 * @Class Name : MCoupnHistoryController.java
 * @Description :  매출관리 > 매출분석 > 모바일쿠폰이력조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.24  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.24
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/anals/mCoupnHistory")
public class MCoupnHistoryController {

    private final SessionService sessionService;
    private final MCoupnHistoryService mCoupnHistoryService;
    private final TodayDtlService todayDtlService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MCoupnHistoryController(SessionService sessionService, MCoupnHistoryService mCoupnHistoryService, TodayDtlService todayDtlService) {
        this.sessionService = sessionService;
        this.mCoupnHistoryService = mCoupnHistoryService;
        this.todayDtlService = todayDtlService;
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

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/anals/mCoupnHistory/mCoupnHistory";
    }

    /**
     * 모바일쿠폰이력조회
     * 
     * @param mCoupnHistoryVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2026.03.24
     */
    @RequestMapping(value = "/getMCoupnHistory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnHistory(MCoupnHistoryVO mCoupnHistoryVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mCoupnHistoryService.getMCoupnHistory(mCoupnHistoryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mCoupnHistoryVO);
    }
}
