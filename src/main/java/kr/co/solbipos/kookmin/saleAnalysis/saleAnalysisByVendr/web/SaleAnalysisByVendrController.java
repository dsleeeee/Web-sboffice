package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service.SaleAnalysisByVendrService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service.SaleAnalysisByVendrVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name  : SaleAnalysisByVendrController.java
 * @Description : 국민대 > 매출분석 > 매입처별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/saleAnalysis/saleAnalysisByVendr")
public class SaleAnalysisByVendrController {

    private final SessionService sessionService;
    private final SaleAnalysisByVendrService saleAnalysisByVendrService;

    /**
     * Constructor Injection
     */
    public SaleAnalysisByVendrController(SessionService sessionService, SaleAnalysisByVendrService saleAnalysisByVendrService) {
        this.sessionService = sessionService;
        this.saleAnalysisByVendrService = saleAnalysisByVendrService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleAnalysisByVendr/view.sb", method = RequestMethod.GET)
    public String saleAnalysisByVendr(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/saleAnalysis/saleAnalysisByVendr/saleAnalysisByVendr";
    }

    /**
     * 매입처별 매출분석 조회
     *
     * @param   saleAnalysisByVendrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 10.
     */
    @RequestMapping(value = "/saleAnalysisByVendr/getSaleAnalysisByVendrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalysisByVendrList(SaleAnalysisByVendrVO saleAnalysisByVendrVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleAnalysisByVendrService.getSaleAnalysisByVendrList(saleAnalysisByVendrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleAnalysisByVendrVO);
    }
}
