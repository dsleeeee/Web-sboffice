package kr.co.solbipos.sale.mrpizza.daySaleMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaVO;
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
 * @Class Name : DaySaleMrpizzaController.java
 * @Description : 미스터피자 > 마케팅조회 > 일자별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/mrpizza/daySaleMrpizza")
public class DaySaleMrpizzaController {

    private final SessionService sessionService;
    private final DaySaleMrpizzaService daySaleMrpizzaService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleMrpizzaController(SessionService sessionService, DaySaleMrpizzaService daySaleMrpizzaService, DayService dayService) {
        this.sessionService = sessionService;
        this.daySaleMrpizzaService = daySaleMrpizzaService;
        this.dayService = dayService;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        DayVO dayVO = new DayVO();

        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayService.getPayColAddList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for (int i = 0; i < payColList.size(); i++) {
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

        return "sale/mrpizza/daySaleMrpizza/daySaleMrpizza";
    }

    /**
     * 일자별매출 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param daySaleMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.07.25
     */
    @RequestMapping(value = "/getDaySaleMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, DaySaleMrpizzaVO daySaleMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = daySaleMrpizzaService.getDaySaleMrpizzaList(daySaleMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, daySaleMrpizzaVO);
    }

    /**
     *  일자별매출 상세 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param daySaleMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.07.25
     */
    @RequestMapping(value = "/getDaySaleMrpizzaDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleMrpizzaDtlList(HttpServletRequest request, HttpServletResponse response, Model model, DaySaleMrpizzaVO daySaleMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = daySaleMrpizzaService.getDaySaleMrpizzaDtlList(daySaleMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, daySaleMrpizzaVO);
    }
}
