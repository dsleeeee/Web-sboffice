package kr.co.solbipos.base.store.tblpt.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblpt.service.TblptService;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
* @Class Name : TblptController.java
* @Description : 기초관리 > 매장관리 > 테이블속성
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @
 * @
 * @
*
* @author
* @since
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/store/tblpt")
public class TblptController {
    private final TblptService tblptService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public TblptController(TblptService tblptService, SessionService sessionService) {
        this.tblptService = tblptService;
        this.sessionService = sessionService;
    }

    /**
     * 테이블속성 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/tblpt/view.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        return "base/store/tblpt/tblpt";
    }


    /**
     * 임시패스워드 생성 등록
     */
    @RequestMapping(value = "/tblpt/tblptOpn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result tblptOpn(TblptVO tblptVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String TBLPT_TEMP_PW = tblptService.tblptOpn(tblptVO, sessionInfoVO);

        return returnJson(Status.OK, TBLPT_TEMP_PW);
    }
}
