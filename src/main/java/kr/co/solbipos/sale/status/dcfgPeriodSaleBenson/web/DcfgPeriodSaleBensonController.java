package kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.DcfgPeriodSaleBensonService;
import kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.DcfgPeriodSaleBensonVO;
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
 * @Class Name : DcfgPeriodSaleBensonController.java
 * @Description : 벤슨 > 매출분석 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.20
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/dcfgPeriodSaleBenson")
public class DcfgPeriodSaleBensonController {

    private final SessionService sessionService;
    private final DcfgPeriodSaleBensonService dcfgPeriodSaleBensonService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DcfgPeriodSaleBensonController(SessionService sessionService, DcfgPeriodSaleBensonService dcfgPeriodSaleBensonService) {
        this.sessionService = sessionService;
        this.dcfgPeriodSaleBensonService = dcfgPeriodSaleBensonService;
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

        return "sale/status/dcfgPeriodSaleBenson/dcfgPeriodSaleBenson";
    }

    /**
     * 할인구분기간상세 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param dcfgPeriodSaleBensonVO
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDcfgPeriodSaleBensonList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, DcfgPeriodSaleBensonVO dcfgPeriodSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dcfgPeriodSaleBensonService.getDcfgPeriodSaleBensonList(dcfgPeriodSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dcfgPeriodSaleBensonVO);
    }
}
