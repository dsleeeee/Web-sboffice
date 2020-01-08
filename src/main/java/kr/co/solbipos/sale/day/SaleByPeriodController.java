package kr.co.solbipos.sale.day;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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
@RequestMapping("/sale/day/saleByPeriod")
public class SaleByPeriodController {
    private final SessionService sessionService;
    private final DayService dayService;

    @Autowired
    public SaleByPeriodController(SessionService sessionService, DayService dayService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
    }

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String salePeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {

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


        // 외식테이블 조회
        List<DefaultMap<String>> tableColList = dayService.getTableColList(dayVO, sessionInfoVO);

        // 외식테이블구분 코드를 , 로 연결하는 문자열 생성
        String tableCol = "";
        for(int i=0; i < tableColList.size(); i++) {
            tableCol += (tableCol.equals("") ? "" : ",") + tableColList.get(i).getStr("tblCd");
        }
        model.addAttribute("tableColList", tableColList);
        model.addAttribute("tableCol", tableCol);
//        System.out.println("tableColList : "+tableColList);
//        System.out.println("tableCol : "+tableCol);


        // 포스 조회
        List<DefaultMap<String>> posColList = dayService.getPosColList(dayVO, sessionInfoVO);

        // 포스구분 코드를 , 로 연결하는 문자열 생성
        String posCol = "";
        for(int i=0; i < posColList.size(); i++) {
            posCol += (posCol.equals("") ? "" : ",") + posColList.get(i).getStr("posNo");
        }
        model.addAttribute("posColList", posColList);
        model.addAttribute("posCol", posCol);
//        System.out.println("posColList : "+posColList);
//        System.out.println("posCol : "+posCol);


        return "sale/day/saleByPeriod";
    }
}

