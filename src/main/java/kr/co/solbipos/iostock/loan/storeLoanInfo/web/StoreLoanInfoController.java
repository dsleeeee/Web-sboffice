package kr.co.solbipos.iostock.loan.storeLoanInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoService;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : StoreLoanController.java
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
@RequestMapping(value = "/iostock/loan/storeLoanInfo")
public class StoreLoanInfoController {
    
    private final SessionService sessionService;
    private final StoreLoanInfoService storeLoanInfoService;

    @Autowired
    public StoreLoanInfoController(SessionService sessionService, StoreLoanInfoService storeLoanInfoService) {
        this.sessionService = sessionService;
        this.storeLoanInfoService = storeLoanInfoService;
    }

    /**
     * 매장별여신상세현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 08. 20.
     */
    @RequestMapping(value = "/storeLoanInfo/view.sb", method = RequestMethod.GET)
    public String storeLoanInfoView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "iostock/loan/storeLoanInfo/storeLoanInfo";
    }

    /**
     * 매장별여신상세현황 - 조회
     * @param   request
     * @param   response
     * @param   storeLoanInfoVO
     * @param   model
     * @return  Result
     * @author  안동관
     * @since   2018. 08. 20.
     */
    
    @RequestMapping(value = "/storeLoanInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreLoanInfoList(HttpServletRequest request, HttpServletResponse response,
                                   StoreLoanInfoVO storeLoanInfoVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storeLoanInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storeLoanInfoService.getStoreLoanInfoList(storeLoanInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeLoanInfoVO);
    }
}
