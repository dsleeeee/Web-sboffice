package kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.DayPayInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.DayPayInfoVO;
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
 * @Class Name : DayPayInfoController.java
 * @Description : 매출공통팝업 - 일자별 매출공통팝업 결제수단 상세 팝업
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
@RequestMapping("/sale/cmmSalePopup/dayPayInfo")
public class DayPayInfoController {
    private final SessionService sessionService;
    private final DayPayInfoService dayPayInfoService;

    @Autowired
    public DayPayInfoController(SessionService sessionService, DayPayInfoService dayPayInfoService) {
        this.sessionService = sessionService;
        this.dayPayInfoService = dayPayInfoService;
    }


    /**
     * 일자별 매출공통팝업 - 신용카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayCard/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCardList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayCardList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 현금 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayCash/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCashList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayCashList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - PAYCO 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayPayco/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPaycoList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayPaycoList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - VMEM 포인트 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayVpoint/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayVpointList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayVpointList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayVcoupn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayVcoupnList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayVcoupnList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayVcharge/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayVchargeList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayVchargeList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 모바일페이 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayMpay/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMpayList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayMpayList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 모바일쿠폰 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayMcoupn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMcoupnList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayMcoupnList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 포인트 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayPoint/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPointList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayPointList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 회원선불 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayPrepaid/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPrepaidList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayPrepaidList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 회원후불 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayPostpaid/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPostpaidList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayPostpaidList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 상품권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayGift/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayGiftList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayGiftList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 식권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayFstmp/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayFstmpList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayFstmpList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 제휴카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayPartner/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPartnerList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayPartnerList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 사원카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayEmpCard/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayEmpCardList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayEmpCardList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }


    /**
     * 일자별 매출공통팝업 - 사원카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 18.
     */
    @RequestMapping(value = "/dayTemporary/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTemporaryList(HttpServletRequest request, HttpServletResponse response,
        Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDayTemporaryList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }

    /**
     * 일자별 매출공통팝업 - 스마트오더 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayPayInfoVO
     * @return  String
     * @author  권지현
     * @since   2021.08.11
     */
    @RequestMapping(value = "/daySmartorder/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySmartorderList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, DayPayInfoVO dayPayInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dayPayInfoService.getDaySmartorderList(dayPayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayPayInfoVO);
    }
}
