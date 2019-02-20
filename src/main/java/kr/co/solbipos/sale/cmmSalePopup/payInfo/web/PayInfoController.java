package kr.co.solbipos.sale.cmmSalePopup.payInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.payInfo.service.PayInfoService;
import kr.co.solbipos.sale.cmmSalePopup.payInfo.service.PayInfoVO;
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
 * @Class Name : PayInfoController.java
 * @Description : 매출공통팝업 - 결제수단 상세 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.02.07  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.02.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/cmmSalePopup/payInfo")
public class PayInfoController {
    private final SessionService sessionService;
    private final PayInfoService payInfoService;

    @Autowired
    public PayInfoController(SessionService sessionService, PayInfoService payInfoService) {
        this.sessionService = sessionService;
        this.payInfoService = payInfoService;
    }


    /**
     * 당일매출상세현황 - 신용카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 07.
     */
    @RequestMapping(value = "/card/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getCardList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 현금 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 08.
     */
    @RequestMapping(value = "/cash/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getCashList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - PAYCO 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 08.
     */
    @RequestMapping(value = "/payco/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPaycoList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getPaycoList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - VMEM 포인트 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 08.
     */
    @RequestMapping(value = "/vpoint/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVpointList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getVpointList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - VMEM 쿠폰 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 08.
     */
    @RequestMapping(value = "/vcoupn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVcoupnList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getVcoupnList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - VMEM 전자상품권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 08.
     */
    @RequestMapping(value = "/vcharge/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVchargeList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getVchargeList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 모바일페이 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/mpay/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMpayList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getMpayList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 모바일쿠폰 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/mcoupn/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMcoupnList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getMcoupnList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 포인트 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/point/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPointList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getPointList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 회원선불 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/prepaid/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrepaidList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getPrepaidList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 회원후불 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/postpaid/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getPostpaidList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 상품권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/gift/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGiftList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getGiftList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 식권 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 11.
     */
    @RequestMapping(value = "/fstmp/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFstmpList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getFstmpList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 제휴카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 12.
     */
    @RequestMapping(value = "/partner/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPartnerList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getPartnerList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 사원카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 12.
     */
    @RequestMapping(value = "/empCard/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpCardList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getEmpCardList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }


    /**
     * 당일매출상세현황 - 사원카드 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 12.
     */
    @RequestMapping(value = "/temporary/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTemporaryList(HttpServletRequest request, HttpServletResponse response,
        Model model, PayInfoVO payInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payInfoService.getTemporaryList(payInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payInfoVO);
    }

}
