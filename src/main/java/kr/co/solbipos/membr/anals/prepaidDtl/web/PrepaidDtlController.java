package kr.co.solbipos.membr.anals.prepaidDtl.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlService;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlVO;
import kr.co.solbipos.membr.anals.prepaidDtl.service.PrepaidDtlService;
import kr.co.solbipos.membr.anals.prepaidDtl.service.PrepaidDtlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : PrepaidDtlController.java
 * @Description : 회원관리 > 회원분석 > 선불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.27  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/prepaidDtl/")
public class PrepaidDtlController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PrepaidDtlService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public PrepaidDtlController(PrepaidDtlService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "prepaidDtl/prepaidDtlView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/prepaidDtl/prepaidDtlView";

    }

    /**
     * 선불 회원 충전, 사용 내역 상세
     *
     * @param prepaidDtlVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "prepaidDtl/getPrepaidDtlMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrepaidDtlMemberList(PrepaidDtlVO prepaidDtlVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPrepaidDtlMemberList(prepaidDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prepaidDtlVO);
    }

}
