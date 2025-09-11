package kr.co.solbipos.kookmin.base.termInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoService;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
/**
 * @Class Name  : TermInfoController.java
 * @Description : 국민대 > 기초관리 > 학기정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/base/termInfo")
public class TermInfoController {

    private final SessionService sessionService;
    private final TermInfoService termInfoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TermInfoController(SessionService sessionService, TermInfoService termInfoService) {
        this.sessionService = sessionService;
        this.termInfoService = termInfoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/termInfo/view.sb", method = RequestMethod.GET)
    public String termInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/base/termInfo/termInfo";
    }

    /**
     * 학기정보 조회
     *
     * @param   termInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 05.
     */
    @RequestMapping(value = "/termInfo/getTermInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTermInfoList(TermInfoVO termInfoVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = termInfoService.getTermInfoList(termInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, termInfoVO);
    }

    /**
     * 학기정보 저장
     * @param   termInfoVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 05.
     * @return
     */
    @RequestMapping(value = "/termInfo/saveTermInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTermInfo(@RequestBody TermInfoVO[] termInfoVOS, HttpServletRequest request,
                                  HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = termInfoService.saveTermInfo(termInfoVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
