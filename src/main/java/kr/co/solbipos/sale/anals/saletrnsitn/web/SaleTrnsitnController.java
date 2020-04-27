package kr.co.solbipos.sale.anals.saletrnsitn.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

import java.lang.reflect.Field;
import java.util.HashMap;
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
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnService;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnVO;

/**
 * @Class Name : SaleTrnsitnController.java
 * @Description : 매출관리 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.14  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP 
 * @since 2020. 01.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sale/anals/saletrnsitn/")
public class SaleTrnsitnController {
	private final Logger 				LOGGER = LoggerFactory.getLogger(this.getClass());
	
    private final SessionService 		sessionService;
    private final SaleTrnsitnService 	saleTrnsitnService;
    private final CmmEnvUtil 			cmmEnvUtil;

    @Autowired
    public SaleTrnsitnController(SessionService sessionService, SaleTrnsitnService saleTrnsitnService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService 	= sessionService;
        this.saleTrnsitnService = saleTrnsitnService;
        this.cmmEnvUtil 		= cmmEnvUtil;
    }



	/**
	 * 매출추이분석 - 화면 이동 (매출관리 > 매출분석 > 매출추이분석)
	 * @param   request
	 * @param   response
	 * @param   model
	 * @return  String
	 * @author  조현수
	 * @since   2020. 01. 14
	*/
    @RequestMapping(value="list.sb", method=RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
    	return "sale/anals/saleTrnsitn/saleTrnsitn";
    }


    
    /**
	 * 매출추이분석 목록조회 (매출관리 > 매출분석 > 매출추이분석)
	 * @param   saleTrnsitnVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020. 01. 14
	*/
    @RequestMapping(value="list.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getSaletrnsitnList(SaleTrnsitnVO saleTrnsitnVO, HttpServletRequest request) {
    	System.out.println("### saleTrnsitnVO: " + saleTrnsitnVO);

        SessionInfoVO 				sessionInfoVO 	= sessionService.getSessionInfo(request);
        List<DefaultMap<String>> 	list 			= saleTrnsitnService.getSaletrnsitnList(saleTrnsitnVO, sessionInfoVO);
        
        //return returnListJson(Status.OK, list, saleTrnsitnVO);
        
        Result result = returnListJson(Status.OK, list, saleTrnsitnVO);
        LOGGER.debug("### result.getData(): " + result.getData() );
        return result;
    }

    
    
    /**
	 * 매출추이분석(엑셀) 목록조회 (매출관리 > 매출분석 > 매출추이분석)
	 * @param   saleTrnsitnVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  박지선
	 * @since   2020. 04. 22
	*/
    @RequestMapping(value="excelList.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getSaletrnsitnExcelList(SaleTrnsitnVO saleTrnsitnVO, HttpServletRequest request) {
    	System.out.println("### saleTrnsitnVO: " + saleTrnsitnVO);

        SessionInfoVO 				sessionInfoVO 	= sessionService.getSessionInfo(request);
        List<DefaultMap<String>> 	list 			= saleTrnsitnService.getSaletrnsitnExcelList(saleTrnsitnVO, sessionInfoVO);
        
        //return returnListJson(Status.OK, list, saleTrnsitnVO);
        
        Result result = returnListJson(Status.OK, list, saleTrnsitnVO);
        LOGGER.debug("### result.getData(): " + result.getData() );
        return result;
    }

}
