package kr.co.solbipos.adi.alimtalk.alimtalkAnalysisTab.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : AlimtalkAnalysisTabController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡전송분석(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkAnalysisTab")
public class AlimtalkAnalysisTabController {

    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkAnalysisTabController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/alimtalkAnalysisTab/list.sb", method = RequestMethod.GET)
    public String alimtalkAnalysisTabView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/alimtalk/alimtalkAnalysisTab/alimtalkAnalysisTab";
    }
}