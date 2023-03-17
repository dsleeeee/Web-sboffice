package kr.co.solbipos.base.store.view.web;

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
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.store.view.service.CopyStoreEnvVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.base.store.view.service.enums.CornerUseYn;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
* @Class Name : ViewController.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.13  김영근      최초생성
 * @ 2018.11.20  김지은      기능오류 수정 및 angular 변경
 * @ 2018.12.28  김지은      매장환경 복사 팝업 생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/store/view")
public class ViewController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ViewService viewService;
    private final DayProdService dayProdService;
    private final ProdService prodService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ViewController(ViewService viewService, DayProdService dayProdService, ProdService prodService, SessionService sessionService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.viewService = viewService;
        this.dayProdService = dayProdService;
        this.prodService = prodService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 매장정보조회 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 브랜드사용여부
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
            // 사용자별 브랜드 콤보박스 조회
            DayProdVO dayProdVO = new DayProdVO();
            model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));
            // 브랜드 리스트 조회(선택 콤보박스용)
            ProdVO prodVO = new ProdVO();
            model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));


            /** 맘스터치 */
            // [1250 맘스터치] 환경설정값 조회
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
            }

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

            /** //맘스터치 */
        }

        return "base/store/view/view";
    }

    /**
     * 매장정보 리스트조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(ViewVO viewVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = viewService.getViewList(viewVO, sessionInfoVO);

        return returnListJson(Status.OK, list, viewVO);
    }

    /**
     * 매장정보 리스트 엑셀 조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/getStoreListExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreListExcel(ViewVO viewVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = viewService.getStoreListExcel(viewVO, sessionInfoVO);

        return returnListJson(Status.OK, list, viewVO);
    }

    /**
     * 매장정보 상세조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/dtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result detail(ViewVO viewVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //매장 상세정보
        DefaultMap<String> storeInfo = viewService.getViewDetail(viewVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("storeInfo", storeInfo);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * VAN사 환경설정 정보 조회
     * 벤사 목록도 함께 조회 (코너, 포스)
     * @param vanConfgVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/vanConfg/vanConfigInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result vanConfigInfo(VanConfigVO vanConfgVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<DefaultMap<String>> posTerminalList = null;
        List<DefaultMap<String>> cornrTerminalList = null;

        CornerUseYn cornerUseYnVal = CornerUseYn.getEnum(viewService.getCornerUseYnVal(vanConfgVO));

        // 포스별승인 목록
        posTerminalList = viewService.getPosTerminalList(vanConfgVO);

        // 코너개별승인 목록
        cornrTerminalList = viewService.getCornerTerminalList(vanConfgVO);


        resultMap.put("cornerUseYnVal", cornerUseYnVal);
        resultMap.put("posTerminalList", posTerminalList);
        resultMap.put("cornrTerminalList", cornrTerminalList);

        return returnJson(Status.OK, resultMap);
    }


    /**
     * 매장환경 복사를 위한 정보 조회
     * @param copyStoreEnvVO
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping(value = "/copyStoreEnv/getStoreEnvInfo.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getStoreEnvInfo(CopyStoreEnvVO copyStoreEnvVO, HttpServletRequest request,
//        HttpServletResponse response, Model model) {
//
//        LOGGER.info(copyStoreEnvVO.getProperties());
//
//        // 복사할 매장환경 목록 조회
//        CommonCodeVO envVO = cmmCodeUtil.getCommCodeData("101");
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//
//        resultMap.put("envList", envVO.getCodeList());
//
//        return returnJson(Status.OK, resultMap);
//    }
    /**
     * 매장환경 복사를 위한 정보 조회
     *
     * @param copyStoreEnvVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 14.
     */
    @RequestMapping(value = "/copyStoreEnv/getStoreEnvInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreEnvInfoList(CopyStoreEnvVO copyStoreEnvVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = viewService.getStoreEnvInfoList(copyStoreEnvVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, copyStoreEnvVO);
    }

    /**
     * 매장환경 복사
     * @param copyStoreEnvVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author 김지은
     * @since 2018.12.29
     */
    @RequestMapping(value = "/copyStoreEnv/copyStoreEnvInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyStoreEnvInfo(@RequestBody CopyStoreEnvVO[] copyStoreEnvVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> posParam = new HashMap<String, Object>();
        posParam.put("originalStoreCd", request.getParameter("originalStoreCd"));
        posParam.put("targetStoreCd", request.getParameter("targetStoreCd"));

        int result = viewService.copyStoreEnv(copyStoreEnvVOs, posParam, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장 리스트 조회
     *
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2022.04.29
     */
    @RequestMapping(value = "/view/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(ViewVO viewVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = viewService.getStoreList(viewVO, sessionInfoVO);

        return returnListJson(Status.OK, list, viewVO);
    }

    /**
     * 매장 판매터치키 콤보박스 데이터 조회
     * @param viewVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/getStoreTouchKeyGrpCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTouchKeyGrpCombo(ViewVO viewVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = viewService.getStoreTouchKeyGrpCombo(viewVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, viewVO);
    }

    /**
     * 매장 판매터치키 선택그룹 복사
     * @param copyStoreEnvVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/copyStoreTouchKeyGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyStoreTouchKeyGrp(@RequestBody CopyStoreEnvVO[] copyStoreEnvVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = viewService.copyStoreTouchKeyGrp(copyStoreEnvVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
