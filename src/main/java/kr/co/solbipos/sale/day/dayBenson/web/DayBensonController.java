package kr.co.solbipos.sale.day.dayBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.dayBenson.service.DayBensonService;
import kr.co.solbipos.sale.day.dayBenson.service.DayBensonVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DayBensonController.java
 * @Description : 벤슨 > 매출분석 > 일별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/day/dayBenson")
public class DayBensonController {

    private final SessionService sessionService;
    private final DayBensonService dayBensonService;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;
    private final DayProdService dayProdService;

    @Autowired
    public DayBensonController(SessionService sessionService, DayBensonService dayBensonService, DayService dayService, StoreChannelService storeChannelService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.dayBensonService = dayBensonService;
        this.dayService = dayService;
        this.storeChannelService = storeChannelService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayBenson/list.sb", method = RequestMethod.GET)
    public String dayBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

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

        return "sale/day/dayBenson/dayBenson";
    }

    /**
     * 일별매출현황(채널별) 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayBensonVO
     * @return  String
     */
    @RequestMapping(value = "/dayBenson/getDayBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayBensonList(HttpServletRequest request, HttpServletResponse response, Model model, DayBensonVO dayBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayBensonService.getDayBensonList(dayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayBensonVO);
    }

    /**
     * 일별매출현황(채널별) 엑셀 다운로드 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayBensonVO
     * @return  String
     */
    @RequestMapping(value = "/dayBenson/getDayBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayBensonVO dayBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayBensonService.getDayBensonExcelList(dayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayBensonVO);
    }
}
