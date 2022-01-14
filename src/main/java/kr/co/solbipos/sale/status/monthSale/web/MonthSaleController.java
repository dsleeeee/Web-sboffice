package kr.co.solbipos.sale.status.monthSale.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/status/monthSale")
public class MonthSaleController {
    private final SessionService sessionService;
    private final DayService dayService;

    @Autowired
    public MonthSaleController(SessionService sessionService, DayService dayService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
    }

    /** 매출현황 - 월별 */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String monthSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

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
            for(int i=0; i < tableColList.size(); i++) {
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


        return "sale/status/monthSale/monthSale";
    }
}