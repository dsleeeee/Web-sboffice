package kr.co.solbipos.sale.month.monthMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.month.monthMrpizza.service.MonthMrpizzaService;
import kr.co.solbipos.sale.month.monthMrpizza.service.MonthMrpizzaVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : MonthMrpizzaController.java
 * @Description : 미스터피자 > 매출분석 > 월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/month/monthMrpizza")
public class MonthMrpizzaController {

    private final SessionService sessionService;
    private final MonthMrpizzaService monthMrpizzaService;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthMrpizzaController(SessionService sessionService, MonthMrpizzaService monthMrpizzaService, DayService dayService, StoreChannelService storeChannelService) {
        this.sessionService = sessionService;
        this.monthMrpizzaService = monthMrpizzaService;
        this.dayService = dayService;
        this.storeChannelService = storeChannelService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/monthMrpizza/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayService.getPayColAddList(dayVO, sessionInfoVO);

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

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        // 모바일페이상세 조회(현금영수증 포함)
        List<DefaultMap<String>> mpayColList = dayService.getMpayColList(dayVO, sessionInfoVO);

        // 모바일페이상세 코드를 , 로 연결하는 문자열 생성
        String mpayCol = "";
        for(int i=0; i < mpayColList.size(); i++) {
            mpayCol += (mpayCol.equals("") ? "" : ",") + mpayColList.get(i).getStr("mpayCd");
        }
        model.addAttribute("mpayColList", mpayColList);
        model.addAttribute("mpayCol", mpayCol);

        return "sale/month/monthMrpizza/monthMrpizza";
    }

    /**
     * 월별매출현황(채널별) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/monthMrpizza/getMonthMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, MonthMrpizzaVO monthMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthMrpizzaService.getMonthMrpizzaList(monthMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthMrpizzaVO);
    }
    
    /**
     * 월별매출현황(채널별) 엑셀다운로드 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/monthMrpizza/getMonthMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthMrpizzaVO monthMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthMrpizzaService.getMonthMrpizzaExcelList(monthMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthMrpizzaVO);
    }
}
