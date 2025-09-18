package kr.co.solbipos.membr.anals.postpaidSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaidSale.service.PostpaidSaleService;
import kr.co.solbipos.membr.anals.postpaidSale.service.PostpaidSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : PostpaidSaleController.java
 * @Description : 국민대 > 매출처관리 > 외상매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/membr/anals/postpaidSale")
public class PostpaidSaleController {

    private final SessionService sessionService;
    private final PostpaidSaleService postpaidSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PostpaidSaleController(SessionService sessionService, PostpaidSaleService postpaidSaleService) {
        this.sessionService = sessionService;
        this.postpaidSaleService = postpaidSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/postpaidSale/list.sb", method = RequestMethod.GET)
    public String postpaidSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "membr/anals/postpaidSale/postpaidSale";
    }

    /**
     * 외상매출조회 - 조회
     *
     * @param postpaidSaleVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2025. 09. 17.
     */
    @RequestMapping(value = "/postpaidSale/getPostpaidSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidSaleList(PostpaidSaleVO postpaidSaleVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = postpaidSaleService.getPostpaidSaleList(postpaidSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidSaleVO);
    }

}