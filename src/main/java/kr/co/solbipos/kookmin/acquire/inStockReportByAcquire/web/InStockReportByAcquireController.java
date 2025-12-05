package kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.InStockReportByAcquireService;
import kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.InStockReportByAcquireVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name  : InStockReportByAcquireController.java
 * @Description : 국민대 > 매입관리 > 매입처별 입고내역서
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
@RequestMapping(value = "/kookmin/acquire/inStockReportByAcquire")
public class InStockReportByAcquireController {

    private final SessionService sessionService;
    private final InStockReportByAcquireService inStockReportByAcquireService;

    /**
     *  Constructor Injection
     */
    public InStockReportByAcquireController(SessionService sessionService, InStockReportByAcquireService inStockReportByAcquireService) {
        this.sessionService = sessionService;
        this.inStockReportByAcquireService = inStockReportByAcquireService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/inStockReportByAcquire/view.sb", method = RequestMethod.GET)
    public String inStockReportByAcquire(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/acquire/inStockReportByAcquire/inStockReportByAcquire";
    }

    /**
     * 매입처별 입고 내역서 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   inStockReportByAcquireVO
     * @return  String
     * @author  김유승
     * @since   2025. 12. 02.
     */
    @RequestMapping(value = "/inStockReportByAcquire/getInStockReportByAcquireList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInStockReportByAcquireList(HttpServletRequest request, HttpServletResponse response,
                                          Model model, InStockReportByAcquireVO inStockReportByAcquireVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = inStockReportByAcquireService.getInStockReportByAcquireList(inStockReportByAcquireVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, inStockReportByAcquireVO);
    }
}
