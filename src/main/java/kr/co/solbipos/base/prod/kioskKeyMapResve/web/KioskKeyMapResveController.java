package kr.co.solbipos.base.prod.kioskKeyMapResve.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveService;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : KioskKeyMapResveController.java
 * @Description : 기초관리 > 상품관리2 > 키오스크키맵설정(예약)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.28  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/kioskKeyMapResve")
public class KioskKeyMapResveController {

    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;
    private final KioskKeyMapService kioskKeyMapService;
    private final KioskKeyMapResveService kioskKeyMapResveService;

    public KioskKeyMapResveController(SessionService sessionService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil, KioskKeyMapService kioskKeyMapService, KioskKeyMapResveService kioskKeyMapResveService) {
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
        this.kioskKeyMapService = kioskKeyMapService;
        this.kioskKeyMapResveService = kioskKeyMapResveService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/kioskKeyMapResve/view.sb", method = RequestMethod.GET)
    public String kioskKeyMapResveView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", convertToJson(kioskTuClsTypeList)  );
        String kioskTuClsTypeListAll = "";
        if (kioskTuClsTypeList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            kioskTuClsTypeListAll = convertToJson(list);
        } else {
            kioskTuClsTypeListAll = cmmCodeUtil.assmblObj(kioskTuClsTypeList, "name", "value", UseYn.ALL);
        }
        model.addAttribute("kioskTuClsTypeListAll", kioskTuClsTypeListAll);

        // 내일날짜
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, +1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(cal.getTime());
        model.addAttribute("tomorrowDate", dateStr);

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));


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

        // 사용자별 그룹 콤보박스 조회
        // 그룹
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


        return "base/prod/kioskKeyMapResve/kioskKeyMapResve";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   kioskKeyMapResveVO
     * @return  String
     * @author  권지현
     * @since   2023.03.28
     */
    @RequestMapping(value = "/kioskKeyMapResve/getKioskKeyMapResveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMapResveList(HttpServletRequest request, HttpServletResponse response, Model model, KioskKeyMapResveVO kioskKeyMapResveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = kioskKeyMapResveService.getKioskKeyMapResveList(kioskKeyMapResveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, kioskKeyMapResveVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   kioskKeyMapResveVO
     * @return  String
     * @author  권지현
     * @since   2023.03.28
     */
    @RequestMapping(value = "/kioskKeyMapResve/getKioskKeyMapResveAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMapResveAddList(HttpServletRequest request, HttpServletResponse response, Model model, KioskKeyMapResveVO kioskKeyMapResveVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = kioskKeyMapResveService.getKioskKeyMapResveAddList(kioskKeyMapResveVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, kioskKeyMapResveVO);
    }

    /**
     * 판매터치키(에약) 추가
     * @param kioskKeyMapResveVOs
     * @param request
     * @return
     * @author  권지현
     * @since   2023.03.28
     */
    @RequestMapping(value = "/kioskKeyMapResve/saveKioskKeyMapResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMapResve(@RequestBody KioskKeyMapResveVO[] kioskKeyMapResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapResveService.saveKioskKeyMapResve(kioskKeyMapResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 판매터치키(에약) 삭제
    * @param kioskKeyMapResveVOs
     * @param request
     * @return
     * @author  권지현
     * @since   2023.03.28
     */
    @RequestMapping(value = "/kioskKeyMapResve/deleteKioskKeyMapResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteKioskKeyMapResve(@RequestBody KioskKeyMapResveVO[] kioskKeyMapResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapResveService.deleteKioskKeyMapResve(kioskKeyMapResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 판매터치키(에약) 수정
    * @param kioskKeyMapResveVOs
     * @param request
     * @return
     * @author  권지현
     * @since   2023.03.28
     */
    @RequestMapping(value = "/kioskKeyMapResve/modKioskKeyMapResve.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modKioskKeyMapResve(@RequestBody KioskKeyMapResveVO[] kioskKeyMapResveVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapResveService.modKioskKeyMapResve(kioskKeyMapResveVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}