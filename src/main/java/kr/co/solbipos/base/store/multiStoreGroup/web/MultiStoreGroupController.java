package kr.co.solbipos.base.store.multiStoreGroup.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.multiStoreGroup.service.MultiStoreGroupService;
import kr.co.solbipos.base.store.multiStoreGroup.service.MultiStoreGroupVO;
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
 * @Class Name : MultiStoreGroupController.java
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
@RequestMapping("/base/store/multiStoreGroup")
public class MultiStoreGroupController {

    private final SessionService sessionService;
    private final MultiStoreGroupService multiStoreGroupService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MultiStoreGroupController(SessionService sessionService, MultiStoreGroupService multiStoreGroupService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.multiStoreGroupService = multiStoreGroupService;
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
    @RequestMapping(value = "/multiStoreGroup/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/store/multiStoreGroup/multiStoreGroup";
    }

    /**
     * 그룹조회
     * @param multiStoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 30.
     */
    @RequestMapping(value = "/multiStoreGroup/getMultiStoreGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMultiStoreGroup(MultiStoreGroupVO multiStoreGroupVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multiStoreGroupService.getMultiStoreGroup(multiStoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 그룹저장
     * @param multiStoreGroupVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 07. 30.
     */
    @RequestMapping(value = "/multiStoreGroup/saveMultiStoreGroup.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMultiStoreGroup(@RequestBody MultiStoreGroupVO[] multiStoreGroupVOs, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = multiStoreGroupService.saveMultiStoreGroup(multiStoreGroupVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }















    /**
     * 매장조회
     * @param multiStoreGroupVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021. 08. 02.
     */
    @RequestMapping(value = "/multiStoreGroup/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(MultiStoreGroupVO multiStoreGroupVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = multiStoreGroupService.getStoreList(multiStoreGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }


}
