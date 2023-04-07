package kr.co.solbipos.store.manage.storeCloseExcept.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptService;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptVO;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreCloseExceptController.java
 * @Description : 기초관리 > 매장정보관리 > 폐점제외매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.04.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/storeCloseExcept")
public class StoreCloseExceptController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final StoreCloseExceptService storeCloseExceptService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public StoreCloseExceptController(StoreCloseExceptService storeCloseExceptService, SessionService sessionService) {
        this.storeCloseExceptService = storeCloseExceptService;
        this.sessionService = sessionService;
    }

    /**
     * 폐점제외매장 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.04.07
     */
    @RequestMapping(value = "/storeCloseExcept/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        model.addAttribute("vanComboList", convertToJson(storeCloseExceptService.getVanComboList()));

        return "store/manage/storeCloseExcept/storeCloseExcept";
    }

    /**
     * 폐점제외매장 - 매장 조회
     * @param   request
     * @param   response
     * @param   storeCloseExceptVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.04.07
     */
    @RequestMapping(value = "/storeCloseExcept/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(HttpServletRequest request, HttpServletResponse response,
                                    StoreCloseExceptVO storeCloseExceptVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeCloseExceptService.getStoreList(storeCloseExceptVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeCloseExceptVO);

    }

    /**
     * 폐점제외매장 - 폐점제외매장 조회
     * @param   request
     * @param   response
     * @param   storeCloseExceptVO
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.04.07
     */
    @RequestMapping(value = "/storeCloseExcept/getStoreCloseExceptList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCloseExceptList(HttpServletRequest request, HttpServletResponse response,
                               StoreCloseExceptVO storeCloseExceptVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = storeCloseExceptService.getStoreCloseExceptList(storeCloseExceptVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeCloseExceptVO);

    }

    /**
     * 폐점제외매장 - 폐점제외매장 등록
     * @param storeCloseExceptVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2023.04.07
     */
    @RequestMapping(value = "/storeCloseExcept/saveStoreCloseExcept.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreCloseExcept(@RequestBody StoreCloseExceptVO[] storeCloseExceptVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeCloseExceptService.saveStoreCloseExcept(storeCloseExceptVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 폐점제외매장 - 폐점제외매장 삭제
     * @param storeCloseExceptVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2023.04.07
     */
    @RequestMapping(value = "/storeCloseExcept/deleteStoreCloseExcept.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteStoreCloseExcept(@RequestBody StoreCloseExceptVO[] storeCloseExceptVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeCloseExceptService.deleteStoreCloseExcept(storeCloseExceptVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
