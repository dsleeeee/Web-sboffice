package kr.co.solbipos.excclc.excclc.saleRegistKmu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.saleRegistKmu.service.SaleRegistKmuService;
import kr.co.solbipos.excclc.excclc.saleRegistKmu.service.SaleRegistKmuVO;
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
 * @Class Name : SaleRegistKmuController.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(일반)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/excclc/excclc/saleRegistKmu/")
public class SaleRegistKmuController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKmuService saleRegistKmuService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public SaleRegistKmuController(SaleRegistKmuService saleRegistKmuService, SessionService sessionService, CmmCodeUtil cmmCodeUtil) {
        this.saleRegistKmuService = saleRegistKmuService;
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
    @RequestMapping(value = "saleRegistKmu/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/saleRegistKmu/saleRegist";
    }

    /**
     * 매출수기등록 리스트 조회
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegistKmu/getSaleRegistKmuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistKmuList(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKmuService.getSaleRegistKmuList(saleRegistKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKmuVO);
    }

    /**
     * 매출수기등록 상품 조회
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistKmu/getSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectProdList(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKmuService.getSelectProdList(saleRegistKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKmuVO);
    }

    /**
     * 매출수기등록 저장
     *
     * @param saleRegistKmuVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "/saleRegistKmu/getNewRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistList(@RequestBody SaleRegistKmuVO[] saleRegistKmuVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKmuService.getNewRegistList(saleRegistKmuVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKmu/getBillDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDtlList(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKmuService.getBillDtlList(saleRegistKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKmuVO);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKmu/getCashAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashAmt(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKmuService.getCashAmt(saleRegistKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 매출구분 조회
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKmu/getSaleFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleFg(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = saleRegistKmuService.getSaleFg(saleRegistKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 삭제
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKmu/getBillDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDel(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKmuService.getBillDel(saleRegistKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 회원선택 팝업 - 조회
     *
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 09. 25.
     */
    @RequestMapping(value = "/saleRegistKmu/getSaleRegistKmuMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistKmuMemberList(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleRegistKmuService.getSaleRegistKmuMemberList(saleRegistKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKmuVO);
    }

    /**
     * 매출전표등록(일반) - 삭제
     *
     * @param saleRegistKmuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 10. 01.
     */
    @RequestMapping(value = "saleRegistKmu/getNewRegistDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistDel(SaleRegistKmuVO saleRegistKmuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKmuService.getNewRegistDel(saleRegistKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

}