package kr.co.solbipos.base.prod.prodOption.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionService;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdOptionController.java
 * @Description : 기초관리 - 상품관리 - 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prodOption")
public class ProdOptionController {

    private final SessionService sessionService;
    private final ProdOptionService prodOptionService;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    MessageService messageService;

    public ProdOptionController(SessionService sessionService, ProdOptionService prodOptionService, StoreTypeService storeTypeService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodOptionService = prodOptionService;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/prodOption/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장상품제한구분 사용여부(매장에서 사용하지만 본사환경설정값으로 여부파악)
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            model.addAttribute("storeProdUseFg", "0");
        } else {
            model.addAttribute("storeProdUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"), "0"));
        }

        // 브랜드 사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/prod/prodOption/prodOption";
    }

    /**
     * 옵션그룹조회
     *
     * @param prodOptionVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodOption/getProdOptionGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionGroup(ProdOptionVO prodOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodOptionService.getProdOptionGroup(prodOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodOptionVO);
    }

    /**
     * 옵션그룹저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param prodOptionVOs ProdOptionVO[]
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.12.19
     */
    @RequestMapping(value = "/prodOption/saveProdOptionGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdOptionGroup(@RequestBody ProdOptionVO[] prodOptionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = prodOptionService.saveProdOptionGroup(prodOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 옵션속성조회
     *
     * @param prodOptionVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodOption/getProdOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdOptionVal(ProdOptionVO prodOptionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodOptionService.getProdOptionVal(prodOptionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodOptionVO);
    }

    /**
     * 옵션속성저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param prodOptionVOs ProdOptionVO[]
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.12.19
     */
    @RequestMapping(value = "/prodOption/saveProdOptionVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdOptionVal(@RequestBody ProdOptionVO[] prodOptionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = prodOptionService.saveProdOptionVal(prodOptionVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 추가(상품포함) 팝업 상품 리스트 조회
     * @param request
     * @param response
     * @param prodOptionVO
     * @param model
     * @return
     */
    @RequestMapping(value = "/prodOption/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(HttpServletRequest request, HttpServletResponse response,
                              ProdOptionVO prodOptionVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodOptionService.getProdList(prodOptionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodOptionVO);
    }
}
