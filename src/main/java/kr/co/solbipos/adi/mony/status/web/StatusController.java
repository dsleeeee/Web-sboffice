package kr.co.solbipos.adi.mony.status.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.mony.status.service.StatusService;
import kr.co.solbipos.adi.mony.status.service.StatusVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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
* @Class Name : DrawHistController.java
* @Description : 부가서비스 > 금전처리 > 금전현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.09.09  김태수      최초생성
*
* @author NHN한국사이버결제 KCP 김태수
* @since 2018.09.09
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/adi/mony/status/status/")
public class StatusController {
     
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    StatusService statusService;
    /** service */
    @Autowired
    SessionService sessionService;
    
    /**
     * 부가서비스 > 금전처리 > 금전현황 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String statusList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "adi/mony/status/status";
    }

    /**
     * 부가서비스 > 금전처리 > 금전현황 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result statusListPost(StatusVO statusVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        LOGGER.debug(statusVO.toString());
        List<StatusVO> result = statusService.selectStatus(statusVO, sessionInfoVO);

        return returnListJson(Status.OK, result, statusVO);
    }
    
    /**
     * 금전현황 입금/출금 SELECT에 따른 계정 조회
     *
     * @param StatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "accnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result employee(StatusVO statusVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> result = statusService.selectAccntList(statusVO);
        
        return returnListJson(Status.OK, result, statusVO);
    }
}
