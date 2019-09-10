package kr.co.solbipos.membr.anals.periodMembr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.periodMembr.service.PeriodMembrService;
import kr.co.solbipos.membr.anals.periodMembr.service.PeriodMembrVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : PeriodMembrController.java
 * @Description : 회원관리 > 회원분석 > 기간회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019. 09.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/periodMembr/")
public class PeriodMembrController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PeriodMembrService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public PeriodMembrController(PeriodMembrService service, SessionService sessionService) {
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
    @RequestMapping(value = "periodMembr/periodMembrView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/periodMembr/periodMembrView";
    }

    /**
     * 기간회원 구매내역 조회
     *
     * @param PeriodMembrVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "periodMembr/getPeriodMembrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodMembrList(PeriodMembrVO periodMembrVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test : " + periodMembrVO.getEndDate());

        List<DefaultMap<Object>> result = service.getPeriodMembrList(periodMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, periodMembrVO);
    }
}
