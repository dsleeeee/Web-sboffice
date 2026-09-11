package kr.co.solbipos.sale.benson.dayCashSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.dayCashSaleBenson.service.DayCashSaleBensonService;
import kr.co.solbipos.sale.benson.dayCashSaleBenson.service.DayCashSaleBensonVO;
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
 * @Class Name : DayCashSaleBensonController.java
 * @Description : 벤슨 > 매출현황2 > 일별(현금)현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/dayCashSaleBenson")
public class DayCashSaleBensonController {

    private final SessionService sessionService;
    private final DayCashSaleBensonService dayCashSaleBensonService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayCashSaleBensonController(SessionService sessionService, DayCashSaleBensonService dayCashSaleBensonService) {
        this.sessionService = sessionService;
        this.dayCashSaleBensonService = dayCashSaleBensonService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     */
    @RequestMapping(value = "/dayCashSaleBenson/list.sb", method = RequestMethod.GET)
    public String dayCashSaleBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayCashSaleBensonVO dayCashSaleBensonVO = new DayCashSaleBensonVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayCashSaleBensonService.getPayColList(dayCashSaleBensonVO, sessionInfoVO);

        DefaultMap<String> cashStin = new DefaultMap<String>();
        cashStin.put("payCd", "0");
        cashStin.put("payNm", "현금(매장)");
        cashStin.put("payMethod", "CASH_STIN");

        DefaultMap<String> cashCid = new DefaultMap<String>();
        cashCid.put("payCd", "1");
        cashCid.put("payNm", "현금(CID)");
        cashCid.put("payMethod", "CASH_CID");

        DefaultMap<String> cashDlvr3 = new DefaultMap<String>();
        cashDlvr3.put("payCd", "3");
        cashDlvr3.put("payNm", "현금(배민)");
        cashDlvr3.put("payMethod", "CASH_DLVR3");

        DefaultMap<String> cashDlvr4 = new DefaultMap<String>();
        cashDlvr4.put("payCd", "4");
        cashDlvr4.put("payNm", "현금(요기요)");
        cashDlvr4.put("payMethod", "CASH_DLVR4");

        payColList.add(2, cashStin);
        payColList.add(3, cashCid);
        payColList.add(4, cashDlvr3);
        payColList.add(5, cashDlvr4);

        payColList.remove(1);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);


        // 할인구분 조회
        List<DefaultMap<String>> dcColList = dayCashSaleBensonService.getDcColList(dayCashSaleBensonVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);


        // 객수 조회
        List<DefaultMap<String>> guestColList = dayCashSaleBensonService.getGuestColList(dayCashSaleBensonVO, sessionInfoVO);

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/benson/dayCashSaleBenson/dayCashSaleBenson";
    }

    /**
     * 일별(현금)현황 - 일별종합(현금) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayCashSaleBensonVO
     * @return  Result
     */
    @RequestMapping(value = "/dayCashSaleBenson/getDayCashTotalBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCashTotalBensonList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayCashSaleBensonVO dayCashSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayCashSaleBensonService.getDayCashTotalBensonList(dayCashSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayCashSaleBensonVO);
    }
}
