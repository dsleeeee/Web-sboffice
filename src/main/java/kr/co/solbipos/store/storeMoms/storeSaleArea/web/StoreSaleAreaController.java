package kr.co.solbipos.store.storeMoms.storeSaleArea.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaService;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaVO;
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
 * @Class Name : StoreSaleAreaController.java
 * @Description : 맘스터치 > 매장관리 > 점포영업지역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.11.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/store/storeMoms/storeSaleArea/")
public class StoreSaleAreaController {

    private final StoreSaleAreaService storeSaleAreaService;
    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public StoreSaleAreaController(StoreSaleAreaService storeSaleAreaService, SessionService sessionService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.storeSaleAreaService = storeSaleAreaService;
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 점포영업지역관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.11.21
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.GET)
    public String view(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {


        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        // 사용자별 코드별 공통코드 콤보박스 조회
        // 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        String momsTeamComboListAll = "";
        if (momsTeamComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsTeamComboListAll = convertToJson(list);
        } else {
            momsTeamComboListAll = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsTeamComboList", momsTeamComboListAll);
        // AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        String momsAcShopComboListAll = "";
        if (momsAcShopComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsAcShopComboListAll = convertToJson(list);
        } else {
            momsAcShopComboListAll = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsAcShopComboList", momsAcShopComboListAll);
        // 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        String momsAreaFgComboListAll = "";
        if (momsAreaFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsAreaFgComboListAll = convertToJson(list);
        } else {
            momsAreaFgComboListAll = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboListAll);
        // 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        String momsCommercialComboListAll = "";
        if (momsCommercialComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsCommercialComboListAll = convertToJson(list);
        } else {
            momsCommercialComboListAll = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsCommercialComboList", momsCommercialComboListAll);
        // 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        String momsShopTypeComboListAll = "";
        if (momsShopTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsShopTypeComboListAll = convertToJson(list);
        } else {
            momsShopTypeComboListAll = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboListAll);
        // 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        String momsStoreManageTypeComboListAll = "";
        if (momsStoreManageTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreManageTypeComboListAll = convertToJson(list);
        } else {
            momsStoreManageTypeComboListAll = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboListAll);

        // 사용자별 지사 콤보박스 조회
        // 지사
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        String branchCdComboListAll = "";
        if (branchCdComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            branchCdComboListAll = convertToJson(list);
        } else {
            branchCdComboListAll = cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("branchCdComboList", branchCdComboListAll);

        // 지사 조회(콤보박스용)
        model.addAttribute("branchCombo", convertToJson(storeSaleAreaService.getBranchCombo(storeSaleAreaVO, sessionInfoVO)));

        return "store/storeMoms/storeSaleArea/storeSaleArea";
    }

    /**
     * 매장목록 조회
     * @param   storeSaleAreaVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2022.11.21
     */
    @RequestMapping(value = "getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeSaleAreaService.getStoreList(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSaleAreaVO);
    }

    /**
     * 매장 조회(콤보박스용)
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getStoreCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCombo(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> storeCombo = storeSaleAreaService.getStoreCombo(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, storeCombo);
    }

    /**
     * 매장 영업지역 조회
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getStoreSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = storeSaleAreaService.getStoreSaleArea(storeSaleAreaVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 매장 영업지역 저장
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "saveStoreSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreSaleArea(@RequestBody StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = storeSaleAreaService.saveStoreSaleArea(storeSaleAreaVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 서울, 경기 매장 영업지역 조회
     * @param storeSaleAreaVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.11.22
     */
    @RequestMapping(value = "getMetropolitanSaleArea.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMetropolitanSaleArea(StoreSaleAreaVO storeSaleAreaVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeSaleAreaService.getMetropolitanSaleArea(storeSaleAreaVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSaleAreaVO);
    }
}
