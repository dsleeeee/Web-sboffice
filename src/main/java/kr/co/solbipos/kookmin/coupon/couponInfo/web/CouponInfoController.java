package kr.co.solbipos.kookmin.coupon.couponInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.coupon.couponInfo.service.CouponInfoService;
import kr.co.solbipos.kookmin.coupon.couponInfo.service.CouponInfoVO;
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
 * @Class Name  : CouponInfoController.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/kookmin/coupon/couponInfo")
public class CouponInfoController {

    private final SessionService sessionService;
    private final CouponInfoService couponInfoService;

    /**
     * Constructor Injection
     */
    public CouponInfoController(SessionService sessionService, CouponInfoService couponInfoService) {
        this.sessionService = sessionService;
        this.couponInfoService = couponInfoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/couponInfo/view.sb", method = RequestMethod.GET)
    public String couponInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/coupon/couponInfo/couponInfo";
    }

    /**
     * 쿠폰 정보 조회
     *
     * @param   couponInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 23.
     */
    @RequestMapping(value = "/couponInfo/getCouponInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponInfoList(CouponInfoVO couponInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponInfoService.getCouponInfoList(couponInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, couponInfoVO);
    }

    /**
     * 쿠폰적용관리 팝업 - 상품 조회
     *
     * @param   couponInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 24.
     */
    @RequestMapping(value = "/couponInfo/getCouponSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponSelectProdList(CouponInfoVO couponInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponInfoService.getCouponSelectProdList(couponInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, couponInfoVO);
    }

    /**
     * 쿠폰적용관리 팝업 - 부서 조회
     *
     * @param   couponInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 24.
     */
    @RequestMapping(value = "/couponInfo/getcouponSelectPartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getcouponSelectPartList(CouponInfoVO couponInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponInfoService.getcouponSelectPartList(couponInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, couponInfoVO);
    }

    /**
     * 쿠폰 등록
     *
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 24.
     */
    @RequestMapping(value = "/couponInfo/getCouponRegist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponRegist(@RequestBody CouponInfoVO couponInfoVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = couponInfoService.getCouponRegist(couponInfoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 쿠폰상세정보 조회
     * @param   couponInfoVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 24.
     */
    @RequestMapping(value = "/couponInfo/getCouponInfoDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponInfoDtlList(CouponInfoVO couponInfoVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponInfoService.getCouponInfoDtlList(couponInfoVO, sessionInfoVO);

        return returnJson(Status.OK, "list", result);
    }

    /**
     * 쿠폰적용관리 팝업 TMP테이블 제거
     * @param   couponInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 28.
     */
    @RequestMapping(value = "couponInfo/getDeleteTmpData.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteTmpData(@RequestBody CouponInfoVO couponInfoVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = couponInfoService.getDeleteTmpData(couponInfoVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 쿠폰적용관리 팝업 쿠폰발행 엑셀업로드
     * @param   couponInfoVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 28.
     * @return
     */
    @RequestMapping(value = "/couponInfo/getCouponIssueExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponIssueExcelUpload(@RequestBody CouponInfoVO[] couponInfoVOs, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = couponInfoService.getCouponIssueExcelUpload(couponInfoVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 쿠폰적용관리 팝업 쿠폰발행 저장
     * @param   couponInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 29.
     */
    @RequestMapping(value = "couponInfo/saveCouponIssue.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCouponIssue(@RequestBody CouponInfoVO couponInfoVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = couponInfoService.saveCouponIssue(couponInfoVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 쿠폰적용관리 팝업 회수쿠폰 조회
     * @param   couponInfoVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 29.
     */
    @RequestMapping(value = "/couponInfo/getSelectSaleCouponList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectSaleCouponList(CouponInfoVO couponInfoVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponInfoService.getSelectSaleCouponList(couponInfoVO, sessionInfoVO);

        return returnJson(Status.OK, "list", result);
    }

}
