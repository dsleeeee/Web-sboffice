package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoService;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MobilePayInfoController.java
 * @Description : (모바일) 공통 결제수단 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/cmmSalePopup/payInfo")
public class MobilePayInfoController {

    private final SessionService sessionService;
    private final MobilePayInfoService mobilePayInfoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobilePayInfoController(SessionService sessionService, MobilePayInfoService mobilePayInfoService) {
        this.sessionService = sessionService;
        this.mobilePayInfoService = mobilePayInfoService;
    }

    /**
     * 결제수단 신용카드 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 09. 13.
     */
    @RequestMapping(value = "/mobileCard/getMobileCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileCardList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileCardList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 현금 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileCash/getMobileCashList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileCashList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileCashList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 페이코 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobilePayco/getMobilePaycoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobilePaycoList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobilePaycoList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 VMEM 포인트 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileVpoint/getMobileVpointList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileVpointList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileVpointList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 VMEM 전자상품권 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileVcharge/getMobileVchargeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileVchargeList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileVchargeList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 모바일페이권 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileMpay/getMobileMpayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMpayList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileMpayList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 모바일쿠폰 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileMcoupn/getMobileMcoupnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMcoupnList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileMcoupnList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 포인트 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobilePoint/getMobilePointList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobilePointList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobilePointList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 선불 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobilePrepaid/getMobilePrepaidList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobilePrepaidList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobilePrepaidList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 후불 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobilePostpaid/getMobilePostpaidList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobilePostpaidList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobilePostpaidList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 상품권 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileGift/getMobileGiftList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileGiftList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileGiftList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 식권 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileFstmp/getMobileFstmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileFstmpList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileFstmpList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 제휴할인 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobilePartner/getMobilePartnerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobilePartnerList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobilePartnerList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 사원카드 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileEmpCard/getMobileEmpCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileEmpCardList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileEmpCardList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 가승인 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 09. 13.
     */
    @RequestMapping(value = "/mobileTemporary/getMobileTemporaryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileTemporaryList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }

    /**
     * 결제수단 스마트오더 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.09.20
     */
    @RequestMapping(value = "/mobileVorder/getMobileVorderList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileVorderList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileVorderList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }
}