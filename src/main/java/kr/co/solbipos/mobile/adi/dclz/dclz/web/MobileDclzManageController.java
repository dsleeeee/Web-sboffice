package kr.co.solbipos.mobile.adi.dclz.dclz.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageService;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageVO;
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
 * @Class Name : MobileDclzManageController.java
 * @Description : (모바일) 부가서비스 > 근태현황
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
@RequestMapping("/mobile/adi/dclz/dclz")
public class MobileDclzManageController {

    private final SessionService sessionService;
    private final MobileProdSaleService mobileProdSaleService;
    private final MobileDclzManageService mobileDclzManageService;

    @Autowired
    public MobileDclzManageController(SessionService sessionService, MobileProdSaleService mobileProdSaleService, MobileDclzManageService mobileDclzManageService) {
        this.sessionService = sessionService;
        this.mobileProdSaleService = mobileProdSaleService;
        this.mobileDclzManageService = mobileDclzManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileDclzManage/list.sb", method = RequestMethod.GET)
    public String mobileDclzManage(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        return "mobile/adi/dclz/dclz/mobileDclzManage";
    }

    /**
     * 조회
     *
     * @param mobileDclzManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 07. 09
     */
    @RequestMapping(value = "/mobileDclzManage/getDclzManage.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDclzManage(MobileDclzManageVO mobileDclzManageVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = mobileDclzManageService.getDclzManage(mobileDclzManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDclzManageVO);
    }
}