package kr.co.solbipos.sale.marketing.saleCancelStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.saleCancelStatus.service.SaleCancelStatusService;
import kr.co.solbipos.sale.marketing.saleCancelStatus.service.SaleCancelStatusVO;
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
 * @Class Name  : SaleCancelStatusController.java
 * @Description : 미스터피자 > 마케팅조회 > 취소현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.31
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/marketing/saleCancelStatus")
public class SaleCancelStatusController {

    private final SessionService sessionService;
    private final SaleCancelStatusService saleCancelStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleCancelStatusController(SessionService sessionService, SaleCancelStatusService saleCancelStatusService) {
        this.sessionService = sessionService;
        this.saleCancelStatusService = saleCancelStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleCancelStatus/list.sb", method = RequestMethod.GET)
    public String saleCancelStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/marketing/saleCancelStatus/saleCancelStatus";
    }

    /**
     * 취소현황 - 전체점포탭 조회
     *
     * @param   saleCancelStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 31.
     */
    @RequestMapping(value = "/saleCancelStatus/getSaleCancelStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleCancelStatusList(SaleCancelStatusVO saleCancelStatusVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleCancelStatusService.getSaleCancelStatusList(saleCancelStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleCancelStatusVO);
    }

    /**
     * 취소현황 - 전체점포탭 상세조회
     *
     * @param   saleCancelStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 31.
     */
    @RequestMapping(value = "/saleCancelStatus/getSaleCancelStatusDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleCancelStatusDtlList(SaleCancelStatusVO saleCancelStatusVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleCancelStatusService.getSaleCancelStatusDtlList(saleCancelStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleCancelStatusVO);
    }

    /**
     * 취소현황 - 선택점포탭 상세조회
     *
     * @param   saleCancelStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 31.
     */
    @RequestMapping(value = "/saleCancelStatus/getSaleCancelStatusStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleCancelStatusStoreList(SaleCancelStatusVO saleCancelStatusVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleCancelStatusService.getSaleCancelStatusStoreList(saleCancelStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleCancelStatusVO);
    }
}
