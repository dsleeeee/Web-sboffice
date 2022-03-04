package kr.co.solbipos.mobile.prod.prodSoldOut.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutService;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
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
 * @Class Name : MobileProdSoldOutController.java
 * @Description : 상품관리 > 품절관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.02  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/prod/prodSoldOut")
public class MobileProdSoldOutController {

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final MobileProdSaleService mobileProdSaleService;
    private final MobileProdSoldOutService mobileProdSoldOutService;

    public MobileProdSoldOutController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, MobileProdSaleService mobileProdSaleService, MobileProdSoldOutService mobileProdSoldOutService) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.mobileProdSaleService = mobileProdSaleService;
        this.mobileProdSoldOutService = mobileProdSoldOutService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileProdSoldOut/list.sb", method = RequestMethod.GET)
    public String mobileProdSoldOutView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // (상품관리)브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        return "mobile/prod/prodSoldOut/mobileProdSoldOut";
    }

    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.03.03
     */
    @RequestMapping(value = "/mobileProdSoldOut/getMobileProdSoldOutList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileProdSoldOutList(HttpServletRequest request, HttpServletResponse response, Model model, MobileProdSoldOutVO mobileProdSoldOutVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileProdSoldOutService.getMobileProdSoldOutList(mobileProdSoldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileProdSoldOutVO);
    }

    /**
     * 상품 품절관리 - 품절여부 저장
     * @param mobileProdSoldOutVOs MobileProdSoldOutVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/mobileProdSoldOut/getMobileProdSoldOutSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileProdSoldOutSave(@RequestBody MobileProdSoldOutVO[] mobileProdSoldOutVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = mobileProdSoldOutService.getMobileProdSoldOutSave(mobileProdSoldOutVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
