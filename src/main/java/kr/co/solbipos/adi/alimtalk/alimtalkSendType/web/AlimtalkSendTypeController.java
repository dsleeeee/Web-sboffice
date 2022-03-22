package kr.co.solbipos.adi.alimtalk.alimtalkSendType.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeVO;
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
 * @Class Name : AlimtalkSendTypeController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송유형
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkSendType")
public class AlimtalkSendTypeController {

    private final SessionService sessionService;
    private final AlimtalkSendTypeService alimtalkSendTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendTypeController(SessionService sessionService, AlimtalkSendTypeService alimtalkSendTypeService) {
        this.sessionService = sessionService;
        this.alimtalkSendTypeService = alimtalkSendTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/alimtalkSendType/list.sb", method = RequestMethod.GET)
    public String alimtalkSendTypeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/alimtalk/alimtalkSendType/alimtalkSendType";
    }

    /**
     * 알림톡 전송유형 - 계정정보 체크 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 17.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkIdRegisterChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkIdRegisterChk(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkIdRegisterChk(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 전송유형 - 전송유형 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 전송유형 - 전송유형 상세 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 전송유형 - 전송유형 상세 저장
     *
     * @param alimtalkSendTypeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailSave(@RequestBody AlimtalkSendTypeVO[] alimtalkSendTypeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkSendTypeService.getAlimtalkSendTypeDetailSave(alimtalkSendTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 전송유형 - 템플릿 상세 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailTemplate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplate(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplate(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 전송유형 - 템플릿 상세 저장
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailTemplateSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplateSave(@RequestBody AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplateSave(alimtalkSendTypeVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 템플릿 선택변경 팝업 - 템플릿 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 15.
     */
    @RequestMapping(value = "/templatePopup/getAlimtalkSendTypeDetailTemplateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplateList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplateList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 템플릿 계정등록 팝업 - 사업자 카테고리 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 21.
     */
    @RequestMapping(value = "/alimtalkIdRegister/getCategoryCodeComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoComboList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getCategoryCodeComboList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }
}