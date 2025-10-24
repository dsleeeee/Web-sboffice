package kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.SaleRegistChargeKmuService;
import kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.SaleRegistChargeKmuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SaleRegistChargeKmuController.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(수수료)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/excclc/excclc/saleRegistChargeKmu/")
public class SaleRegistChargeKmuController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistChargeKmuService saleRegistChargeKmuService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public SaleRegistChargeKmuController(SaleRegistChargeKmuService saleRegistChargeKmuService, SessionService sessionService, CmmCodeUtil cmmCodeUtil) {
        this.saleRegistChargeKmuService = saleRegistChargeKmuService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 매출수기등록 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegistChargeKmu/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/saleRegistChargeKmu/saleRegist";
    }

    /**
     * 매출수기등록 리스트 조회
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegistChargeKmu/getSaleRegistChargeKmuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistChargeKmuList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistChargeKmuService.getSaleRegistChargeKmuList(saleRegistChargeKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistChargeKmuVO);
    }

    /**
     * 매출수기등록 상품 조회
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistChargeKmu/getSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectProdList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistChargeKmuService.getSelectProdList(saleRegistChargeKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistChargeKmuVO);
    }

    /**
     * 매출수기등록 저장
     *
     * @param saleRegistChargeKmuVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "/saleRegistChargeKmu/getNewRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistList(@RequestBody SaleRegistChargeKmuVO[] saleRegistChargeKmuVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistChargeKmuService.getNewRegistList(saleRegistChargeKmuVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistChargeKmu/getBillDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDtlList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistChargeKmuService.getBillDtlList(saleRegistChargeKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistChargeKmuVO);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistChargeKmu/getCashAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashAmt(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistChargeKmuService.getCashAmt(saleRegistChargeKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 매출구분 조회
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistChargeKmu/getSaleFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleFg(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = saleRegistChargeKmuService.getSaleFg(saleRegistChargeKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 삭제
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistChargeKmu/getBillDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDel(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistChargeKmuService.getBillDel(saleRegistChargeKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 회원선택 팝업 - 조회
     *
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 09. 25.
     */
    @RequestMapping(value = "/saleRegistChargeKmu/getSaleRegistChargeKmuMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistChargeKmuMemberList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleRegistChargeKmuService.getSaleRegistChargeKmuMemberList(saleRegistChargeKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistChargeKmuVO);
    }

    /**
     * 매출처선택 팝업 - 조회
     *
     * @param saleRegistChargeKmuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 10. 22.
     */
    @RequestMapping(value = "/saleRegistChargeKmu/getSaleRegistChargeKmuCustomerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistChargeKmuCustomerList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleRegistChargeKmuService.getSaleRegistChargeKmuCustomerList(saleRegistChargeKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistChargeKmuVO);
    }

}