package kr.co.solbipos.sale.anals.nonSaleCharge.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeService;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : NonSaleChargeController.java
 * @Description : 매출관리 - 매출분석 - 비매출충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.02.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sale/anals/nonSaleCharge/")
public class NonSaleChargeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final NonSaleChargeService nonSaleChargeService;

    /** Constructor Injection */
    @Autowired
    public NonSaleChargeController(SessionService sessionService, NonSaleChargeService nonSaleChargeService){
        this.sessionService = sessionService;
        this.nonSaleChargeService = nonSaleChargeService;
    }

    /**
     * 비매출 충전내역 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.02.04
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/anals/nonSaleCharge/nonSaleCharge";
    }

    /**
     * 비매출 충전내역 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.02.04
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonSaleChargeList(NonSaleChargeVO nonSaleChargeVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleChargeService.getNonSaleChargeList(nonSaleChargeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, nonSaleChargeVO);
    }

    /**
     * 비매출 충전내역 조회 Excel 다운로드
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.02.04
     * @return
     */
    @RequestMapping(value = "/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonSaleChargeExcelList(NonSaleChargeVO nonSaleChargeVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleChargeService.getNonSaleChargeExcelList(nonSaleChargeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, nonSaleChargeVO);
    }
}
