package kr.co.solbipos.base.price.sideMenuSalePrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.sideMenuSalePrice.service.SideMenuSalePriceService;
import kr.co.solbipos.base.price.sideMenuSalePrice.service.SideMenuSalePriceVO;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
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
 * @Class Name : SideMenuSalePriceController.java
 * @Description : 기초관리 - 가격관리 - 매장별구성상품가격변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/sideMenuSalePrice")
public class SideMenuSalePriceController {

    private final SessionService sessionService;
    private final SideMenuSalePriceService sideMenuSalePriceService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final ProdService prodService;

    /** Constructor Injection */
    @Autowired
    public SideMenuSalePriceController(SessionService sessionService, SideMenuSalePriceService sideMenuSalePriceService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, ProdService prodService) {
        this.sessionService = sessionService;
        this.sideMenuSalePriceService = sideMenuSalePriceService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.prodService = prodService;
    }

    /**
     * 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author 권지현
     * @since 2023.05.03
     */
    @RequestMapping(value = "/sideMenuSalePrice/view.sb", method = RequestMethod.GET)
    public String hqSideMenuSalePriceView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/price/sideMenuSalePrice/sideMenuSalePrice";
    }

    /**
     * 선택메뉴 리스트 조회
     * @param sideMenuSalePriceVO
     * @param request
     * @return
     * @author 권지현
     * @since 2023.05.03
     */
    @RequestMapping(value = "/sideMenuSalePrice/getSideMenuSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuSalePriceList(SideMenuSalePriceVO sideMenuSalePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = sideMenuSalePriceService.getSideMenuSalePriceList(sideMenuSalePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, sideMenuSalePriceVO);
    }

    /**
     * 가격 저장
     *
     * @param sideMenuSalePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author 권지현
     * @since 2023.05.03
     */
    @RequestMapping(value = "/sideMenuSalePrice/saveSideMenuSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSideMenuSalePrice(@RequestBody SideMenuSalePriceVO[] sideMenuSalePriceVOs, HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuSalePriceService.saveSideMenuSalePrice(sideMenuSalePriceVOs, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

}
