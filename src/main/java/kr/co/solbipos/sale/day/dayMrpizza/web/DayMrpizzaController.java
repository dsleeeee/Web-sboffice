package kr.co.solbipos.sale.day.dayMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.dayMrpizza.service.DayMrpizzaService;
import kr.co.solbipos.sale.day.dayMrpizza.service.DayMrpizzaVO;
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
 * @Class Name : DayMrpizzaController.java
 * @Description : 미스터피자 > 매출분석 > 일별매출현황(채널별)
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
@RequestMapping("/sale/day/dayMrpizza")

public class DayMrpizzaController {
    
    private final SessionService sessionService;
    private final DayMrpizzaService dayMrpizzaService;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;
    
    /**
     * Constructor Injection
     */
    @Autowired
    public DayMrpizzaController(SessionService sessionService, DayMrpizzaService dayMrpizzaService, DayService dayService, StoreChannelService storeChannelService) {
        this.sessionService = sessionService;
        this.dayMrpizzaService = dayMrpizzaService;
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
    @RequestMapping(value = "/dayMrpizza/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

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
        for (int i = 0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for (int i = 0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        // 모바일페이상세 조회(현금영수증 포함)
        List<DefaultMap<String>> mpayColList = dayService.getMpayColList(dayVO, sessionInfoVO);

        // 모바일페이상세 코드를 , 로 연결하는 문자열 생성
        String mpayCol = "";
        for (int i = 0; i < mpayColList.size(); i++) {
            mpayCol += (mpayCol.equals("") ? "" : ",") + mpayColList.get(i).getStr("mpayCd");
        }
        model.addAttribute("mpayColList", mpayColList);
        model.addAttribute("mpayCol", mpayCol);

        return "sale/day/dayMrpizza/dayMrpizza";
    }

    /**
     * 일별매출현황(채널별) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/dayMrpizza/getDayMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, DayMrpizzaVO dayMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayMrpizzaService.getDayMrpizzaList(dayMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayMrpizzaVO);
    }

    /**
     * 일별매출현황(채널별) 엑셀다운로드 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/dayMrpizza/getDayMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayMrpizzaVO dayMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayMrpizzaService.getDayMrpizzaExcelList(dayMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayMrpizzaVO);
    }
}
