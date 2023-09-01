package kr.co.solbipos.base.prod.touchkey.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : TouchKeyController.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.19  노현수      메소드정리/분리
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/touchKey/touchKey")
public class TouchKeyController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final TouchKeyService touchkeyService;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public TouchKeyController(SessionService sessionService,
                              TouchKeyService touchkeyService, StoreTypeService storeTypeService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.touchkeyService = touchkeyService;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 판매 터치키 화면 오픈
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String touchKeyView(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setPageNo(1);

        TouchKeyStyleVO touchKeyStyleVO = new TouchKeyStyleVO();
        touchKeyStyleVO.setStyleCd("");

        // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
        } else {
            model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

            // 본사통제구분-판매가
            model.addAttribute("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));
            model.addAttribute("coercionFg", "0");
        }

        // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
        String envstCd = "0017";
        String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
        model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);

        // 터치키본사통제시매장수정허용
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("touchKeyEnvstVal2", "0");
        } else {
            model.addAttribute("touchKeyEnvstVal2", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1248"), "0"));
        }

        // 터치키 그룹 가져오기
        List<DefaultMap<String>> touchKeyGrpList = touchkeyService.getTouchKeyGrp(params, sessionInfoVO);
        model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 터치메뉴 폰트크기 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("fontSizeEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1236"), "15"));
        } else {
            model.addAttribute("fontSizeEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1236"), "15"));
        }

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

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

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        /** //맘스터치 */

        return "base/prod/touchKey/touchKey";
    }

    /**
     * 터치키 초기화
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/deleteTouchKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTouchKey(TouchKeyVO touchKeyVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        touchkeyService.deleteTouchKey(touchKeyVO, sessionInfoVO);
    return returnJson(Status.OK);
    }

    /**
     * 터치키 분류 페이지별 스타일 코드 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/getTouchKeyStyleCd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyStyleCd(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return returnJson(Status.OK, convertToJson(touchkeyService.getTouchKeyPageStyleCd(sessionInfoVO, request.getParameter("tukeyGrpCd"))));
    }

    /**
     * 터치키 스타일 코드 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/getTouchKeyStyleCdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyStyleCdList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = touchkeyService.getTouchKeyStyleCdList();

        return returnJson(Status.OK, convertToJson(list));
    }

    /**
     * 터치키 스타일 목록 조회
     *
     * @param touchKeyStyleVO TouchKeyStyleVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/getTouchKeyStyleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyStyles(TouchKeyStyleVO touchKeyStyleVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = touchkeyService.getTouchKeyStyleList(touchKeyStyleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyStyleVO);
    }

    /**
     * 판매 터치키 목록 조회 (상품정보 목록)
     *
     * @param touchKeyVO TouchKeyVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductListForTouchKey(TouchKeyVO touchKeyVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return returnListJson(Status.OK, touchkeyService.getProductListForTouchKey(touchKeyVO, sessionInfoVO));
    }

    /**
     * 판매 터치키 기존 설정 조회
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/touchKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyXml(HttpServletRequest request, HttpSession session, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String xml = touchkeyService.getTouchKeyXml(sessionInfoVO, request.getParameter("tukeyGrpCd"), request.getParameter("gubun"));
        return new Result(Status.OK, xml);
    }

    /**
     * 판매 터치키 저장
     *
     * @param request HttpServletRequest
     * @param session HttpSession
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKey(HttpServletRequest request, HttpSession session, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Result result = new Result(Status.FAIL);
        String xml = CmmUtil.decoder(request.getParameter("xml"));
        xml.replace("\n", "&#xa;");

        result = touchkeyService.saveTouchkey(sessionInfoVO, XssPreventer.unescape(xml), request.getParameter("tukeyGrpCd"), request.getParameter("tukeyGrpNm"));

        return result;
    }

    /**
     * 터치키미적용상품
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 12. 28.
     */
    @RequestMapping(value = "/noTouchKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNoTouchKey(HttpServletRequest request, HttpServletResponse response,
                               TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 매장 목록 조회
        list = touchkeyService.getNoTouchKey(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 매장수정허용분류_조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.10.20
     */
    @RequestMapping(value = "/getStoreModGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreModGrpList(HttpServletRequest request, HttpServletResponse response,
                               TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 매장 목록 조회
        list = touchkeyService.getStoreModGrpList(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 매장수정허용분류_저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVOs TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.10.20
     */
    @RequestMapping(value = "/saveStoreModGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreModGrp(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody TouchKeyVO[] touchKeyVOs, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = touchkeyService.saveStoreModGrp(touchKeyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }


    /**
     * 터치키그룹 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.11.07
     */
    @RequestMapping(value = "/getGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGrpList(HttpServletRequest request, HttpServletResponse response,
                                     TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 매장 목록 조회
        list = touchkeyService.getGrpList(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 터치키그룹 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVOs TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.10.20
     */
    @RequestMapping(value = "/saveGrpNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveGrpNm(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody TouchKeyVO[] touchKeyVOs, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = touchkeyService.saveGrpNm(touchKeyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 분류 삭제 전 매장수정허용분류 체크
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/getDeleteClassChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteClassChk(HttpServletRequest request, HttpServletResponse response,
            TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(touchkeyService.getDeleteClassChk(touchKeyVO, sessionInfoVO) == 0){
            System.out.println("성공");
            return returnJson(Status.OK);
        }
        System.out.println("실패");
        return returnJson(Status.FAIL);

    }

    /**
     * 판매 터치키 매장적용 - 매장조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 12. 28.
     */
    @RequestMapping(value = "/storeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(HttpServletRequest request, HttpServletResponse response,
        TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 매장 목록 조회
        list = touchkeyService.getStoreList(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 판매 터치키 매장적용 - 매장적용
     *
     * @param touchKeyVOs TouchKeyVO[]
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 12. 28.
     */
    @RequestMapping(value = "/applyStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKeyToStore(@RequestBody TouchKeyVO[] touchKeyVOs, HttpServletRequest request, HttpServletResponse response,
        Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = touchkeyService.saveTouchKeyToStore(touchKeyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 터치키 그룹 가져오기(터치기 수정/저장 후 사용)
     *
     * @param touchKeyVO TouchKeyVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model   Model
     * @return
     */
    @RequestMapping(value = "/getTouchKeyGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyGrp(TouchKeyVO touchKeyVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return returnListJson(Status.OK, touchkeyService.getTouchKeyGrp(params, sessionInfoVO));
    }

    /**
     * 터치키그룹 복사
     *
     * @param touchKeyVO TouchKeyVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 이다솜
     * @since 2020. 04. 29.
     */
    @RequestMapping(value = "/copyTouchKeyGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyTouchKeyGrp(@RequestBody TouchKeyVO touchKeyVO, HttpServletRequest request, HttpServletResponse response,
                                      Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        Result result = new Result(Status.FAIL);

        result = touchkeyService.copyTouchKeyGrp(touchKeyVO, sessionInfoVO);

        return result;
    }

    /**
     * 판매 터치키 포스 조회 ENVST4038
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.03.08
     */
    @RequestMapping(value = "/getTouchKeyEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyEnv(HttpServletRequest request, HttpServletResponse response,
                               TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list =  touchkeyService.getTouchKeyEnv(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 판매 터치키 포스에 적용 ENVST4038
     *
     * @param touchKeyVOs TouchKeyVO[]
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.03.08
     */
    @RequestMapping(value = "/saveTouchKeyEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTouchKeyEnv(@RequestBody TouchKeyVO[] touchKeyVOs, HttpServletRequest request, HttpServletResponse response,
                                      Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = touchkeyService.saveTouchKeyEnv(touchKeyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 터치키삭제 팝업 - 조회
     *
     * @param touchKeyVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 11.
     */
    @RequestMapping(value = "/getPopUpTouchKeyDelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPopUpTouchKeyDelList(TouchKeyVO touchKeyVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = touchkeyService.getPopUpTouchKeyDelList(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, touchKeyVO);
    }

    /**
     * 터치키삭제 팝업 - 저장
     *
     * @param touchKeyVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 07. 11.
     */
    @RequestMapping(value = "/getPopUpTouchKeyDelSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPopUpTouchKeyDelSave(@RequestBody TouchKeyVO[] touchKeyVOs, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = touchkeyService.getPopUpTouchKeyDelSave(touchKeyVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 판매 터치키 본사판매가관리
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.08.24
     */
    @RequestMapping(value = "/getHqSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePrice(HttpServletRequest request, HttpServletResponse response,
                                 TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list =  touchkeyService.getHqSalePrice(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 판매 터치키 매장판매가관리
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.08.25
     */
    @RequestMapping(value = "/getStoreSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePrice(HttpServletRequest request, HttpServletResponse response,
                                 TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list =  touchkeyService.getStoreSalePrice(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }

    /**
     * 판매 터치키 판매가관리
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param touchKeyVO TouchKeyVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.08.29
     */
    @RequestMapping(value = "/getSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePrice(HttpServletRequest request, HttpServletResponse response,
                                 TouchKeyVO touchKeyVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list =  touchkeyService.getSalePrice(touchKeyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, touchKeyVO);

    }
}
