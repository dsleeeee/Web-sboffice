package kr.co.solbipos.sale.benson.saleTrnsitnBenson.web;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonService;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonVO;

/**
 * @Class Name : SaleTrnsitnBensonController.java
 * @Description : 벤슨 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/saleTrnsitnBenson")
public class SaleTrnsitnBensonController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final SaleTrnsitnBensonService saleTrnsitnBensonService;

    /** Constructor Injection */
    @Autowired
    public SaleTrnsitnBensonController(SessionService sessionService, SaleTrnsitnBensonService saleTrnsitnBensonService) {
        this.sessionService = sessionService;
        this.saleTrnsitnBensonService = saleTrnsitnBensonService;
    }

    /**
     * 매출추이분석 - 화면 이동 (벤슨 > 매출분석 > 매출추이분석)
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2026.09.10
    */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/benson/saleTrnsitnBenson/saleTrnsitnBenson";
    }

    /**
     * 매출추이분석 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @param   request
     * @return  kr.co.common.data.structure.Result
     * @author  김유승
     * @since   2026.09.10
    */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaletrnsitnBensonList(SaleTrnsitnBensonVO saleTrnsitnBensonVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = saleTrnsitnBensonService.getSaletrnsitnBensonList(saleTrnsitnBensonVO, sessionInfoVO);

        Result result = returnListJson(Status.OK, list, saleTrnsitnBensonVO);
        LOGGER.debug("### result.getData(): " + result.getData());
        return result;
    }

    /**
     * 매출추이분석(엑셀) 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @param   request
     * @return  kr.co.common.data.structure.Result
     * @author  김유승
     * @since   2026.09.10
    */
    @RequestMapping(value = "/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaletrnsitnBensonExcelList(SaleTrnsitnBensonVO saleTrnsitnBensonVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = saleTrnsitnBensonService.getSaletrnsitnBensonExcelList(saleTrnsitnBensonVO, sessionInfoVO);

        Result result = returnListJson(Status.OK, list, saleTrnsitnBensonVO);
        LOGGER.debug("### result.getData(): " + result.getData());
        return result;
    }

}
