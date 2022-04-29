package kr.co.solbipos.mobile.sale.status.appr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.appr.service.MobileApprService;
import kr.co.solbipos.mobile.sale.status.appr.service.MobileApprVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileApprController.java
 * @Description : (모바일) 매출현황 > 승인현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/appr")
public class MobileApprController {

    private final SessionService sessionService;
    private final MobileApprService mobileApprService;
    private final MobileProdSaleService mobileProdSaleService;

    public MobileApprController(SessionService sessionService, MobileApprService mobileApprService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileApprService = mobileApprService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileAppr/list.sb", method = RequestMethod.GET)
    public String mobileApprView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());
        return "mobile/sale/status/appr/mobileAppr";
    }

    /**
     * 승인현황 - 조회
     *
     * @param mobileApprVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/appr/getMobileApprList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileApprList(MobileApprVO mobileApprVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileApprService.getMobileApprList(mobileApprVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileApprVO);
    }

    /**
     * 상세 - 조회
     *
     * @param mobileApprVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/appr/getMobileApprDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileApprDtlList(MobileApprVO mobileApprVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileApprService.getMobileApprDtlList(mobileApprVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileApprVO);
    }

}