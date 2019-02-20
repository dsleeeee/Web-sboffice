package kr.co.solbipos.sale.day.day.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
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
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 일자별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.02.18  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.02.18
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/day/day")
public class DayController {
    private final SessionService sessionService;
    private final DayService dayService;

    @Autowired
    public DayController(SessionService sessionService, DayService dayService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
    }


    /**
     * 일자별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String dayView(HttpServletRequest request, HttpServletResponse response, Model model) {
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

        return "sale/day/day/day";
    }


    /**
     * 일자별(일별종합 탭) - 일별종합 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayTotal/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTotalList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayTotalList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


    /**
     * 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayStoreDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayStoreDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayStoreDtlList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


    /**
     * 일자별(일별종합 탭) - 일자 매장별 할인현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 20.
     */
    @RequestMapping(value = "/dayStoreDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayStoreDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayStoreDcList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


    /**
     * 일자별(할인구분별 탭) - 할인구분 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 20.
     */
    @RequestMapping(value = "/dayDc/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayDcList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayDcList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


}
