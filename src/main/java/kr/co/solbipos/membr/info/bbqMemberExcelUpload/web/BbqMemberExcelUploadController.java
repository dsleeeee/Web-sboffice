package kr.co.solbipos.membr.info.bbqMemberExcelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.membr.info.bbqMemberExcelUpload.service.BbqMemberExcelUploadService;
import kr.co.solbipos.membr.info.bbqMemberExcelUpload.service.BbqMemberExcelUploadVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
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

/**
 * @Class Name : BbqMemberExcelUploadController.java
 * @Description : 회원관리 > 회원정보 > 회원엑셀업로드(BBQ)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.26  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/info/bbqUpload")
public class BbqMemberExcelUploadController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final BbqMemberExcelUploadService bbqMemberExcelUploadService;
    private final RegistService registService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public BbqMemberExcelUploadController(SessionService sessionService, BbqMemberExcelUploadService bbqMemberExcelUploadService, RegistService registService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.bbqMemberExcelUploadService = bbqMemberExcelUploadService;
        this.registService = registService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
    * 회원엑셀업로드(BBQ) 페이지 이동
    *
    * @param request
    * @param response
    * @param model
    */
    @RequestMapping(value = "/bbqUpload/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 회원등급 리스트 조회
        model.addAttribute("memberClassList", cmmCodeUtil.assmblObj(registService.getMembrClassList(sessionInfoVO), "name", "value", UseYn.ALL));

        // 회원등급 관리구분
        model.addAttribute("membrClassManageFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1"));

        return "membr/info/view/bbqMemberExcelUpload";
    }

    /**
     * 회원엑셀업로드(BBQ) 저장
     *
     * @param bbqMemberExcelUploadVOs
     * @param request
     * @return
     */
    @RequestMapping(value = "/bbqUpload/memberExcelSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result memberExcelSave(@RequestBody BbqMemberExcelUploadVO[] bbqMemberExcelUploadVOs, RegistVO registVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = bbqMemberExcelUploadService.memberExcelSave(bbqMemberExcelUploadVOs, registVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
