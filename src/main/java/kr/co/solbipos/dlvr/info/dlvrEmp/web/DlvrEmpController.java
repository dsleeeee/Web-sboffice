package kr.co.solbipos.dlvr.info.dlvrEmp.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpService;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;
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
 * @Class Name : DlvrEmpController.java
 * @Description : 배달관리 > 배달정보 > 배달사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.10.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/dlvr/manage/info/")
public class DlvrEmpController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrEmpService dlvrEmpService;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public DlvrEmpController(DlvrEmpService dlvrEmpService, SessionService sessionService, MessageService messageService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.dlvrEmpService = dlvrEmpService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 배달사원정보관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 10. 20.
     */
    @RequestMapping(value = "dlvrEmp/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "dlvr/info/dlvrEmp/dlvrEmp";
    }

    /**
     * 배달사원정보 목록 조회
     *
     * @param dlvrEmpVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 10. 20.
     */
    @RequestMapping(value = "dlvrEmp/getDlvrEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrEmpList(DlvrEmpVO dlvrEmpVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dlvrEmpService.getDlvrEmpList(dlvrEmpVO, sessionInfoVO);

        return returnListJson(Status.OK, list, dlvrEmpVO);
    }

    /**
     * 배달사원정보 상세 조회
     *
     * @param dlvrEmpVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 10. 20.
     */
    @RequestMapping(value = "dlvrEmp/getDlvrEmpDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrEmpDtl(DlvrEmpVO dlvrEmpVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = dlvrEmpService.getDlvrEmpDtl(dlvrEmpVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 배달사원 신규등록
     *
     * @param dlvrEmpVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 10. 20.
     */
    @RequestMapping(value = "dlvrEmp/insertDlvrEmp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertDlvrEmp(@RequestBody DlvrEmpVO dlvrEmpVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrEmpService.insertDlvrEmp(dlvrEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 배달사원 정보수정
     *
     * @param dlvrEmpVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 10. 20.
     */
    @RequestMapping(value = "dlvrEmp/updateDlvrEmp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDlvrEmp(@RequestBody DlvrEmpVO dlvrEmpVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrEmpService.updateDlvrEmp(dlvrEmpVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
