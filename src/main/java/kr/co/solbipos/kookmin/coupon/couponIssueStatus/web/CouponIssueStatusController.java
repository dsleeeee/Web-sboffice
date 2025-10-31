package kr.co.solbipos.kookmin.coupon.couponIssueStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.CouponIssueStatusService;
import kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.CouponIssueStatusVO;
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
/**
 * @Class Name  : CouponIssueStatusController.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰상태관리(관리자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/kookmin/coupon/couponIssueStatus")
public class CouponIssueStatusController {

    private final CouponIssueStatusService couponIssueStatusService;
    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    public CouponIssueStatusController(CouponIssueStatusService couponIssueStatusService, SessionService sessionService) {
        this.couponIssueStatusService = couponIssueStatusService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/couponIssueStatus/view.sb", method = RequestMethod.GET)
    public String couponInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/coupon/couponIssueStatus/couponIssueStatus";
    }

    /**
     * 쿠폰 정보 조회
     *
     * @param   couponIssueStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 30.
     */
    @RequestMapping(value = "/couponIssueStatus/getCouponIssueStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCouponIssueStatusList(CouponIssueStatusVO couponIssueStatusVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = couponIssueStatusService.getCouponIssueStatusList(couponIssueStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, couponIssueStatusVO);
    }

    /**
     * 쿠폰상태변환 저장
     * @param   couponIssueStatusVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 30.
     */
    @RequestMapping(value = "/couponIssueStatus/saveCouponIssueStatus.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCouponIssueStatus(@RequestBody CouponIssueStatusVO[] couponIssueStatusVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = couponIssueStatusService.saveCouponIssueStatus(couponIssueStatusVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
