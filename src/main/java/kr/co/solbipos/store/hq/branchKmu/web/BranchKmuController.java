package kr.co.solbipos.store.hq.branchKmu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.branchKmu.service.BranchKmuService;
import kr.co.solbipos.store.hq.branchKmu.service.BranchKmuVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageService;
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
 * @Class Name : BranchKmuController.java
 * @Description : 국민대 > 매장관리 > 그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value="/store/hq/branchKmu/")
public class BranchKmuController {

    private final BranchKmuService branchKmuService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public BranchKmuController(BranchKmuService branchKmuService, SessionService sessionService) {
        this.branchKmuService = branchKmuService;
        this.sessionService = sessionService;
    }

    /**
     * 본사-그룹 관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.09.23
     */
    @RequestMapping(value = "branchKmu/view.sb", method = RequestMethod.GET)
    public String view(BranchKmuVO branchKmuVO, HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        return "store/hq/branchKmu/branchKmu";
    }

    /**
     * 조회
     * @param   branchKmuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2022.09.23
     */
    @RequestMapping(value = "branchKmu/getBranchKmuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(BranchKmuVO branchKmuVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = branchKmuService.getBranchKmuList(branchKmuVO, sessionInfoVO);

        return returnListJson(Status.OK, list, branchKmuVO);
    }


    /**
     * 본사 신규등록
     * @param   branchKmuVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2022.
     */
    @RequestMapping(value = "branchKmu/saveBranchKmu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBranchKmu(@RequestBody BranchKmuVO branchKmuVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = branchKmuService.saveBranchKmu(branchKmuVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

}
