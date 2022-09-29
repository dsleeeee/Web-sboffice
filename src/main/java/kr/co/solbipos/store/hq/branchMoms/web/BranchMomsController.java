package kr.co.solbipos.store.hq.branchMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.branchMoms.service.BranchMomsService;
import kr.co.solbipos.store.hq.branchMoms.service.BranchMomsVO;
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
 * @Class Name : BranchMomsController.java
 * @Description : 가맹점관리 > 본사정보 > 본사-지사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.23  권지현      최초생성
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2022.09.23
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/store/hq/branchMoms/")
public class BranchMomsController {

    private final BranchMomsService branchMomsService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public BranchMomsController(BranchMomsService branchMomsService, SessionService sessionService) {
        this.branchMomsService = branchMomsService;
        this.sessionService = sessionService;
    }

    /**
     * 본사-지사 관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.09.23
     */
    @RequestMapping(value = "branchMoms/view.sb", method = RequestMethod.GET)
    public String view(BranchMomsVO branchMomsVO, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        return "store/hq/branchMoms/branchMoms";
    }

    /**
     * 조회
     * @param   branchMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2022.09.23
     */
    @RequestMapping(value = "branchMoms/getBranchMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(BranchMomsVO branchMomsVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = branchMomsService.getBranchMomsList(branchMomsVO, sessionInfoVO);

        return returnListJson(Status.OK, list, branchMomsVO);
    }


    /**
     * 본사 신규등록
     * @param   branchMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2022.
     */
    @RequestMapping(value = "branchMoms/saveBranchMoms.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBranchMoms(@RequestBody BranchMomsVO branchMomsVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int cnt = branchMomsService.saveBranchMoms(branchMomsVO, sessionInfoVO);

        return returnJson(Status.OK, cnt);
    }

}
