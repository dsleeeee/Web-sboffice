package kr.co.solbipos.sale.status.saleStatusKwu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.saleStatusKwu.service.SaleStatusKwuService;
import kr.co.solbipos.sale.status.saleStatusKwu.service.SaleStatusKwuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SaleStatusKwuController.java
 * @Description : 광운대 > 리포트 > 매출현황2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.02.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/saleStatusKwu")
public class SaleStatusKwuController {

    private final SessionService sessionService;
    private final SaleStatusKwuService saleStatusKwuService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleStatusKwuController(SessionService sessionService, SaleStatusKwuService saleStatusKwuService) {
        this.sessionService = sessionService;
        this.saleStatusKwuService = saleStatusKwuService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleStatusKwu/view.sb", method = RequestMethod.GET)
    public String saleStatusKwuView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/saleStatusKwu/saleStatusKwu";
    }

    /**
     * 매출현황2 - 조회
     *
     * @param saleStatusKwuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 14.
     */
    @RequestMapping(value = "/saleStatusKwu/getSaleStatusKwuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleStatusKwuList(SaleStatusKwuVO saleStatusKwuVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleStatusKwuService.getSaleStatusKwuList(saleStatusKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleStatusKwuVO);
    }
}