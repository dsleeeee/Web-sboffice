package kr.co.solbipos.base.store.emp.system.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpService;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;


/**
 * @Class Name : SystemEmpController.java
 * @Description : 기초관리 > 사원정보관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자      수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.26  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.26
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/emp/system")
public class SystemEmpController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final SystemEmpService systemEmpService;

    /** Constructor Injection */
    @Autowired
    public SystemEmpController(SessionService sessionService, SystemEmpService systemEmpService) {
        this.sessionService = sessionService;
        this.systemEmpService = systemEmpService;
    }

    /**
     * 사원 리스트 화면
     * @param model
     * @return the string
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(Model model) {
        return "base/store/emp/systemEmp";
    }


    /**
     * 사원 목록 조회
     * @param systemEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result view(HttpServletRequest request, SystemEmpVO systemEmpVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = systemEmpService.getSystemEmpList(systemEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, list,systemEmpVO);
    }

    /**
     * 사원정보 상세 조회
     * @param systemEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    public Result getDtlInfo(SystemEmpVO systemEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = systemEmpService.getSystemEmpDtlInfo(systemEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사원정보 등록
     * @param systemEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/regist.sb", method = RequestMethod.POST)
    public Result regist(@RequestBody SystemEmpVO systemEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        EmpResult empResult = systemEmpService.insertSystemEmpInfo(systemEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 사원정보 웹 사용자 ID 조회 (중복체크)
     * @param systemEmpVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chkSystemUserId.sb", method = RequestMethod.POST)
    public Result chkSystemUserId(SystemEmpVO systemEmpVO) {

        EmpResult empResult= systemEmpService.getSystemUserIdCnt(systemEmpVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 사원정보 수정
     * @param systemEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    public Result save(@RequestBody SystemEmpVO systemEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = systemEmpService.saveSystemEmpInfo(systemEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }

    /**
     * 비밀번호 변경
     * @param systemEmpVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword.sb", method = RequestMethod.POST)
    public Result modifyPassword(@RequestBody SystemEmpVO systemEmpVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        EmpResult empResult = systemEmpService.modifyPassword(systemEmpVO,sessionInfoVO);

        return returnJson(Status.OK, empResult);
    }


}
