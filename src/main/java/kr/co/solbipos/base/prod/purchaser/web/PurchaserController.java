package kr.co.solbipos.base.prod.purchaser.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.purchaser.service.PurchaserService;
import kr.co.solbipos.base.prod.purchaser.service.PurchaserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : PurchaserController.java
 * @Description : 국민대 > 매입처관리 > 매입처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/purchaser")
public class PurchaserController {

    private final SessionService sessionService;
    private final PurchaserService purchaserService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PurchaserController(SessionService sessionService, PurchaserService purchaserService) {
        this.sessionService = sessionService;
        this.purchaserService = purchaserService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/purchaser/list.sb", method = RequestMethod.GET)
    public String purchaserView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/prod/purchaser/purchaser";
    }

    /**
     * 매입처조회 - 조회
     *
     * @param purchaserVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 10. 10.
     */
    @RequestMapping(value = "/purchaser/getPurchaserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPurchaserList(PurchaserVO purchaserVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = purchaserService.getPurchaserList(purchaserVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, purchaserVO);
    }
}