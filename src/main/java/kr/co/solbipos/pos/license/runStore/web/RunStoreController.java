package kr.co.solbipos.pos.license.runStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.pos.license.oper.service.OperVO;
import kr.co.solbipos.pos.license.runStore.service.RunStoreService;
import kr.co.solbipos.pos.license.runStore.service.RunStoreVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @Class Name : RunStoreController.java
 * @Description : 포스관리 > 라이선스 관리 > 런닝매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.11  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/pos/license/runStore")
public class RunStoreController {

    private final SessionService sessionService;
    private final RunStoreService runStoreService;

    public RunStoreController(SessionService sessionService, RunStoreService runStoreService) {
        this.sessionService = sessionService;
        this.runStoreService = runStoreService;
    }


    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024.04.11
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        return "pos/license/runStore/runStoreTab";
    }

    /**
     * 런닝매장현황탭 - 조회
     *
     * @param runStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/runStore/getRunStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRunStoreList(RunStoreVO runStoreVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = runStoreService.getRunStoreList(runStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, runStoreVO);
    }

    /**
     * 런닝COPY수 탭 - 조회
     *
     * @param runStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/runStore/getRunCopyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRunCopyList(RunStoreVO runStoreVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = runStoreService.getRunCopyList(runStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, runStoreVO);
    }

    /**
     * 런닝COPY수 탭 - 런닝/신규/폐점 매장 수 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   runStoreVO
     * @return  String
     * @author  김유승
     * @since   2024. 04. 11.
     */
    @RequestMapping(value = "/runStore/getRunCopyCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRunCopyCnt(HttpServletRequest request, HttpServletResponse response,
                               Model model, RunStoreVO runStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = runStoreService.getRunCopyCnt(runStoreVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 런닝매장추이 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   runStoreVO
     * @return  String
     * @author  김유승
     * @since   2024. 04. 15.
     */
    @RequestMapping(value = "/runStore/getRunTrnsitnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRunTrnsitnList(HttpServletRequest request, HttpServletResponse response,
                                Model model, RunStoreVO runStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = runStoreService.getRunTrnsitnList(runStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, runStoreVO);
    }
}
