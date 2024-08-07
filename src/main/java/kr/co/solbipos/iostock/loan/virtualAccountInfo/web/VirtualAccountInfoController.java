package kr.co.solbipos.iostock.loan.virtualAccountInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.virtualAccountInfo.service.VirtualAccountInfoService;
import kr.co.solbipos.iostock.loan.virtualAccountInfo.service.VirtualAccountInfoVO;
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
 * @Class Name : VirtualAccountInfoController.java
 * @Description : 수불관리 > 주문관리 > 가상계좌-기초정보등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/loan/virtualAccountInfo")
public class VirtualAccountInfoController {

    private final SessionService sessionService;
    private final VirtualAccountInfoService virtualAccountInfoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public VirtualAccountInfoController(SessionService sessionService, VirtualAccountInfoService virtualAccountInfoService) {
        this.sessionService = sessionService;
        this.virtualAccountInfoService = virtualAccountInfoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/virtualAccountInfo/list.sb", method = RequestMethod.GET)
    public String virtualAccountInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "iostock/loan/virtualAccountInfo/virtualAccountInfo";
    }

    /**
     * 가상계좌-기초정보등록 - 조회
     *
     * @param virtualAccountInfoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 08. 06.
     */
    @RequestMapping(value = "/virtualAccountInfo/getVirtualAccountInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualAccountInfoList(VirtualAccountInfoVO virtualAccountInfoVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = virtualAccountInfoService.getVirtualAccountInfoList(virtualAccountInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, virtualAccountInfoVO);
    }

    /**
     * 가상계좌-기초정보등록 - 저장
     *
     * @param virtualAccountInfoVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 08. 06.
     */
    @RequestMapping(value = "/virtualAccountInfo/getVirtualAccountInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailSave(@RequestBody VirtualAccountInfoVO[] virtualAccountInfoVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = virtualAccountInfoService.getVirtualAccountInfoSave(virtualAccountInfoVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}