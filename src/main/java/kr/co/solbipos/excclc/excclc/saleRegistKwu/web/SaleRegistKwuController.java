package kr.co.solbipos.excclc.excclc.saleRegistKwu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.saleRegistKwu.service.SaleRegistKwuVO;
import kr.co.solbipos.excclc.excclc.saleRegistKwu.service.SaleRegistKwuService;
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
 * @Class Name : SaleRegistKwuController.java
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
@RequestMapping(value = "/excclc/excclc/saleRegistKwu/")
public class SaleRegistKwuController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKwuService saleRegistKwuService;
    private final SessionService sessionService;

    public SaleRegistKwuController(SaleRegistKwuService saleRegistKwuService, SessionService sessionService) {
        this.saleRegistKwuService = saleRegistKwuService;
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
    @RequestMapping(value = "saleRegistKwu/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/saleRegistKwu/saleRegist";
    }

    /**
     * 매출수기등록 리스트 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.24
     */
    @RequestMapping(value = "saleRegistKwu/getSaleRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleRegistKwuList(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getSaleRegistKwuList(saleRegistKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwuVO);
    }

    /**
     * 매출수기등록 상품 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistKwu/getSelectProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectProdList(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getSelectProdList(saleRegistKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwuVO);
    }

    /**
     * 매출처 팝업 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "saleRegistKwu/getMembrKwList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrKwList(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getMembrKwList(saleRegistKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwuVO);
    }

    /**
     * 매출수기등록 저장
     *
     * @param saleRegistKwuVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.06.27
     */
    @RequestMapping(value = "/saleRegistKwu/getNewRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewRegistKwList(@RequestBody SaleRegistKwuVO[] saleRegistKwuVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKwuService.getNewRegistKwList(saleRegistKwuVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKwu/getBillDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDtlList(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getBillDtlList(saleRegistKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleRegistKwuVO);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKwu/getCashAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashAmt(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getCashAmt(saleRegistKwuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.05
     */
    @RequestMapping(value = "saleRegistKwu/getHdrInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHdrInfo(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleRegistKwuService.getHdrInfo(saleRegistKwuVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 매출구분 조회
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKwu/getSaleFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleFg(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = saleRegistKwuService.getSaleFg(saleRegistKwuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 특정 전표 삭제
     * @param saleRegistKwuVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.06.29
     */
    @RequestMapping(value = "saleRegistKwu/getBillDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillDel(SaleRegistKwuVO saleRegistKwuVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleRegistKwuService.getBillDel(saleRegistKwuVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
