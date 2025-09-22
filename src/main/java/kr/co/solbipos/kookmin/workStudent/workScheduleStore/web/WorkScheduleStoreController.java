package kr.co.solbipos.kookmin.workStudent.workScheduleStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelClassVO;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoVO;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateVO;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoVO;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreService;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreVO;
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
 * @Class Name  : WorkScheduleStoreController.java
 * @Description : 국민대 > 근로학생관리 > 매장별 근무테이블 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.09
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/workStudent/workScheduleStore")
public class WorkScheduleStoreController {

    private final SessionService sessionService;
    private final WorkScheduleStoreService workScheduleStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkScheduleStoreController(SessionService sessionService, WorkScheduleStoreService workScheduleStoreService) {
        this.sessionService = sessionService;
        this.workScheduleStoreService = workScheduleStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workScheduleStore/view.sb", method = RequestMethod.GET)
    public String termInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workScheduleStore/workScheduleStore";
    }

    /**
     * 근무테이블 조회
     *
     * @param   workScheduleStoreVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 09.
     */
    @RequestMapping(value = "/workScheduleStore/getWorkScheduleStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkScheduleStoreList(WorkScheduleStoreVO workScheduleStoreVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = workScheduleStoreService.getWorkScheduleStoreList(workScheduleStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workScheduleStoreVO);
    }

    /**
     * 로우 추가 매장별 근무코드 조회
     *
     * @param   workScheduleStoreVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 09.
     */
    @RequestMapping(value = "/workScheduleStore/addRowWorkScheduleStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addRowWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = workScheduleStoreService.addRowWorkScheduleStore(workScheduleStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workScheduleStoreVO);
    }

    /**
     * 근무테이블 저장
     *
     * @param   workScheduleStoreVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 09.
     */
    @RequestMapping(value = "/workScheduleStore/saveWorkScheduleStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWorkScheduleStore(@RequestBody WorkScheduleStoreVO[] workScheduleStoreVOs, HttpServletRequest request,
                               HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workScheduleStoreService.saveWorkScheduleStore(workScheduleStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 학기정보 조회
     *
     * @param   workScheduleStoreVO
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 22.
     */
    @RequestMapping(value = "/workScheduleStore/getTermInfoChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTermInfoChk(WorkScheduleStoreVO workScheduleStoreVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = workScheduleStoreService.getTermInfoChk(workScheduleStoreVO, sessionInfoVO);

        return returnJson(Status.OK, list);
    }
}
