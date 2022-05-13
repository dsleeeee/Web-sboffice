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


//    /**
//     * 일자별 - 페이지 이동
//     * @param   request
//     * @param   response
//     * @param   model
//     * @return  String
//     * @author  안동관
//     * @since   2019. 02. 18.
//     */
//    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
//    public String dayView(HttpServletRequest request, HttpServletResponse response, Model model) {
//        DayVO dayVO = new DayVO();
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        // 결제수단 조회
//        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);
//
//        // 결제수단 코드를 , 로 연결하는 문자열 생성
//        String payCol = "";
//        for(int i=0; i < payColList.size(); i++) {
//            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
//        }
//        model.addAttribute("payColList", payColList);
//        model.addAttribute("payCol", payCol);
//
//        // 할인구분 조회
//        List<DefaultMap<String>> dcColList = dayService.getDcColList(dayVO, sessionInfoVO);
//
//        // 할인구분 코드를 , 로 연결하는 문자열 생성
//        String dcCol = "";
//        for(int i=0; i < dcColList.size(); i++) {
//            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
//        }
//        model.addAttribute("dcColList", dcColList);
//        model.addAttribute("dcCol", dcCol);
//
//        return "sale/day/day/day";
//    }


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
     * 일자(현금)종합 (일별종합 탭) - 일별종합 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayCashTotal/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCashTotalList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayCashTotalList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


    /**
     * 매장별 매출현황 팝업 - 매장별 매출현황 조회
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
     * 매장별 매출현황 팝업 - 매장별 매출현황 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  권지현
     * @since   2022.03.29
     */
    @RequestMapping(value = "/dayProdDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayProdDtlList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 매장별 매출현황 팝업 - 매장별 매출현황 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  권지현
     * @since   2022.03.30
     */
    @RequestMapping(value = "/dayDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayDtlList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 매장별 매출현황 팝업 - 매장별 매출현황 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  권지현
     * @since   2022.03.30
     */
    @RequestMapping(value = "/daySumAvg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySumAvgList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDaySumAvgList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }


    /**
     * 매장별 할인내역 팝업 - 매장별 할인내역 조회
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


    /**
     * 일자별(과면세별 탭) - 과면세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  이다솜
     * @since   2019. 06. 13.
     */
    @RequestMapping(value = "/dayTax/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTaxList(HttpServletRequest request, HttpServletResponse response,
                               Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayTaxList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 일자별(시간대별 탭) - 시간대별 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  이다솜
     * @since   2019. 06. 21.
     */
    @RequestMapping(value = "/dayTime/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTimeList(HttpServletRequest request, HttpServletResponse response,
                                Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayTimeList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 일자별(상품분류별 탭) - 분류레벨에 따른 상품분류 가져오기
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  이다솜
     * @since   2020. 03. 06.
     */
    @RequestMapping(value = "/dayProdClass/level.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdClassLevel(HttpServletRequest request, HttpServletResponse response,
                                      Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayProdClassLevel(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 일자별(상품분류별 탭) - 상품분류별 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayVO
     * @return  String
     * @author  이다솜
     * @since   2020. 03. 06.
     */
    @RequestMapping(value = "/dayProdClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdClassList(HttpServletRequest request, HttpServletResponse response,
                                 Model model, DayVO dayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayService.getDayProdClassList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayVO);
    }

    /**
     * 코너별 - 코너별 매출조회
     *
     * @param dayVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author  김설아
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/getDayCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCornerList(DayVO dayVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayService.getDayCornerList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayVO);
    }

    /**
     * 외식테이블별 - 외식테이블별매출조회
     *
     * @param dayVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author  김설아
     * @since   2020. 01. 09.
     */
    @RequestMapping(value = "/day/getDayTableList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTableList(DayVO dayVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayService.getDayTableList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayVO);
    }

    /**
     * 상품매출 상세내역 팝업 - 상품매출 상세내역 조회
     *
     * @param dayVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author  김설아
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/dayProdSaleDtl/getDayProdSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdSaleDtlList(DayVO dayVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayService.getDayProdSaleDtlList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayVO);
    }

    /**
     * 포스별 - 포스별매출조회
     *
     * @param dayVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author  김설아
     * @since   2019. 12. 18.
     */
    @RequestMapping(value = "/day/getDayPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPosList(DayVO dayVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayService.getDayPosList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayVO);
    }

    /**
     * 사원카드별 - 사원카드별 매출조회
     *
     * @param dayVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021.12.20
     */
    @RequestMapping(value = "/day/getDayEmpCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayEmpCardList(DayVO dayVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayService.getDayEmpCardList(dayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayVO);
    }
}
