package kr.co.solbipos.membr.anals.membrProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.dayMembr.service.DayMembrVO;
import kr.co.solbipos.membr.anals.membrProd.service.MembrProdService;
import kr.co.solbipos.membr.anals.membrProd.service.MembrProdVO;
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
 * @Class Name : MembrProdController.java
 * @Description : 회원관리 > 회원분석 > 회원 상품 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019. 09.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/membrProd/")
public class MembrProdController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MembrProdService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public MembrProdController(MembrProdService service, SessionService sessionService) {
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
    @RequestMapping(value = "membrProd/membrProdView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/membrProd/membrProdView";
    }

    /**
     * 회원 상품 구매내역 조회
     *
     * @param membrProdVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "membrProd/getMembrProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMembrProdList(MembrProdVO membrProdVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getMembrProdList(membrProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, membrProdVO);
    }

}
