package kr.co.solbipos.sys.admin.saleChk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.saleChk.service.SaleChkService;
import kr.co.solbipos.sys.admin.saleChk.service.SaleChkVO;
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
 * @Description : 시스템관리 > 관리자기능 > 매출점검
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.07  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sys/admin/saleChk")
public class SaleChkController {

    private final SessionService sessionService;
    private final SaleChkService saleChkService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleChkController(SessionService sessionService, SaleChkService saleChkService) {
        this.sessionService = sessionService;
        this.saleChkService = saleChkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleChk/list.sb", method = RequestMethod.GET)
    public String logSendView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin/saleChk/saleChk";
    }

    /**
     * 조회
     * @param   saleChkVO
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.03.07
     */
    @RequestMapping(value = "/saleChk/getSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosList(SaleChkVO saleChkVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = saleChkService.getSaleList(saleChkVO, sessionInfoVO);

        return returnListJson(Status.OK, list, saleChkVO);
    }

    /**
     * [POS-DB] 간 로그 송신 구분을 등록
     * @param saleChkVOs
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.03.07
     */
    @RequestMapping(value = "/saleChk/updateResultMsg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateResultMsg(@RequestBody SaleChkVO[] saleChkVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{

            result = saleChkService.updateResultMsg(saleChkVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }



}
