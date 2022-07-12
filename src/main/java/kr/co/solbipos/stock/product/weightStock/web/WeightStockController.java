package kr.co.solbipos.stock.product.weightStock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockService;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockVO;
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

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : WeightStockController.java
 * @Description : 재고관리 > 생산관리 > 중량재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/stock/product/weightStock")
public class WeightStockController {

    private final SessionService sessionService;
    private final WeightStockService weightStockService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WeightStockController(SessionService sessionService, WeightStockService weightStockService) {
        this.sessionService = sessionService;
        this.weightStockService = weightStockService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/weightStock/list.sb", method = RequestMethod.GET)
    public String weightStockView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "stock/product/weightStock/weightStock";
    }

    /**
     * 중량재고현황(매장) - 조회
     *
     * @param weightStockVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 07. 06.
     */
    @RequestMapping(value = "/weightStock/getWeightStockList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightStockList(WeightStockVO weightStockVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = weightStockService.getWeightStockList(weightStockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, weightStockVO);
    }
}