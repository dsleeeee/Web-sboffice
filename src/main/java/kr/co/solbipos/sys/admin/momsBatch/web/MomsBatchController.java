package kr.co.solbipos.sys.admin.momsBatch.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.momsBatch.service.MomsBatchService;
import kr.co.solbipos.sys.admin.momsBatch.service.MomsBatchVO;
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
 * @Class Name : MomsBatchController.java
 * @Description : 시스템관리 > 관리자기능 > 맘스터치일괄처리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2023.02.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sys/admin/momsBatch")
public class MomsBatchController {

    private final SessionService sessionService;
    private final MomsBatchService momsBatchService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MomsBatchController(SessionService sessionService, MomsBatchService momsBatchService) {
        this.sessionService = sessionService;
        this.momsBatchService = momsBatchService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/momsBatch/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin/momsBatch/momsBatch";
    }

    /**
     * 일괄처리
     * @param momsBatchVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/momsBatch/batchProc.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result batchProc(@RequestBody MomsBatchVO momsBatchVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = momsBatchService.batchProc(momsBatchVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장코드 조회
     * @param request
     * @param response
     * @param model
     * @param momsBatchVO
     * @return
     */
    @RequestMapping(value = "/momsBatch/selectStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStoreList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, MomsBatchVO momsBatchVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = momsBatchService.selectStoreList(momsBatchVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, momsBatchVO);
    }

}
