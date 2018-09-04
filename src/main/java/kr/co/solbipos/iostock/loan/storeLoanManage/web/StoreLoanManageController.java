package kr.co.solbipos.iostock.loan.storeLoanManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.storeLoanManage.service.StoreLoanManageService;
import kr.co.solbipos.iostock.loan.storeLoanManage.service.StoreLoanManageVO;
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
 * @Class Name : StoreLoanManageController.java
 * @Description : 수불관리 > 수주관리 > 매장여신관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/iostock/loan/storeLoanManage")
public class StoreLoanManageController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    /** service */
    @Autowired
    StoreLoanManageService storeLoanManageService;
    @Autowired
    SessionService sessionService;
    @Autowired
    AuthService authService;
    @Autowired
    CmmMenuService cmmMenuService;

    /**
     * 매장여신관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 08. 20.
     */
    @RequestMapping(value = "/storeLoanManage/view.sb", method = RequestMethod.GET)
    public String storeLoanManageView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "iostock/loan/storeLoanManage/storeLoanManage";
    }

    /**
     * 매장여신관리 - 조회
     * @param   request
     * @param   response
     * @param   storeLoanManageVO
     * @param   model
     * @return  Result
     * @author  안동관
     * @since   2018. 08. 20.
     */
    
    @RequestMapping(value = "/storeLoanManage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreLoanManageList(HttpServletRequest request, HttpServletResponse response,
                                   StoreLoanManageVO storeLoanManageVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeLoanManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeLoanManageService.getStoreLoanManageList(storeLoanManageVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeLoanManageVO);

    }

    /**
     * 매장여신관리 - 여신 저장
     *
     * @param request
     * @param response
     * @param storeLoanManageVOs
     * @param model
     * @return Result
     * @author 안동관
     * @since 2018. 08. 20.
     */
    @RequestMapping(value = "/storeLoanManage/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreLoanManageList(@RequestBody StoreLoanManageVO[] storeLoanManageVOs, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = storeLoanManageService.saveLoanManageList(storeLoanManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

}
