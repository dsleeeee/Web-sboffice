package kr.co.solbipos.sys.admin.posSmartOrderCon.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.posSmartOrderCon.service.PosSmartOrderConService;
import kr.co.solbipos.sys.admin.posSmartOrderCon.service.PosSmartOrderConVO;
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
 * @Class Name : PosSmartOrderConController.java
 * @Description : 시스템관리 > 관리자기능 > 스마트오더연결상태
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.20  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.06.20
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sys/admin/posSmartOrderCon")
public class PosSmartOrderConController {

    private final SessionService sessionService;
    private final PosSmartOrderConService posSmartOrderConService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PosSmartOrderConController(SessionService sessionService, PosSmartOrderConService posSmartOrderConService) {
        this.sessionService = sessionService;
        this.posSmartOrderConService = posSmartOrderConService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/posSmartOrderCon/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin/posSmartOrderCon/posSmartOrderCon";
    }

    /**
     * 매장코드 조회
     * @param request
     * @param response
     * @param model
     * @param posSmartOrderConVO
     * @return
     */
    @RequestMapping(value = "/posSmartOrderCon/getPosSmartOrderConList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosSmartOrderConList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, PosSmartOrderConVO posSmartOrderConVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posSmartOrderConService.getPosSmartOrderConList(posSmartOrderConVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, posSmartOrderConVO);
    }

}
