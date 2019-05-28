package kr.co.solbipos.membr.anals.postpaidDtl.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlService;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlVO;
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
 * @Class Name : PostpaidDtlController.java
 * @Description : 회원관리 > 회원분석 > 후불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.21  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/postpaidDtl/")
public class PostpaidDtlController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostpaidDtlService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public PostpaidDtlController(PostpaidDtlService service, SessionService sessionService) {
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
    @RequestMapping(value = "postpaidDtl/postpaidDtlView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/postpaidDtl/postpaidDtlView";

    }

    /**
     * 후불 회원 외상, 입금 내역 상세
     *
     * @param postpaidDtlVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaidDtl/getPostpaidDtlMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidDtlMemberList(PostpaidDtlVO postpaidDtlVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPostpaidDtlMemberList(postpaidDtlVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidDtlVO);
    }
}
