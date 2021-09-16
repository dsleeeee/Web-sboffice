package kr.co.solbipos.adi.sms.smsTelNoManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SmsTelNoManageController.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsTelNoManage")
public class SmsTelNoManageController {

    private final SessionService sessionService;
    private final SmsTelNoManageService smsTelNoManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsTelNoManageController(SessionService sessionService, SmsTelNoManageService smsTelNoManageService) {
        this.sessionService = sessionService;
        this.smsTelNoManageService = smsTelNoManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsTelNoManage/list.sb", method = RequestMethod.GET)
    public String smsTelNoManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsTelNoManage/smsTelNoManage";
    }

    /**
     * 발신번호관리 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 09. 15.
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoManageList(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsTelNoManageService.getSmsTelNoManageList(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /**
     * 발신번호관리 저장
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 15.
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoManageSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoManageSaveUpdate(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsTelNoManageSaveUpdate(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}