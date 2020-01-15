package kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.DayBillInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.DayBillInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/cmmSalePopup/dayBillInfo")
public class DayBillInfoController {

    private final SessionService sessionService;
    private final DayBillInfoService dayBillInfoService;

    /** Constructor Injection */
    @Autowired
    public DayBillInfoController(SessionService sessionService, DayBillInfoService dayBillInfoService){
        this.sessionService = sessionService;
        this.dayBillInfoService = dayBillInfoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String DayBillInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/cmmSalePopup/dayBillInfo/dayBillInfo";
    }

    /**
     * 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회
     *
     * @param dayBillInfoVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김설아
     * @since   2019. 12. 18.
     */
    @RequestMapping(value = "/dayBillInfo/getDayStoreBillList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayStoreBillList(DayBillInfoVO dayBillInfoVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayBillInfoService.getDayStoreBillList(dayBillInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayBillInfoVO);
    }
}