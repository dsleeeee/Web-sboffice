package kr.co.solbipos.application.common.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.interceptor.AuthenticationInterceptor;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.CmAgencyVO;
import kr.co.solbipos.application.common.service.HqVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * 화면 상단에 고정 메뉴 및 히스토리 메뉴 관리 관련 컨트롤러
 *
 * @author 정용길
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    private final SessionService sessionService;
    private final CmmMenuService cmmMenuService;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public MenuController(SessionService sessionService, CmmMenuService cmmMenuService, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.sessionService = sessionService;
        this.cmmMenuService = cmmMenuService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /**
     * 메뉴목록 조회 : 세션에 저장된 메뉴목록
     *
     * @param request HttpServletRequest
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/menuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuList(HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return returnJson(Status.OK, sessionInfoVO.getMenuData());
    }

    /**
     * 즐겨찾기 메뉴목록 조회 : 세션에 저장된 즐겨찾기 메뉴목록
     *
     * @param request HttpServletRequest
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/bkmkList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBkmkList(HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return returnJson(Status.OK, sessionInfoVO.getBkmkMenuData());
    }

    /**
     * 선택메뉴 가져오기 : 세션의 현재 선택한 메뉴 정보 반환
     *
     * @param request HttpServletRequest
     * @param model Model
     * @return
     */
    @RequestMapping(value = "/currentMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCurrentMenu(HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return returnJson(Status.OK, sessionInfoVO.getCurrentMenu());
    }

    /**
     * 히스토리 메뉴 삭제
     *
     * @param menuId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/delHistMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delHistMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cmmMenuService.deleteHistMenu(menuId, sessionInfoVO);
        return returnJson(Status.OK);
    }

    /**
     * 고정 메뉴 삭제
     *
     * @param menuId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/delFixMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delFixMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cmmMenuService.deleteFixMenu(menuId, sessionInfoVO);
        return returnJson(Status.OK);
    }

    /**
      * 레이어 팝업 매장 조회
      *
      * @param storeVO
      * @param request
      * @param model
      * @return
      */
    @RequestMapping(value = "/selectStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStore(StoreVO storeVO, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사일 경우 해당 본사의 기본매장(코드)가 있는지 체크 후, 기본매장은 빼고 조회 해야 함.
        String defaultStoreCd = "";
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd =  StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd = defaultStoreCd.replace("*", "");
        }
        storeVO.setDefaultStoreCd(defaultStoreCd);
        storeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<StoreVO> list = cmmMenuService.getStoreInfo(storeVO);
        return returnJson(Status.OK, list);
    }

    /**
     * 레이어 팝업 본사 조회
     *
     * @param hqVO
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectHq.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqInfo(HqVO hqVO, HttpServletRequest request, Model model) {
        List<HqVO> list = cmmMenuService.getHqInfo(hqVO);
        return returnJson(Status.OK, list);
    }


    /**
     * 레이어 팝업 업체 조회
     *
     * @param caVO
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectCmAgency.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmAgencyInfo(CmAgencyVO caVO, HttpServletRequest request, Model model) {
        List<CmAgencyVO> list = cmmMenuService.getCmAgencyInfo(caVO);
        return returnJson(Status.OK, list);
   }

    /**
     * 메뉴이동 제한 화면 비밀번호 검증 - 조회
     *
     * @param
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 22.
     */
    @RequestMapping(value = "/loginPwdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result loginPwdChk(PwdManageVO pwdManageVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = cmmMenuService.getLoginPwdChk(pwdManageVO, sessionInfoVO);
        if(result == 1){
            // 세션에 pageAccessChkPwdYn값(비밀번호 확인) 설정
            sessionInfoVO.setPageAccessChkPwdYn("Y");
            sessionService.setSessionInfo(sessionInfoVO);
        }

        return returnJson(Status.OK, result);
    }

}


