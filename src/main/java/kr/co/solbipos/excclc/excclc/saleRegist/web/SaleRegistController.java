package kr.co.solbipos.excclc.excclc.saleRegist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.saleRegist.service.SaleRegistService;
import kr.co.solbipos.excclc.excclc.saleRegist.service.SaleRegistVO;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : SaleRegistController.java
 * @Description : 정산관리 > 정산관리 > 매출수기등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.24  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/excclc/excclc/saleRegist/")
public class SaleRegistController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistService saleRegistService;
    private final SessionService sessionService;

    @Autowired
    public SaleRegistController(SaleRegistService saleRegistService, SessionService sessionService) {
        this.saleRegistService = saleRegistService;
        this.sessionService = sessionService;
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
    @RequestMapping(value = "saleRegist/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/saleRegist/saleRegist";
    }

    /**
     * 매출수기등록 리스트 조회
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegist/getSaleRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistList(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistService.getSaleRegistList(saleRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistVO);
    }

    /**
     * 매출수기등록 상품 조회
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegist/getSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectProdList(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistService.getSelectProdList(saleRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistVO);
    }

    /**
     * 매출수기등록 저장
     *
     * @param saleRegistVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "/saleRegist/getNewRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistList(@RequestBody SaleRegistVO[] saleRegistVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistService.getNewRegistList(saleRegistVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegist/getBillDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDtlList(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistService.getBillDtlList(saleRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistVO);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegist/getCashAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashAmt(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistService.getCashAmt(saleRegistVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 매출구분 조회
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegist/getSaleFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleFg(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = saleRegistService.getSaleFg(saleRegistVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 삭제
     * @param saleRegistVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegist/getBillDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDel(SaleRegistVO saleRegistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistService.getBillDel(saleRegistVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
