package kr.co.solbipos.sale.store.storeMonthChannelMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import kr.co.solbipos.sale.store.storeDayChannelMrpizza.service.StoreDayChannelMrpizzaVO;
import kr.co.solbipos.sale.store.storeMonthChannel.service.StoreMonthChannelVO;
import kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service.StoreMonthChannelMrpizzaService;
import kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service.StoreMonthChannelMrpizzaVO;
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
 * @Class Name : StoreMonthChannelMrpizzaController.java
 * @Description : 미스터피자 > 매장분석 > 매장-월별매출현황(채널별)
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
@RequestMapping("/sale/store/storeMonthChannelMrpizza")
public class StoreMonthChannelMrpizzaController {

    private final DayService dayService;
        private final TodayDtlService todayDtlService;
        private final SessionService sessionService;
        private final StoreMonthChannelMrpizzaService storeMonthChannelMrpizzaService;
        private final StoreChannelService storeChannelService;
        private final DayProdService dayProdService;
        private final CmmCodeUtil cmmCodeUtil;


    @Autowired
    public StoreMonthChannelMrpizzaController(DayService dayService, TodayDtlService todayDtlService, SessionService sessionService, StoreMonthChannelMrpizzaService storeMonthChannelMrpizzaService, StoreChannelService storeChannelService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.dayService = dayService;
        this.todayDtlService = todayDtlService;
        this.sessionService = sessionService;
        this.storeMonthChannelMrpizzaService = storeMonthChannelMrpizzaService;
        this.storeChannelService = storeChannelService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    @RequestMapping(value = "/storeMonthChannel/view.sb", method = RequestMethod.GET)
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

            // 맘스터치 결제수단 '식권' -> '식권대장' 으로 변경표기 (20240909)
            if(sessionInfoVO.getHqOfficeCd().equals("DS021") || sessionInfoVO.getHqOfficeCd().equals("DS034") || sessionInfoVO.getHqOfficeCd().equals("H0393")){
                if(payColList.get(i).getStr("payCd").equals("14")){
                    payColList.get(i).put("payNm", "식권대장");
                }
            }
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

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

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

        return "sale/store/storeMonthChannelMrpizza/storeMonthChannelMrpizza";
    }

    /**
     * 매장-월별매출현황(채널별) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param storeMonthChannelMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/storeMonthChannelMrpizza/getStoreMonthChannelMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMonthChannelMrpizzaList(HttpServletRequest request, HttpServletResponse response,
                               Model model, StoreMonthChannelMrpizzaVO storeMonthChannelMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeMonthChannelMrpizzaService.getStoreMonthChannelMrpizzaList(storeMonthChannelMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMonthChannelMrpizzaVO);
    }

    /**
     * 매장-월별매출현황(채널별) 엑셀 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param storeMonthChannelMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/storeMonthChannelMrpizza/getStoreMonthChannelMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMonthChannelMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response,
                             Model model, StoreMonthChannelMrpizzaVO storeMonthChannelMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeMonthChannelMrpizzaService.getStoreMonthChannelMrpizzaExcelList(storeMonthChannelMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMonthChannelMrpizzaVO);
    }

}
