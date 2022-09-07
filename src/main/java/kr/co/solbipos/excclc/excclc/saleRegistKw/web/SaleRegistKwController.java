package kr.co.solbipos.excclc.excclc.saleRegistKw.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.saleRegistKw.service.SaleRegistKwVO;
import kr.co.solbipos.excclc.excclc.saleRegistKw.service.SaleRegistKwService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : SaleRegistKwController.java
 * @Description : 광운대 > 후방매출등록 > 후방매출등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.31  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.08.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/excclc/excclc/saleRegistKw/")
public class SaleRegistKwController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKwService saleRegistKwService;
    private final SessionService sessionService;

    public SaleRegistKwController(SaleRegistKwService saleRegistKwService, SessionService sessionService) {
        this.saleRegistKwService = saleRegistKwService;
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
    @RequestMapping(value = "saleRegistKw/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/saleRegistKw/saleRegist";
    }

    /**
     * 매출수기등록 리스트 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegistKw/getSaleRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistKwList(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getSaleRegistKwList(saleRegistKwVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwVO);
    }

    /**
     * 매출수기등록 상품 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistKw/getSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectProdList(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getSelectProdList(saleRegistKwVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwVO);
    }

    /**
     * 매출처 팝업 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistKw/getMembrKwList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrKwList(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getMembrKwList(saleRegistKwVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwVO);
    }

    /**
     * 매출수기등록 저장
     *
     * @param saleRegistKwVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "/saleRegistKw/getNewRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistKwList(@RequestBody SaleRegistKwVO[] saleRegistKwVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKwService.getNewRegistKwList(saleRegistKwVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKw/getBillDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDtlList(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getBillDtlList(saleRegistKwVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwVO);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKw/getCashAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashAmt(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getCashAmt(saleRegistKwVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.05
     */
    @RequestMapping(value = "saleRegistKw/getHdrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHdrInfo(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwService.getHdrInfo(saleRegistKwVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 매출구분 조회
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKw/getSaleFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleFg(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = saleRegistKwService.getSaleFg(saleRegistKwVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 삭제
     * @param saleRegistKwVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKw/getBillDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDel(SaleRegistKwVO saleRegistKwVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKwService.getBillDel(saleRegistKwVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
