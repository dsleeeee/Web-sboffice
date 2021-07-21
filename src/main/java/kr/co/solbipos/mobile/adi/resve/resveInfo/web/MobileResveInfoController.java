package kr.co.solbipos.mobile.adi.resve.resveInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageService;
import kr.co.solbipos.mobile.adi.resve.resveInfo.service.MobileResveInfoService;
import kr.co.solbipos.mobile.adi.resve.resveInfo.service.MobileResveInfoVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
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
 * @Class Name : MobileResveInfoController.java
 * @Description : (모바일) 부가서비스 > 예약현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/adi/resve/resveInfo")
public class MobileResveInfoController {

    private final SessionService sessionService;
    private final MobileProdSaleService mobileProdSaleService;
    private final MobileResveInfoService mobileResveInfoService;

    @Autowired
    public MobileResveInfoController(SessionService sessionService, MobileProdSaleService mobileProdSaleService, MobileResveInfoService mobileResveInfoService) {
        this.sessionService = sessionService;
        this.mobileProdSaleService = mobileProdSaleService;
        this.mobileResveInfoService = mobileResveInfoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileResveInfo/list.sb", method = RequestMethod.GET)
    public String mobileResveInfo(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        return "mobile/adi/resve/resveInfo/mobileResveInfo";
    }

    /**
     * 조회
     *
     * @param mobileResveInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 07. 09
     */
    @RequestMapping(value = "/mobileResveInfo/getResveInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getResveInfo(MobileResveInfoVO mobileResveInfoVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = mobileResveInfoService.getResveList(mobileResveInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileResveInfoVO);
    }
}