package kr.co.solbipos.sys.admin.posLogCollectMgmt.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtService;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtVO;
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
 * @Class Name  : PosLogCollectMgmtController.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin/posLogCollectMgmt")
public class PosLogCollectMgmtController {

    private final SessionService sessionService;
    private final PosLogCollectMgmtService posLogCollectMgmtService;

    /**
     *  Constructor Injection
     */
    @Autowired
    public PosLogCollectMgmtController(SessionService sessionService, PosLogCollectMgmtService posLogCollectMgmtService) {
        this.sessionService = sessionService;
        this.posLogCollectMgmtService = posLogCollectMgmtService;
    }

    /**
     * 페이지 이동
     *
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2026. 03. 04.
     */
    @RequestMapping(value="/posLogCollectMgmt/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model){
        return "sys/admin/posLogCollectMgmt/posLogCollectMgmt";
    }

    /**
     * POS로그수집관리 - 조회
     *
     * @param   posLogCollectMgmtVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 03. 04.
     */
    @RequestMapping(value = "/posLogCollectMgmt/getSearchPosLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchPosLogList(PosLogCollectMgmtVO posLogCollectMgmtVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posLogCollectMgmtService.getSearchPosLogList(posLogCollectMgmtVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, posLogCollectMgmtVO);
    }

    /**
     * POS로그수집등록 팝업 - 조회
     *
     * @param   posLogCollectMgmtVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 03. 04.
     */
    @RequestMapping(value = "/posLogCollectMgmt/getSearchStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchStoreList(PosLogCollectMgmtVO posLogCollectMgmtVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posLogCollectMgmtService.getSearchStoreList(posLogCollectMgmtVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, posLogCollectMgmtVO);
    }

    /**
     * POS로그수집등록 팝업 - POS로그 저장
     * @param   posLogCollectMgmtVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 03. 05.
     */
    @RequestMapping(value = "/posLogRegist/savePosLog.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosLog(@RequestBody PosLogCollectMgmtVO[] posLogCollectMgmtVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = posLogCollectMgmtService.savePosLog(posLogCollectMgmtVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
