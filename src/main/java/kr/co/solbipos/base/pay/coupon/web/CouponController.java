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
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponService;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /** service */
    @Autowired
    CouponService service;
    @Autowired
    SessionService sessionService;

    /** util */
    @Autowired
    CmmCodeUtil cmmCodeUtil;

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

        // 환경변수 체크
        if(cmmCodeUtil.getHqEnvst(sessionInfoVO, envstCd) == null ) {
            String msg = "";

            throw new CodeException(CodeType.HQ_ENV, envstCd, "/error/envError.sb");
        }

        return "base/pay/coupon/couponView";
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getCouponClassList(payMethodClassVO, sessionInfoVO);

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
     * 쿠폰 적용상품 등록 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.09
     */
    @RequestMapping(value = "/prod/couponProdView.sb", method = RequestMethod.GET)
    public String couponProdView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "base/pay/coupon/couponProdView";
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> registProdList = service.getRegistProdList(couponProdVO, sessionInfoVO);
        List<DefaultMap<String>> noRegistProdList = service.getNoRegistProdList(couponProdVO, sessionInfoVO);

        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("registProdList", registProdList);
        resultMap.put("noRegistProdList", noRegistProdList);

        return returnListJson(Status.OK, resultMap, couponProdVO);
    }

}
