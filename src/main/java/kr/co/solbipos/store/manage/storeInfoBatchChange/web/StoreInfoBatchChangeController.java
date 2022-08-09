package kr.co.solbipos.store.manage.storeInfoBatchChange.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storeInfoBatchChange.service.StoreInfoBatchChangeService;
import kr.co.solbipos.store.manage.storeInfoBatchChange.service.StoreInfoBatchChangeVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvConfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreInfoBatchChangeController.java
 * @Description : 기초관리 > 매장정보관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/store/manage/storeInfoBatchChange")
public class StoreInfoBatchChangeController {

    private final SessionService sessionService;
    private final StoreInfoBatchChangeService storeInfoBatchChangeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreInfoBatchChangeController(SessionService sessionService, StoreInfoBatchChangeService storeInfoBatchChangeService) {
        this.sessionService = sessionService;
        this.storeInfoBatchChangeService = storeInfoBatchChangeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeInfoBatchChange/list.sb", method = RequestMethod.GET)
    public String storeInfoBatchChangeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "store/manage/storeInfoBatchChange/storeInfoBatchChange";
    }

    /**
     * 매장정보일괄변경 - 조회
     *
     * @param storeInfoBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 08. 03.
     */
    @RequestMapping(value = "/storeInfoBatchChange/getStoreInfoBatchChangeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreInfoBatchChangeList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeInfoBatchChangeService.getStoreInfoBatchChangeList(storeInfoBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeInfoBatchChangeVO);
    }

    /**
     * 대리점코드 콤보박스 조회
     *
     * @param storeInfoBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 08. 03.
     */
    @RequestMapping(value = "/storeInfoBatchChange/getAgencyCdComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeInfoBatchChangeService.getAgencyCdComboList(storeInfoBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeInfoBatchChangeVO);
    }

    /**
     * 관리벤사 콤보박스 조회
     *
     * @param storeInfoBatchChangeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 08. 03.
     */
    @RequestMapping(value = "/storeInfoBatchChange/getVanCdComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVanCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeInfoBatchChangeService.getVanCdComboList(storeInfoBatchChangeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeInfoBatchChangeVO);
    }

    /**
     * 매장정보일괄변경 - 저장
     *
     * @param storeInfoBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 08. 04.
     */
    @RequestMapping(value = "/storeInfoBatchChange/getStoreInfoBatchChangeSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreInfoBatchChangeSave(@RequestBody StoreInfoBatchChangeVO[] storeInfoBatchChangeVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeInfoBatchChangeService.getStoreInfoBatchChangeSave(storeInfoBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}