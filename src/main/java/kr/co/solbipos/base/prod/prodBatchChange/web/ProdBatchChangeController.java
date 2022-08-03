package kr.co.solbipos.base.prod.prodBatchChange.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeService;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
/**
 * @Class Name : ProdBatchChangeController.java
 * @Description : 기초관리 > 상품관리 > 상품정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 20201.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/prodBatchChange")
public class ProdBatchChangeController {

    private final SessionService sessionService;
    private final ProdBatchChangeService prodBatchChangeService;
    private final StoreTypeService storeTypeService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdBatchChangeController(SessionService sessionService, ProdBatchChangeService prodBatchChangeService, StoreTypeService storeTypeService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodBatchChangeService = prodBatchChangeService;
        this.storeTypeService = storeTypeService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodBatchChange/list.sb", method = RequestMethod.GET)
    public String prodBatchChangeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "base/prod/prodBatchChange/prodBatchChangeTab";
    }

    /**
     * 상품정보일괄변경 조회
     *
     * @param prodBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 28.
     */
    @RequestMapping(value = "/prodBatchChange/getProdBatchChangeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdBatchChangeList(ProdBatchChangeVO prodBatchChangeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodBatchChangeService.getProdBatchChangeList(prodBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodBatchChangeVO);
    }

    /**
     * 상품정보일괄변경 저장(판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분)
     *
     * @param prodBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 28.
     */
    @RequestMapping(value = "/prodBatchChange/getProdBatchChangeSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdBatchChangeSave(@RequestBody ProdBatchChangeVO[] prodBatchChangeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBatchChangeService.getProdBatchChangeSave(prodBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품정보일괄변경 저장(브랜드, 상품분류)
     *
     * @param prodBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 12. 17.
     */
    @RequestMapping(value = "/prodBatchChange/getProdBatchChange2Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdBatchChange2Save(@RequestBody ProdBatchChangeVO[] prodBatchChangeVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodBatchChangeService.getProdBatchChange2Save(prodBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}