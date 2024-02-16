package kr.co.solbipos.store.manage.accountManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.accountManage.service.AccountManageService;
import kr.co.solbipos.store.manage.accountManage.service.AccountManageVO;
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
 * @Class Name : AccountManageController.java
 * @Description : 기초관리 > 매장정보관리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.14  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/acountManage")
public class AccountManageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** service */
    private final AccountManageService accountManageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public AccountManageController(AccountManageService accountManageService, SessionService sessionService) {
        this.accountManageService = accountManageService;
        this.sessionService = sessionService;
    }

    /**
     * 계정관리 - 페이지이동
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "store/manage/accountManage/accountManageTab";
    }

    /**
     * 계정관리 - 장기미사용 계정리스트 조회
     * @param accountManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getLongTermUnusedList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLongTermUnusedList(AccountManageVO accountManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = accountManageService.getLongTermUnusedList(accountManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, accountManageVO);
    }

    /**
     * 계정관리 - 장기미사용 계정리스트 엑셀다운로드 조회
     * @param accountManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getLongTermUnusedExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLongTermUnusedExcelList(AccountManageVO accountManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = accountManageService.getLongTermUnusedExcelList(accountManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, accountManageVO);
    }

    /**
     * 계정관리 - 장기미사용 계정 상태 변경
     * @param accountManageVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveAccountStatChg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAccountStatChg(@RequestBody AccountManageVO[] accountManageVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = accountManageService.saveAccountStatChg(accountManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
