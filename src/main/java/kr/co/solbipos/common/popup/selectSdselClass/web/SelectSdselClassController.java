package kr.co.solbipos.common.popup.selectSdselClass.web;

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
import kr.co.solbipos.common.popup.selectSdselClass.service.SelectSdselClassService;
import kr.co.solbipos.common.popup.selectSdselClass.service.SelectSdselClassVO;
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
 * @Class Name : SelectSdselClassController.java
 * @Description : (공통) 선택분류 팝업
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
@RequestMapping("/common/popup/selectSdselClass")
public class SelectSdselClassController {

    private final SessionService sessionService;
    private final SelectSdselClassService selectSdselClassService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectSdselClassController(SessionService sessionService, SelectSdselClassService selectSdselClassService) {
        this.sessionService = sessionService;
        this.selectSdselClassService = selectSdselClassService;
    }

    /**
     * 선택분류 공통 - 선택분류 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   selectSdselClassVO
     * @return  String
     * @author  김설아
     * @since   2023. 09. 11.
     */
    @RequestMapping(value = "/getSelectSdselClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectSdselClassList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, SelectSdselClassVO selectSdselClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = selectSdselClassService.getSelectSdselClassList(selectSdselClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectSdselClassVO);
    }
}