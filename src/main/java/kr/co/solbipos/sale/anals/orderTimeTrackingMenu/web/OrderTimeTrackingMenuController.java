package kr.co.solbipos.sale.anals.orderTimeTrackingMenu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.orderTimeTracking.service.OrderTimeTrackingVO;
import kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.OrderTimeTrackingMenuService;
import kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.OrderTimeTrackingMenuVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : OrderTimeTrackingMenuController.java
 * @Description : 매출관리 - 매출분석2 - 주문시간트레킹-메뉴별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.18  김유승       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sale/anals/orderTimeTrackingMenu")
public class OrderTimeTrackingMenuController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final OrderTimeTrackingMenuService orderTimeTrackingMenuService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public OrderTimeTrackingMenuController(SessionService sessionService, OrderTimeTrackingMenuService orderTimeTrackingMenuService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.orderTimeTrackingMenuService = orderTimeTrackingMenuService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  김유승
     * @since   2024.06.18
     */
    @RequestMapping(value = "/orderTimeTrackingMenu/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("momsHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 사용자별 그룹 콤보박스 조회
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

        // 사용자별 코드별 공통코드 콤보박스 조회
        // - 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        model.addAttribute("momsTeamComboList", momsTeamComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N));

        // - AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        model.addAttribute("momsAcShopComboList", momsAcShopComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N));

        // - 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N));

        // - 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        model.addAttribute("momsCommercialComboList", momsCommercialComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N));

        // - 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N));

        // - 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N));

        // - 매장그룹
        List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
        model.addAttribute("momsStoreFg01ComboList", momsStoreFg01ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg01ComboList, "name", "value", UseYn.N));

        // - 매장그룹2
        List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
        model.addAttribute("momsStoreFg02ComboList", momsStoreFg02ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg02ComboList, "name", "value", UseYn.N));

        // - 매장그룹3
        List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
        model.addAttribute("momsStoreFg03ComboList", momsStoreFg03ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg03ComboList, "name", "value", UseYn.N));

        // - 매장그룹4
        List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
        model.addAttribute("momsStoreFg04ComboList", momsStoreFg04ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg04ComboList, "name", "value", UseYn.N));

        // - 매장그룹5
        List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
        model.addAttribute("momsStoreFg05ComboList", momsStoreFg05ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg05ComboList, "name", "value", UseYn.N));


        return "sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu";
    }

    /**
     * 주문시간트레킹 리스트 조회
     * @param orderTimeTrackingMenuVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024.06.18
     */
    @RequestMapping(value = "/orderTimeTrackingMenu/getOrderTimeTrackingMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderTimeTrackingMenuList(OrderTimeTrackingMenuVO orderTimeTrackingMenuVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = orderTimeTrackingMenuService.getOrderTimeTrackingMenuList(orderTimeTrackingMenuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, orderTimeTrackingMenuVO);
    }

    /**
     * 주문시간트레킹 엑셀다운로드 조회
     * @param orderTimeTrackingMenuVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024.06.18
     */
    @RequestMapping(value = "/orderTimeTrackingMenu/getOrderTimeTrackingMenuExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderTimeTrackingMenuExcelList(OrderTimeTrackingMenuVO orderTimeTrackingMenuVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = orderTimeTrackingMenuService.getOrderTimeTrackingMenuExcelList(orderTimeTrackingMenuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, orderTimeTrackingMenuVO);
    }
}
