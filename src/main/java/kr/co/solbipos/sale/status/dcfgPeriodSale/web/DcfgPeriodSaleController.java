package kr.co.solbipos.sale.status.dcfgPeriodSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgVO;
import kr.co.solbipos.sale.status.dcfgPeriodSale.service.DcfgPeriodSaleService;
import kr.co.solbipos.sale.status.dcfgPeriodSale.service.DcfgPeriodSaleVO;
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
 * @Class Name : DcfgPeriodSaleController.java
 * @Description : 매출관리 > 매출현황2 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.14  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.06.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/dcfgPeriodSale")
public class DcfgPeriodSaleController {

    private final SessionService sessionService;
    private final DcfgPeriodSaleService dcfgPeriodSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DcfgPeriodSaleController(SessionService sessionService, DcfgPeriodSaleService dcfgPeriodSaleService) {
        this.sessionService = sessionService;
        this.dcfgPeriodSaleService = dcfgPeriodSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/dcfgPeriodSale/dcfgPeriodSale";
    }

    /**
     * 할인구분기간상세 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param dcfgPeriodSaleVO
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcfgPeriodSaleList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, DcfgPeriodSaleVO dcfgPeriodSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcfgPeriodSaleService.getDcfgPeriodSaleList(dcfgPeriodSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcfgPeriodSaleVO);
    }
}
