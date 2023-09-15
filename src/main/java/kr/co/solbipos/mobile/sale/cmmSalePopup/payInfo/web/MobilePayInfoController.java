package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoService;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoVO;
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
 * @Class Name : MobilePayInfoController.java
 * @Description : (모바일) 공통 결제수단 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/cmmSalePopup/payInfo")
public class MobilePayInfoController {

    private final SessionService sessionService;
    private final MobilePayInfoService mobilePayInfoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobilePayInfoController(SessionService sessionService, MobilePayInfoService mobilePayInfoService) {
        this.sessionService = sessionService;
        this.mobilePayInfoService = mobilePayInfoService;
    }

    /**
     * 가승인 팝업 - 조회
     *
     * @param mobilePayInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 09. 13.
     */
    @RequestMapping(value = "/mobileTemporary/getMobileTemporaryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobilePayInfoService.getMobileTemporaryList(mobilePayInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobilePayInfoVO);
    }
}