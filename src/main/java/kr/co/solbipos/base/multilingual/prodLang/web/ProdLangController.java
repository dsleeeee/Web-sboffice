package kr.co.solbipos.base.multilingual.prodLang.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdVO;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangService;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdLangController.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/multilingual/prodLang")
public class ProdLangController {

    private final SessionService sessionService;
    private final ProdLangService prodLangService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdLangController(SessionService sessionService, ProdLangService prodLangService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodLangService = prodLangService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(ProdLangVO prodLangVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/multilingual/prodLang/prodLangTab";
    }

    /**
     * 상품명 탭 리스트 조회
     * @param prodLangVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/getProdNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdNmList(ProdLangVO prodLangVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodLangService.getProdNmList(prodLangVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodLangVO);
    }

    /**
     * 상품명 영문, 중문, 일문 저장
     * @param prodLangVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/saveProdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdNm(@RequestBody ProdLangVO[] prodLangVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodLangService.saveProdNm(prodLangVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 상품설명 탭 리스트 조회
     * @param prodLangVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/getProdInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfoList(ProdLangVO prodLangVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodLangService.getProdInfoList(prodLangVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodLangVO);
    }

    /**
     * 상품설명 영문, 중문, 일문 저장
     * @param prodLangVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2023. 12. 28.
     */
    @RequestMapping(value = "/saveProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdInfo(@RequestBody ProdLangVO[] prodLangVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodLangService.saveProdInfo(prodLangVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
