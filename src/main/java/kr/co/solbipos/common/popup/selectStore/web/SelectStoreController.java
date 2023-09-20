package kr.co.solbipos.common.popup.selectStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreService;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SelectStoreController.java
 * @Description : (공통) 매장 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/common/popup/selectStore")
public class SelectStoreController {

    private final SessionService sessionService;
    private final SelectStoreService selectStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectStoreController(SessionService sessionService, SelectStoreService selectStoreService) {
        this.sessionService = sessionService;
        this.selectStoreService = selectStoreService;
    }

    /**
     * 매장 공통 - 매장 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 09. 11.
     */
    @RequestMapping(value = "/getSelectStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectStoreList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectStoreService.getSelectStoreList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 매장 공통 - 회사 구분 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 08. 11.
     */
    @RequestMapping(value = "/getSelectStoreCompanyFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectStoreCompanyFg(SelectStoreVO selectStoreVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = selectStoreService.getSelectStoreCompanyFg(selectStoreVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 사용자별 브랜드 콤보박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 09. 11.
     */
    @RequestMapping(value = "/getSelectBrandMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectBrandMomsList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectStoreService.getSelectBrandMomsList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 사용자별 코드별 공통코드 콤보박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 09. 11.
     */
    @RequestMapping(value = "/getSelectHqNmcodeMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectHqNmcodeMomsList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectStoreService.getSelectHqNmcodeMomsList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 사용자별 그룹 콤보박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 09. 11.
     */
    @RequestMapping(value = "/getSelectBranchMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectBranchMomsList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectStoreService.getSelectBranchMomsList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 업로드매장 공통 - 업로드매장 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 08. 11.
     */
    @RequestMapping(value = "/getSelectUploadStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectUploadStoreList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectStoreService.getSelectUploadStoreList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 업로드매장 공통 - 검증결과 저장
     *
     * @param selectStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 08. 11.
     */
    @RequestMapping(value = "/getSelectUploadStoreExcelUploadSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectUploadStoreExcelUploadSave(@RequestBody SelectStoreVO[] selectStoreVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = selectStoreService.getSelectUploadStoreExcelUploadSave(selectStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 업로드매장 공통 - 검증결과 전체 삭제
     *
     * @param selectStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 08. 11.
     */
    @RequestMapping(value = "/getSelectUploadStoreExcelUploadDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectUploadStoreExcelUploadDeleteAll(@RequestBody SelectStoreVO selectStoreVO, HttpServletRequest request,
                                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = selectStoreService.getSelectUploadStoreExcelUploadDeleteAll(selectStoreVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 업로드매장 공통 - 업로드매장 텍스트박스 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectStoreVO
     * @return  String
     * @author  김설아
     * @since   2023. 08. 11.
     */
    @RequestMapping(value = "/getSelectUploadStoreText.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectUploadStoreText(SelectStoreVO selectStoreVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = selectStoreService.getSelectUploadStoreText(selectStoreVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}