package kr.co.solbipos.base.store.multistoreGroup.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.multistoreGroup.service.MultistoreGroupService;
import kr.co.solbipos.base.store.multistoreGroup.service.MultistoreGroupVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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

/**
 * @Class Name : MultistoreGroupController.java
 * @Description : 기초관리 - 매장관리 - 매장타입관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.07.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/multistoreGroup")
public class MultistoreGroupController {

    private final SessionService sessionService;
    private final MultistoreGroupService multistoreGroupService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MultistoreGroupController(SessionService sessionService, MultistoreGroupService multistoreGroupService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.multistoreGroupService = multistoreGroupService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 07. 28.
     */
    @RequestMapping(value = "/multistoreGroup/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/store/multistoreGroup/multistoreGroup";
    }

    /**
     * 그룹조회
     * @param multistoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 30.
     */
    @RequestMapping(value = "/multistoreGroup/getMultistoreGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMultistoreGroup(MultistoreGroupVO multistoreGroupVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multistoreGroupService.getMultistoreGroup(multistoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 그룹저장
     * @param multistoreGroupVOS
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 30.
     */
    @RequestMapping(value = "/multistoreGroup/saveMultistoreGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMultistoreGroup(@RequestBody MultistoreGroupVO[] multistoreGroupVOS, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = multistoreGroupService.saveMultistoreGroup(multistoreGroupVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 등록매장조회
     * @param multistoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 02.
     */
    @RequestMapping(value = "/multistoreGroup/getMultiStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMultiStoreList(MultistoreGroupVO multistoreGroupVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multistoreGroupService.getMultiStoreList(multistoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장조회
     * @param multistoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 02.
     */
    @RequestMapping(value = "/multistoreGroup/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(MultistoreGroupVO multistoreGroupVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multistoreGroupService.getStoreList(multistoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장등록, 삭제
     * @param multistoreGroupVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 02.
     */
    @RequestMapping(value = "/multistoreGroup/saveStoreMapping.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreMapping(@RequestBody MultistoreGroupVO[] multistoreGroupVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = multistoreGroupService.saveStoreMapping(multistoreGroupVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장 기능사용자 목록 조회
     * @param multistoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 02.
     */
    @RequestMapping(value = "/multistoreGroup/getMultiStoreUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMultiStoreUserList(MultistoreGroupVO multistoreGroupVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multistoreGroupService.getMultiStoreUserList(multistoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }


}
