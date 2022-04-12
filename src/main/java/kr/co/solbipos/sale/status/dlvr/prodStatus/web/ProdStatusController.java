package kr.co.solbipos.sale.status.dlvr.prodStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dlvr.prodStatus.service.ProdStatusService;
import kr.co.solbipos.sale.status.dlvr.prodStatus.service.ProdStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ProdStatusController.java
 * @Description : 매출관리 - 매출현황 - 배달상품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.11  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sale/status/dlvr/")
public class ProdStatusController {

    private final SessionService sessionService;
    private final ProdStatusService prodStatusService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ProdStatusController(SessionService sessionService, ProdStatusService prodStatusService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodStatusService = prodStatusService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 배달상품현황 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.11
     */
    @RequestMapping(value = "prodStatus/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "sale/status/dlvr/prodStatus";
    }

    /**
     * 배달상품현황 리스트 조회
     * @param prodStatusVO
     * @param request
     * @return
     * @author 이다솜
     * @since 2022.04.11
     */
    @RequestMapping(value = "prodStatus/getProdStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdStatusList(ProdStatusVO prodStatusVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = prodStatusService.getProdStatusList(prodStatusVO, sessionInfoVO);

        return returnListJson(Status.OK, result, prodStatusVO);
    }

}
