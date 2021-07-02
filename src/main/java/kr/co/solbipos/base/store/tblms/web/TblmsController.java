package kr.co.solbipos.base.store.tblms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblms.service.TblmsService;
import kr.co.solbipos.base.store.tblms.service.TblmsVO;
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
* @Class Name : TblmsController.java
* @Description :
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.13  김영근      최초생성
 * @ 2018.11.20  김지은      기능오류 수정 및 angular 변경
 * @ 2018.12.28  김지은      매장환경 복사 팝업 생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/store/tblms")
public class TblmsController {


    private final TblmsService tblmsService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public TblmsController(TblmsService tblmsService, SessionService sessionService) {
        this.tblmsService = tblmsService;
        this.sessionService = sessionService;
    }

    /**
     * 창고정보 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/tblms/view.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "base/store/tblms/tblms";
    }

    /**
     * 창고정보 리스트조회
     *
     * @param tblmsVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/tblms/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTblmsList(TblmsVO tblmsVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = tblmsService.getTblmsList(tblmsVO, sessionInfoVO);

        return returnListJson(Status.OK, list, tblmsVO);
    }

    /**
     * 임시패스워드 생성 등록
     */
    @RequestMapping(value = "/tblms/tblmsOpn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result tblmsOpn(TblmsVO tblmsVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String TBLMS_TEMP_PW = tblmsService.tblmsOpn(tblmsVO, sessionInfoVO);

        return returnJson(Status.OK, TBLMS_TEMP_PW);
    }
}
