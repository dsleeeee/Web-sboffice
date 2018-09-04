package kr.co.solbipos.base.pay.coupon.web;

import kr.co.common.data.enums.CodeType;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.exception.CodeException;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponService;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : CouponController.java
 * @Description : 기초관리 > 결제수단 > 쿠폰등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/pay/coupon")
public class CouponController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    @Autowired
    CouponService service;
    @Autowired
    SessionService sessionService;

    /** util */
    @Autowired
    CmmCodeUtil cmmCodeUtil;
    @Autowired
    CmmEnvUtil cmmEnvUtil;

    /**
     * 쿠폰 등록 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/couponView.sb", method = RequestMethod.GET)
    public String prodClassView(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        String envstCd = "0019";
        String coupnEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));

        LOGGER.info("======================> coupnEnvstVal : "+ coupnEnvstVal);
        LOGGER.info("======================> CoupnEnvFg enum : "+ CoupnEnvFg.getEnum(coupnEnvstVal));

        // 환경설정값 체크 : [0019] 쿠폰등록-본사통제여부 확인 ( 1: 매장통제, 2:본사통제 )
        // 매장통제, 본사통제 둘다 메뉴 사용 가능하지만 null인 경우는 사용 불가능.
        // 해당 환경설정값이 없는 경우, 본사환경설정에서 환경설정 필요.
        if("".equals(coupnEnvstVal)) {
            throw new CodeException(CodeType.HQ_ENV, envstCd, "/error/envError.sb");
        } else{
            model.addAttribute("coupnEnvstVal", coupnEnvstVal);
            return "base/pay/coupon/couponView";
        }
    }

    /**
     * 쿠폰분류 조회
     * @param   payMethodClassVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/getCouponClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponClassList(PayMethodClassVO payMethodClassVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        LOGGER.debug(payMethodClassVO.getProperties());

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getCouponClassList(payMethodClassVO, sessionInfoVO);

        for( DefaultMap<String> f : list) {
            System.out.println(f);
        }

        return returnListJson(Status.OK, list, payMethodClassVO);
    }

    /**
     * 쿠폰분류 저장 (본사/매장)
     * @param   payMethodClassVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.10
     */
    @RequestMapping(value = "/class/saveCouponClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCouponClassList(@RequestBody PayMethodClassVO[] payMethodClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveCouponClassList(payMethodClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 쿠폰 조회
     * @param   couponVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/class/getCouponList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponList(CouponVO couponVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getCouponList(couponVO, sessionInfoVO);

        return returnListJson(Status.OK, list, couponVO);
    }

    /**
     * 쿠폰 저장 (본사/매장)
     * @param   couponVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.10
     */
    @RequestMapping(value = "/class/saveCouponList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCouponList(@RequestBody CouponVO[] couponVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveCouponList(couponVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 쿠폰 상품 조회
     * @param   couponProdVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.17
     */
    @RequestMapping(value = "/prod/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponList(CouponProdVO couponProdVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        List<DefaultMap<String>> prodList = service.getProdList(couponProdVO);

        return returnListJson(Status.OK, prodList, couponProdVO);
    }

    /**
     * 쿠폰 적용 상품 등록
     * @param couponProdVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/prod/registCouponProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registCouponProd(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.registCouponProd(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }


    /**
     * 쿠폰 적용 상품 삭제
     * @param couponProdVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/prod/deleteCouponProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCouponProd(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.deleteCouponProd(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
