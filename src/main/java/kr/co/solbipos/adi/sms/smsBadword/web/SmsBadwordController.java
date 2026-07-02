package kr.co.solbipos.adi.sms.smsBadword.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsBadword.service.SmsBadwordService;
import kr.co.solbipos.adi.sms.smsBadword.service.SmsBadwordVO;
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
 * @Class Name : SmsBadwordController.java
 * @Description : 부가서비스 > SMS관리 > SMS금칙어(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.01  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsBadword")
public class SmsBadwordController {

    private final SessionService sessionService;
    private final SmsBadwordService smsBadwordService;

    @Autowired
    public SmsBadwordController(SessionService sessionService, SmsBadwordService smsBadwordService) {
        this.sessionService = sessionService;
        this.smsBadwordService = smsBadwordService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/smsBadword/smsBadwordTab";
    }

    /**
     * 금칙어관리 - 목록 조회
     */
    @RequestMapping(value = "/badwordManage/getBadwordManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBadwordManageList(SmsBadwordVO smsBadwordVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsBadwordService.getBadwordManageList(smsBadwordVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsBadwordVO);
    }

    /**
     * 금칙어관리 - 저장
     */
    @RequestMapping(value = "/badwordManage/getBadwordManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBadwordManageSave(@RequestBody SmsBadwordVO[] smsBadwordVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsBadwordService.saveBadwordManage(smsBadwordVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 탐지/차단 결과 로그 - 목록 조회
     */
    @RequestMapping(value = "/msgBlockLog/getMsgBlockLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgBlockLogList(SmsBadwordVO smsBadwordVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = smsBadwordService.getMsgBlockLogList(smsBadwordVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsBadwordVO);
    }
}
