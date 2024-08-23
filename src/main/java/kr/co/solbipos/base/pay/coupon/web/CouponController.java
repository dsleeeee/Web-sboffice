package kr.co.solbipos.base.pay.coupon.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.*;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private final CouponService service;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public CouponController(CouponService service, SessionService sessionService,
        CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.service = service;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

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
        model.addAttribute("mappingFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1118"), "0"));

//        String envstCd = "0019";
//        String coupnEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
//        String orgnFg = String.valueOf(sessionInfoVO.getOrgnFg());


        // 환경설정값 체크 : [0019] 쿠폰등록-본사통제여부 확인 ( 1: 매장통제, 2:본사통제 )
        // 매장통제, 본사통제 둘다 메뉴 사용 가능하지만 null인 경우는 사용 불가능.
        // 해당 환경설정값이 없는 경우, 본사환경설정에서 환경설정 필요.
        // 본사통제인데 매장에서 접속시, 권한 오류
//        if("".equals(coupnEnvstVal) || ("2".equals(coupnEnvstVal) && sessionInfoVO.getOrgnFg() == OrgnFg.STORE )) {
//            throw new CodeException(CodeType.HQ_ENV, envstCd, "/error/envError.sb");
//        } else{
//            model.addAttribute("coupnEnvstVal", coupnEnvstVal);
//            return "base/pay/coupon/couponView";
//        }

        // 원래 본사통제면 매장 접근 금지였는데
        // 매장에서 등록은 안되고 조회만 가능하도록 수정 (2018-10-04)

//        model.addAttribute("coupnEnvstVal", coupnEnvstVal);
        // [0019] 사용하지 않는 코드인데 에러나서 고정값으로 수정 (2021-09-30)
        model.addAttribute("coupnEnvstVal", "1");


        // [1299] 프랜매장-쿠폰순서수정허용여부
        model.addAttribute("couponSeqChgVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1299"), "0"));


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
     * 쿠폰분류 매장적용 (본사/매장)
     * @param   payMethodClassVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.12.28
     */
    @RequestMapping(value = "/class/applyCouponClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result applyCouponClassList(@RequestBody PayMethodClassVO[] payMethodClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result  = service.applyCouponClassList(payMethodClassVOs, sessionInfoVO);

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
     * 쿠폰 적용/미적용 상품 조회
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
    public Result getProdList(CouponProdVO couponProdVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> prodList = service.getProdList(couponProdVO, sessionInfoVO);

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

        int result =  service.registCouponProd(couponProdVOs, sessionInfoVO);

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


    /**
     * 쿠폰 적용/미적용 매장 조회
     * @param   couponStoreVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.17
     */
    @RequestMapping(value = "/store/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(CouponStoreVO couponStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = service.getStoreList(couponStoreVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, couponStoreVO);
    }

    /**
     * 쿠폰 적용 매장 등록 : 해당매장에 쿠폰 + 쿠폰별상품 적용
     * @param couponStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/store/registCouponStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registCouponProd(@RequestBody CouponStoreVO[] couponStoreVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.registCouponStore(couponStoreVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 쿠폰 적용 매장 삭제 : 해당매장에 쿠폰 + 쿠폰별상품 삭제
     * @param couponStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/store/deleteCouponStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCouponStore(@RequestBody CouponStoreVO[] couponStoreVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result =  service.deleteCouponStore(couponStoreVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 쿠폰 순서저장
     *
     * @param couponVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 14.
     */
    @RequestMapping(value = "/class/getCouponSeqChgSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponSeqChgSave(@RequestBody CouponVO[] couponVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.getCouponSeqChgSave(couponVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 쿠폰순서 매장적용 팝업 - 조회
     *
     * @param couponVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 14.
     */
    @RequestMapping(value = "/class/getCouponSeqChgStoreRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponSeqChgStoreRegistList(CouponVO couponVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getCouponSeqChgStoreRegistList(couponVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, couponVO);
    }

    /**
     * 쿠폰순서 매장적용 팝업 - 저장
     *
     * @param couponVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 14.
     */
    @RequestMapping(value = "/class/getCouponSeqChgStoreRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGuestManageSave(@RequestBody CouponVO[] couponVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.getCouponSeqChgStoreRegistSave(couponVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제외상품 등록/미등록 상품 조회
     * @param   couponProdVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.14
     */
    @RequestMapping(value = "/prod/getExceptProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExceptProdList(CouponProdVO couponProdVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> exceptProdList = service.getExceptProdList(couponProdVO, sessionInfoVO);

        return returnListJson(Status.OK, exceptProdList, couponProdVO);
    }

    /**
     * 쿠폰 제외상품 등록
     * @param   couponProdVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.14
     */
    @RequestMapping(value = "/prod/registCouponProdExcept.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registCouponProdExcept(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result =  service.registCouponProdExcept(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 쿠폰 제외상품 삭제
     * @param   couponProdVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.14
     */
    @RequestMapping(value = "/prod/deleteCouponProdExcept.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCouponProdExcept(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.deleteCouponProdExcept(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 적용대상소분류 등록/미등록 조회
     * @param   couponProdVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.14
     */
    @RequestMapping(value = "/prod/getProdClsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClsList(CouponProdVO couponProdVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> exceptProdList = service.getProdClsList(couponProdVO, sessionInfoVO);

        return returnListJson(Status.OK, exceptProdList, couponProdVO);
    }

    /**
     * 적용대상 소분류 등록
     * @param   couponProdVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.16
     */
    @RequestMapping(value = "/prod/registCouponProdCls.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registCouponProdCls(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result =  service.registCouponProdCls(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 적용대상 소분류 삭제
     * @param   couponProdVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.16
     */
    @RequestMapping(value = "/prod/deleteCouponProdCls.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCouponProdCls(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.deleteCouponProdCls(couponProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 엑셀 업로드
     * @param   couponProdVOs
     * @param   request
     * @return  String
     * @author  김유승
     * @since   2024.08.16
     */
    @RequestMapping(value = "/prod/getExcelUploadSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelUploadSave(@RequestBody CouponProdVO[] couponProdVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.getExcelUploadSave(couponProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품 양식다운로드
     * @param   couponProdVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.08.20
     */
    @RequestMapping(value = "/prod/getExcelProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelProdList(CouponProdVO couponProdVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> prodList = service.getExcelProdList(couponProdVO, sessionInfoVO);

        return returnListJson(Status.OK, prodList, couponProdVO);
    }
}
