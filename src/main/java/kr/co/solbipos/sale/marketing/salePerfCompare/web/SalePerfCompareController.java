package kr.co.solbipos.sale.marketing.salePerfCompare.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.salePerfCompare.service.SalePerfCompareService;
import kr.co.solbipos.sale.marketing.salePerfCompare.service.SalePerfCompareVO;
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
 * @Class Name : SalePerfCompareController.java
 * @Description : 미스터피자 > 마케팅조회 > 매출실적비교
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.08  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/marketing/salePerfCompare")
public class SalePerfCompareController {

    private final SessionService sessionService;
    private final SalePerfCompareService salePerfCompareService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SalePerfCompareController(SessionService sessionService, SalePerfCompareService salePerfCompareService) {
        this.sessionService = sessionService;
        this.salePerfCompareService = salePerfCompareService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/salePerfCompare/list.sb", method = RequestMethod.GET)
    public String salePerfCompareView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/marketing/salePerfCompare/salePerfCompare";
    }

    /**
     * 채널별매출 - 조회
     *
     * @param   salePerfCompareVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 08.
     */
    @RequestMapping(value = "/salePerfCompare/getSalePerfCompareList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareList(SalePerfCompareVO salePerfCompareVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareService.getSalePerfCompareList(salePerfCompareVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareVO);
    }

    /**
     * 취소현황 - 전체점포탭 상세조회
     *
     * @param   salePerfCompareVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 11.
     */
    @RequestMapping(value = "/salePerfCompare/getSalePerfCompareDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareDtlList(SalePerfCompareVO salePerfCompareVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareService.getSalePerfCompareDtlList(salePerfCompareVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareVO);
    }

    /**
     * 채널별매출 - 조회
     *
     * @param   salePerfCompareVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 08. 12.
     */
    @RequestMapping(value = "/salePerfCompare/getSalePerfCompareStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePerfCompareStoreList(SalePerfCompareVO salePerfCompareVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePerfCompareService.getSalePerfCompareStoreList(salePerfCompareVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePerfCompareVO);
    }
}
