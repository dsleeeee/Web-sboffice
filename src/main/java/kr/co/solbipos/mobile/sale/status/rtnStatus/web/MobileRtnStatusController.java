package kr.co.solbipos.mobile.sale.status.rtnStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import kr.co.solbipos.mobile.sale.status.rtnStatus.service.MobileRtnStatusService;
import kr.co.solbipos.mobile.sale.status.rtnStatus.service.MobileRtnStatusVO;
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
 * @Class Name : MobileRtnStatusController.java
 * @Description : (모바일) 매출현황 > 반품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/rtnStatus")
public class MobileRtnStatusController {

    private final SessionService sessionService;
    private final MobileRtnStatusService mobileRtnStatusService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileRtnStatusController(SessionService sessionService, MobileRtnStatusService mobileRtnStatusService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileRtnStatusService = mobileRtnStatusService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileRtnStatus/list.sb", method = RequestMethod.GET)
    public String mobileRtnStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/rtnStatus/mobileRtnStatus";
    }

    /**
     * 반품현황 - 조회
     *
     * @param mobileRtnStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.27
     */
    @RequestMapping(value = "/rtnStatus/getMobileRtnStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileRtnStatusList(MobileRtnStatusVO mobileRtnStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileRtnStatusService.getMobileRtnStatusList(mobileRtnStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileRtnStatusVO);
    }

    /**
     * 반품현황 상세 팝업 - 조회
     *
     * @param mobileRtnStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.27
     */
    @RequestMapping(value = "/rtnStatus/getMobileRtnStatusDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileRtnStatusDtlList(MobileRtnStatusVO mobileRtnStatusVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileRtnStatusService.getMobileRtnStatusDtlList(mobileRtnStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileRtnStatusVO);
    }

}