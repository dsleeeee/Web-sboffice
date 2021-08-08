package kr.co.solbipos.sale.status.dlvrFgSale.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgService;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/status/dlvrFg")
public class DlvrFgSaleController {
    private final SessionService sessionService;
    private final DayService dayService;
    private final DlvrFgService dlvrFgService;
    private final CmmCodeUtil cmmCodeUtil;

    public DlvrFgSaleController(SessionService sessionService, DayService dayService, DlvrFgService dlvrFgService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.dayService = dayService;
        this.dlvrFgService = dlvrFgService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동 - 내점/배달/포장 현황
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String dayOfWeekView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        DlvrFgVO dlvrFgVO = new DlvrFgVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 배달구분
        if(sessionInfoVO.getHqOfficeCd().equals("A0001")){
            List<DefaultMap<String>> list = dlvrFgService.getDlvrFgData(dlvrFgVO, sessionInfoVO);
            String comboFgDataAll = cmmCodeUtil.assmblObj(list, "name", "value", UseYn.ALL);
            model.addAttribute("comboFgData", comboFgDataAll);
        }

       // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/dlvr/dlvrFg/dlvrFg";
    }

}