package kr.co.solbipos.sys.admin.logSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.sys.admin.logSend.service.LogSendService;
import kr.co.solbipos.sys.admin.logSend.service.LogSendVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : LogSendController.java
 * @Description : 시스템관리 > 관리자기능 > POS 시스템 로그 송신
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.25  이다솜      최초생성
 *
 * @author 솔비포스 백엔드 pt 이다솜
 * @since 2020. 08. 25
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sys/admin/logSend")
public class LogSendController {

    private final SessionService sessionService;
    private final LogSendService logSendService;

    /**
     * Constructor Injection
     */
    @Autowired
    public LogSendController(SessionService sessionService, LogSendService logSendService) {
        this.sessionService = sessionService;
        this.logSendService = logSendService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/logSend/list.sb", method = RequestMethod.GET)
    public String logSendView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin/logSend/logSend";
    }

    /**
     * 매장별 포스목록 조회
     * @param   logSendVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2020.08.27
     */
    @RequestMapping(value = "/logSend/getPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosList(LogSendVO logSendVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = logSendService.getPosList(logSendVO, sessionInfoVO);

        return returnListJson(Status.OK, list, logSendVO);
    }

    /**
     * [POS-DB] 간 로그 송신 구분을 등록
     * @param logSendVOs
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2020.08.27
     */
    @RequestMapping(value = "/logSend/updateLogSend.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateLogSend(@RequestBody LogSendVO[] logSendVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{

            result = logSendService.updateLogSend(logSendVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }



}
