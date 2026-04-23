package kr.co.solbipos.sys.admin2.authorInExRegist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin2.authorInExRegist.service.AuthorInExRegistService;
import kr.co.solbipos.sys.admin2.authorInExRegist.service.AuthorInExRegistVO;
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

/**
 * @Class Name  : AuthorInExRegistController.java
 * @Description : 시스템관리 > 관리자기능2 > 메뉴권한임의등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.04.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin2/authorInExRegist")
public class AuthorInExRegistController {

    private final SessionService sessionService;
    private final AuthorInExRegistService authorInExRegistService;

    /**
     *  Constructor Injection
     */
    @Autowired
    public AuthorInExRegistController(SessionService sessionService, AuthorInExRegistService authorInExRegistService) {
        this.sessionService = sessionService;
        this.authorInExRegistService = authorInExRegistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/authorInExRegist/view.sb", method = RequestMethod.GET)
    public String saleTotalAnalysisByTime(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin2/authorInExRegist/authorInExRegistTab";
    }

    /**
     * 사용자기준 탭 - 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 17.
     */
    @RequestMapping(value = "/userInExRegist/getSearchUserInfoDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchUserInfoDtlList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchUserInfoDtlList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 사용자기준 탭 - 등록메뉴 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 17.
     */
    @RequestMapping(value = "/userInExRegist/getSearchUserRegMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchUserRegMenuList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchUserRegMenuList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 사용자기준 탭 - 등록메뉴 삭제
     *
     * @param   authorInExRegistVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 17.
     */
    @RequestMapping(value = "/userInExRegist/deleteUserMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserMenu(@RequestBody AuthorInExRegistVO[] authorInExRegistVOS, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = authorInExRegistService.deleteUserMenu(authorInExRegistVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사용자기준 탭 - 미등록메뉴 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 20.
     */
    @RequestMapping(value = "/userInExRegist/getSearchUserNoRegMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchUserNoRegMenuList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchUserNoRegMenuList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 사용자기준 탭 - 메뉴 등록
     *
     * @param   authorInExRegistVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 17.
     */
    @RequestMapping(value = "/userInExRegist/insertUserMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserMenu(@RequestBody AuthorInExRegistVO[] authorInExRegistVOS, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = authorInExRegistService.insertUserMenu(authorInExRegistVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 소속기준 탭 - 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 20.
     */
    @RequestMapping(value = "/orgnInExRegist/getSearchOrgnInfoDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchOrgnInfoDtlList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchOrgnInfoDtlList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 소속기준 탭 - 등록메뉴 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 21.
     */
    @RequestMapping(value = "/orgnInExRegist/getSearchOrgnRegMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchOrgnRegMenuList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchOrgnRegMenuList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 소속기준 탭 - 미등록메뉴 조회
     *
     * @param   authorInExRegistVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 21.
     */
    @RequestMapping(value = "/orgnInExRegist/getSearchOrgnNoRegMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchOrgnNoRegMenuList(AuthorInExRegistVO authorInExRegistVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = authorInExRegistService.getSearchOrgnNoRegMenuList(sessionInfoVO, authorInExRegistVO);

        return returnListJson(Status.OK, list, authorInExRegistVO);
    }

    /**
     * 소속기준 탭 - 메뉴 등록
     *
     * @param   authorInExRegistVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 21.
     */
    @RequestMapping(value = "/orgnInExRegist/insertOrgnMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertOrgnMenu(@RequestBody AuthorInExRegistVO[] authorInExRegistVOS, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = authorInExRegistService.insertOrgnMenu(authorInExRegistVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 소속기준 탭 - 등록메뉴 삭제
     *
     * @param   authorInExRegistVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 04. 21.
     */
    @RequestMapping(value = "/orgnInExRegist/deleteOrgnMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteOrgnMenu(@RequestBody AuthorInExRegistVO[] authorInExRegistVOS, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = authorInExRegistService.deleteOrgnMenu(authorInExRegistVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
