package kr.co.solbipos.sale.benson.timeProdChannelBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.timeProdChannelBenson.service.TimeProdChannelBensonService;
import kr.co.solbipos.sale.benson.timeProdChannelBenson.service.TimeProdChannelBensonVO;
import kr.co.solbipos.sale.day.day.service.DayService;
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
 * @Class Name : TimeProdChannelBensonController.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/timeProdChannelBenson")
public class TimeProdChannelBensonController {

    private final SessionService sessionService;
    private final TimeProdChannelBensonService timeProdChannelBensonService;
    private final DayProdService dayProdService;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeProdChannelBensonController(SessionService sessionService, TimeProdChannelBensonService timeProdChannelBensonService, DayProdService dayProdService, DayService dayService, StoreChannelService storeChannelService){
        this.sessionService = sessionService;
        this.timeProdChannelBensonService = timeProdChannelBensonService;
        this.dayProdService = dayProdService;
        this.dayService = dayService;
        this.storeChannelService = storeChannelService;
    }

    /**
     * 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/timeProdChannelBenson/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("momsHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 주문채널 구분자 조회
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        String dlvrInFgColNm = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
          dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
          dlvrInFgColNm += (dlvrInFgColNm.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFgNm");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);
        model.addAttribute("dlvrInFgColNm", dlvrInFgColNm);

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
           timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "sale/benson/timeProdChannelBenson/timeProdChannelBenson";
   }

    /**
     * 상품별시간대매출(채널별) 조회
     * @param request
     * @param response
     * @param model
     * @param timeProdChannelBensonVO
     * @return
     * @author  김유승
     * @since   2026.09.09
     */

    @RequestMapping(value = "/timeProdChannelBenson/getTimeProdChannelBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdChannelBensonList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdChannelBensonVO timeProdChannelBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = timeProdChannelBensonService.getTimeProdChannelBensonList(timeProdChannelBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdChannelBensonVO);
    }

    /**
     * 상품별시간대매출(채널별) 엑셀다운로드
     * @param request
     * @param response
     * @param model
     * @param timeProdChannelBensonVO
     * @return
     * @author  김유승
     * @since   2026.09.09
     */

    @RequestMapping(value = "/timeProdChannelBenson/getTimeProdChannelBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdChannelBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdChannelBensonVO timeProdChannelBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = timeProdChannelBensonService.getTimeProdChannelBensonExcelList(timeProdChannelBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdChannelBensonVO);
    }

}
