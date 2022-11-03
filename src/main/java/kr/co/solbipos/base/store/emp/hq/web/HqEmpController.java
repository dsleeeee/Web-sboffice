package kr.co.solbipos.base.store.emp.hq.web;

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
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpMenuVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : HqEmpController.java
 * @Description : 기초관리 > 사원관리 > 사원정보관리(이전 : 기초관리 > 매장관리 > 본사사원정보관리)
 * @Modification Information
 * @
 * @  수정일      수정자      수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 수정
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/emp/hq")
public class HqEmpController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final HqEmpService hqEmpService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public HqEmpController(SessionService sessionService, HqEmpService hqEmpService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.hqEmpService = hqEmpService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 본사사원 리스트 화면
     * @param model
     * @return the string
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HqEmpVO hqEmpVO = new HqEmpVO();

        // 브랜드사용여부
        String userHqBrandYn = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"));
        model.addAttribute("userHqBrandYn", userHqBrandYn);

        // 본사 거래처 콤보박스
        model.addAttribute("vendrList", convertToJson(hqEmpService.getHqVendrCombo(hqEmpVO, sessionInfoVO)));

        // 지사 콤보박스
        model.addAttribute("branchList", convertToJson(hqEmpService.getHqBranchCombo(hqEmpVO, sessionInfoVO)));

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

        // 코드별 본사 공통코드 콤보박스 조회
        // 추가정보-팀별
        List momsTeamComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "151");
        String momsTeamComboListAll = "";
        if (momsTeamComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsTeamComboListAll = convertToJson(list);
        } else {
            momsTeamComboListAll = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsTeamComboList", momsTeamComboListAll);
        // 추가정보-AC점포별
        List momsAcShopComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "152");
        String momsAcShopComboListAll = "";
        if (momsAcShopComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsAcShopComboListAll = convertToJson(list);
        } else {
            momsAcShopComboListAll = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsAcShopComboList", momsAcShopComboListAll);
        // 추가정보-지역구분
        List momsAreaFgComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "153");
        String momsAreaFgComboListAll = "";
        if (momsAreaFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsAreaFgComboListAll = convertToJson(list);
        } else {
            momsAreaFgComboListAll = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboListAll);
        // 추가정보-상권
        List momsCommercialComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "154");
        String momsCommercialComboListAll = "";
        if (momsCommercialComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsCommercialComboListAll = convertToJson(list);
        } else {
            momsCommercialComboListAll = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsCommercialComboList", momsCommercialComboListAll);
        // 추가정보-점포유형
        List momsShopTypeComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "155");
        String momsShopTypeComboListAll = "";
        if (momsShopTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsShopTypeComboListAll = convertToJson(list);
        } else {
            momsShopTypeComboListAll = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboListAll);
        // 추가정보-매장관리타입
        List momsStoreManageTypeComboList = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "156");
        String momsStoreManageTypeComboListAll = "";
        if (momsStoreManageTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "선택");
            m.put("value", "");
            list.add(m);
            momsStoreManageTypeComboListAll = convertToJson(list);
        } else {
            momsStoreManageTypeComboListAll = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.SELECT);
        }
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboListAll);
        /** //맘스터치 */

        return "base/store/emp/hqEmp";
    }


    /**
     * 본사 사원 목록 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result view(HttpServletRequest request, HqEmpVO hqEmpVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = hqEmpService.getHqEmpList(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, list,hqEmpVO);
    }

    /**
     * 본사사원정보 상세 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    public Result getDtlInfo(HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = hqEmpService.getHqEmpDtlInfo(hqEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사사원정보 등록
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    public Result regist(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        EmpResult empResult = hqEmpService.insertHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 본사사원정보 웹 사용자 ID 조회 (중복체크)
     * @param hqEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkHqUserId.sb", method = RequestMethod.POST)
    public Result chkHqUserId(HqEmpVO hqEmpVO) {

        EmpResult empResult= hqEmpService.getHqUserIdCnt(hqEmpVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 본사사원정보 수정
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    public Result save(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = hqEmpService.saveHqEmpInfo(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 비밀번호 변경
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword.sb", method = RequestMethod.POST)
    public Result modifyPassword(@RequestBody HqEmpVO hqEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = hqEmpService.modifyPassword(hqEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 권한복사를 위한 본사 사원 리스트 조회
     * @param hqEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/authHqEmpList.sb", method = RequestMethod.POST)
    public Result authHqEmpList(HqEmpVO hqEmpVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 메뉴권한 복사할 본사 사원 목록 조회
        List<DefaultMap<String>> authHqEmpList = hqEmpService.authHqEmpList(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, authHqEmpList);
    }

    /**
     * 사용메뉴 조회
     * @param hqEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.01
     */
    @RequestMapping(value = "/avlblMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result avlblMenu(HqEmpVO hqEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 사용메뉴 조회
        List<DefaultMap<String>> avlblMenu = hqEmpService.avlblMenu(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, avlblMenu);
    }

    /**
     * 미사용메뉴 조회
     * @param hqEmpVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2020.06.01
     */
    @RequestMapping(value = "/beUseMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result beUseMenu(HqEmpVO hqEmpVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 미사용메뉴 조회
        List<DefaultMap<String>> beUseMenu = hqEmpService.beUseMenu(hqEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, beUseMenu);
    }

    /**
     * 메뉴권한복사
     * @param   hqEmpMenuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody HqEmpMenuVO hqEmpMenuVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.copyAuth(hqEmpMenuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   hqEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody HqEmpMenuVO[] hqEmpMenus, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.addAuth(hqEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   hqEmpMenus
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020.06.01
     */
    @RequestMapping(value = "/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody HqEmpMenuVO[] hqEmpMenus, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = hqEmpService.removeAuth(hqEmpMenus, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 미적용 관리브랜드 조회 팝업 - 조회
     *
     * @param hqEmpVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 11. 22.
     */
    @RequestMapping(value = "/getSearchNoUserHqBrandList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchNoUserHqBrandList(HqEmpVO hqEmpVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqEmpService.getSearchNoUserHqBrandList(hqEmpVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, hqEmpVO);
    }
}
