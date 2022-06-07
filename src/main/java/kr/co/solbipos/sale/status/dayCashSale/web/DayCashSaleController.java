package kr.co.solbipos.sale.status.dayCashSale.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/status/dayCashSale")
public class DayCashSaleController {
    private final SessionService sessionService;
    private final DayService dayService;
    private final TodayDtlService todayDtlService;

    @Autowired
    public DayCashSaleController(SessionService sessionService, DayService dayService, TodayDtlService todayDtlService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
        this.todayDtlService = todayDtlService;
    }

    /** 매출현황 - 일자별 */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String daySaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

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
        List<DefaultMap<String>> dcColList = dayService.getDcColList(dayVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);


        // 코너 조회
        List<DefaultMap<String>> cornerColList = dayService.getCornerColList(dayVO, sessionInfoVO);

        // 코너구분 코드를 , 로 연결하는 문자열 생성
        String cornerCol = "";
        for(int i=0; i < cornerColList.size(); i++) {
            cornerCol += (cornerCol.equals("") ? "" : ",") + cornerColList.get(i).getStr("storeCornrCd");
        }
        model.addAttribute("cornerColList", cornerColList);
        model.addAttribute("cornerCol", cornerCol);


        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 외식테이블 조회
            List<DefaultMap<String>> tableColList = dayService.getTableColList(dayVO, sessionInfoVO);

            // 외식테이블구분 코드를 , 로 연결하는 문자열 생성
            String tableCol = "";
            for (int i = 0; i < tableColList.size(); i++) {
                tableCol += (tableCol.equals("") ? "" : ",") + tableColList.get(i).getStr("tblCd");
            }
            model.addAttribute("tableColList", tableColList);
            model.addAttribute("tableCol", tableCol);
        }else{
            model.addAttribute("tableColList", "");
            model.addAttribute("tableCol", "");
        }


        // 포스 조회
        List<DefaultMap<String>> posColList = dayService.getPosColList(dayVO, sessionInfoVO);

        // 포스구분 코드를 , 로 연결하는 문자열 생성
        String posCol = "";
        for(int i=0; i < posColList.size(); i++) {
            posCol += (posCol.equals("") ? "" : ",") + posColList.get(i).getStr("storePosNo");
        }
        model.addAttribute("posColList", posColList);
        model.addAttribute("posCol", posCol);


        // 상분분류별 탭 - 분류레벨 최대값 조회
        model.addAttribute("maxLevel", dayService.getDayProdClassMaxLevel(dayVO, sessionInfoVO));


        // 객수 조회
        TodayDtlVO todayDtlVO = new TodayDtlVO();

        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);
        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/status/dayCashSale/dayCashSale";
    }
}