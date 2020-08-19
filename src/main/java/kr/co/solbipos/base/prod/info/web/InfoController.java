package kr.co.solbipos.base.prod.info.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.InfoService;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
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
 * @Class Name : InfoController.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/info")
public class InfoController {

    private final InfoService service;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public InfoController(InfoService service, SessionService sessionService,
        CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.service = service;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 분류코드 등록 화면
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018.08.03
     */
    @RequestMapping(value = "/class/prodClassView.sb", method = RequestMethod.GET)
    public String prodClassView(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품등록 본사 통제여부
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));

        model.addAttribute("prodEnvstVal", prodEnvstVal);

        return "base/prod/info/prodClassView";
    }

    /**
     * 본사 분류 조회
     * @param   hqProdClsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 03.
     */
    @RequestMapping(value = "/hq/getProdClass.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result hqClsList(ProductClassVO hqProdClsVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<ProductClassVO> list = service.getProdClsList(hqProdClsVO, sessionInfoVO);

        return returnListJson(Status.OK, list, hqProdClsVO);
    }


    /**
     * 매장 분류 조회
     * @param   storeProdClsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 06.
     */
    @RequestMapping(value = "/store/getProdClass.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storeClsList(ProductClassVO storeProdClsVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<ProductClassVO> list = service.getProdClsList(storeProdClsVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeProdClsVO);
    }


    /**
     * 분류 등록
     * @param   productClassVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/class/saveProdClass.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result clsSave(@RequestBody ProductClassVO[] productClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.productClassSave(productClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
