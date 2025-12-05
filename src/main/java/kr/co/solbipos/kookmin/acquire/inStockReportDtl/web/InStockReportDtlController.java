package kr.co.solbipos.kookmin.acquire.inStockReportDtl.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.InStockReportDtlService;
import kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.InStockReportDtlVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name  : InStockReportDtlController.java
 * @Description : 국민대 > 매입관리 > 매입처별 상세매입내역(상품별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/acquire/inStockReportDtl")
public class InStockReportDtlController {

    private final SessionService sessionService;
    private final InStockReportDtlService inStockReportDtlService;

    /**
     *  Constructor Injection
     */
    public InStockReportDtlController(SessionService sessionService, InStockReportDtlService inStockReportDtlService) {
        this.sessionService = sessionService;
        this.inStockReportDtlService = inStockReportDtlService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/inStockReportDtl/view.sb", method = RequestMethod.GET)
    public String inStockReportDtl(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/acquire/inStockReportDtl/inStockReportDtl";
    }

    /**
     * 매입처별 상세내역서(상품별) - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   inStockReportDtlVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 03.
     */
    @RequestMapping(value = "/inStockReportDtl/getInStockReportDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInStockReportDtlList(HttpServletRequest request, HttpServletResponse response,
                                                Model model, InStockReportDtlVO inStockReportDtlVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = inStockReportDtlService.getInStockReportDtlList(inStockReportDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, inStockReportDtlVO);
    }
}
