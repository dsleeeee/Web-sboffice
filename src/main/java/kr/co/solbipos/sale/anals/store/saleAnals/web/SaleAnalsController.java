package kr.co.solbipos.sale.anals.store.saleAnals.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankService;
import kr.co.solbipos.sale.anals.store.saleAnals.service.SaleAnalsService;
import kr.co.solbipos.sale.anals.store.saleAnals.service.SaleAnalsVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
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
 * @Class Name : SaleAnalsController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 매장순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.29  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.29
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/store")
public class SaleAnalsController {
    private final DayService dayService;
    private final TodayDtlService todayDtlService;
    private final SessionService sessionService;
    private final SaleAnalsService saleAnalsService;

    @Autowired
    public SaleAnalsController(DayService dayService, TodayDtlService todayDtlService, SessionService sessionService, SaleAnalsService saleAnalsService) {
        this.dayService = dayService;
        this.todayDtlService = todayDtlService;
        this.sessionService = sessionService;
        this.saleAnalsService = saleAnalsService;
    }


    /**
     * 매장순위 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/saleAnals/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
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

        return "sale/anals/saleAnals/saleAnalsTab";
    }

    /**
     * 일별 - 일별 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   saleAnalsVO
     * @return  String
     * @author  권지현
     * @since   2022.09.22
     */
    @RequestMapping(value = "/saleAnals/getDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayList(HttpServletRequest request, HttpServletResponse response,
                             Model model, SaleAnalsVO saleAnalsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = saleAnalsService.getDayList(saleAnalsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleAnalsVO);
    }

    /**
     * 일별 - 일별 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   saleAnalsVO
     * @return  String
     * @author  권지현
     * @since   2022.09.22
     */
    @RequestMapping(value = "/saleAnals/getMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthList(HttpServletRequest request, HttpServletResponse response,
                             Model model, SaleAnalsVO saleAnalsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = saleAnalsService.getMonthList(saleAnalsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleAnalsVO);
    }


}
