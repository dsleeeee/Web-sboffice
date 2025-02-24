package kr.co.solbipos.store.hq.hqmanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageService;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : HqManageController.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/store/hq/hqManage/")
public class HqManageController {

    private final HqManageService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public HqManageController(HqManageService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 본사정보관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqManage/list.sb", method = RequestMethod.GET)
    public String view(HqManageVO hqManage, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 관리업체 코드조회
        List<DefaultMap<String>> agencyList = service.getAgencyCd(hqManage, sessionInfoVO);

        model.addAttribute("agencyList", convertToJson(agencyList));

        return "store/hq/hqManage/hqManage";
    }

    //TB_MS_TABLE_ATTR
    //    TB_MS_TABLE_GROUP

    /**
     * 본사정보관리 리스트 조회
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "hqManage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.list(hqManage, sessionInfoVO);

        return returnListJson(Status.OK, list, hqManage);
    }

    /**
     * 본사정보 상세조회
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/dtlInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dtlInfo(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = service.dtlInfo(hqManage);

        return returnJson(Status.OK, result);
    }

    /**
     * 사업자번호 중복체크
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/chkBizNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkBizNo(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        int cnt = service.chkBizNo(hqManage);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사업자번호 사용현황 조회
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/bizUseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bizUseList(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.getBizUseList(hqManage);

        return returnListJson(Status.OK, list);
    }

    /**
     * 사업자번호 사용현황 상세
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/bizInfoDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bizInfoDtl(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        DefaultMap<String> result = service.getBizInfoDtl(hqManage);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 신규등록
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regist(@RequestBody HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.regist(hqManage, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 본사 수정
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "master/modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result modify(@RequestBody HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.modify(hqManage, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 메뉴권한 데이터 조회config/getConfiglist.sb
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/getAuthHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result authHqList(HqManageVO hqManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 복사할 본사 조회
        List<DefaultMap<String>> authHqList = service.authHqList(hqManage, sessionInfoVO);

        // 사용가능한 메뉴 조회
        List<DefaultMap<String>> avlblMenu = service.avlblMenu(hqManage);

        // 사용중인 메뉴 조회
        List<DefaultMap<String>> beUseMenu = service.beUseMenu(hqManage);

        resultMap.put("authHqList", authHqList);
        resultMap.put("avlblMenu", avlblMenu);    // 사용메뉴
        resultMap.put("beUseMenu", beUseMenu);    // 미사용메뉴

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 메뉴권한복사
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/copyAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyAuth(@RequestBody HqMenuVO hqMenu, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.copyAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 추가
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/addAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addAuth(@RequestBody HqMenuVO[] hqMenu, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.addAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 사용메뉴 삭제
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "authorExcept/removeAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeAuth(@RequestBody HqMenuVO[] hqMenu, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.removeAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }


    /**
     * 환경설정 조회
     * @param   hqManageVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/getConfiglist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result configList(HqManageVO hqManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = service.getConfigList(hqManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, hqManageVO);
    }

    /**
     * 환경설정 저장
     * @param   hqEnvsts
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "config/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveConfig(@RequestBody HqEnvstVO[] hqEnvsts, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveConfig(hqEnvsts, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 모바일 메뉴권한 조회
     * @param   hqManage
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 11. 19.
     */
    @RequestMapping(value = "authorExcept/getAuthMobileHqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result authMobileHqList(HqManageVO hqManage, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 복사할 본사 조회
        List<DefaultMap<String>> authMobileHqList = service.authHqList(hqManage, sessionInfoVO);

        // 사용가능한 메뉴 조회
        List<DefaultMap<String>> avlblMobileMenu = service.avlblMobileMenu(hqManage);

        // 사용중인 메뉴 조회
        List<DefaultMap<String>> beUseMobileMenu = service.beUseMobileMenu(hqManage);

        resultMap.put("authMobileHqList", authMobileHqList);
        resultMap.put("avlblMobileMenu", avlblMobileMenu);    // 사용메뉴
        resultMap.put("beUseMobileMenu", beUseMobileMenu);    // 미사용메뉴

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 모바일 사용메뉴 추가
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 11. 19.
     */
    @RequestMapping(value = "authorExcept/addMobileAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addMobileAuth(@RequestBody HqMenuVO[] hqMenu, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.addMobileAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 모바일 사용메뉴 삭제
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 11. 19.
     */
    @RequestMapping(value = "authorExcept/removeMobileAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result removeMobileAuth(@RequestBody HqMenuVO[] hqMenu, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.removeMobileAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

    /**
     * 모바일 메뉴권한복사
     * @param   hqMenu
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2024. 11. 19.
     */
    @RequestMapping(value = "authorExcept/copyMobileAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyMobileAuth(@RequestBody HqMenuVO hqMenu, HttpServletRequest request,
                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = service.copyMobileAuth(hqMenu, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

}
