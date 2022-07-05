package kr.co.solbipos.adi.alimtalk.alimtalkTemplateTab.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateService;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeService;
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
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : AlimtalkTemplateTabController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡템플릿(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkTemplateTab")
public class AlimtalkTemplateTabController {

    private final SessionService sessionService;
    private final AlimtalkTemplateService alimtalkTemplateService; // 알림톡 템플릿관리
    private final AlimtalkSendTypeService alimtalkSendTypeService; // 알림톡 전송유형

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkTemplateTabController(SessionService sessionService, AlimtalkTemplateService alimtalkTemplateService, AlimtalkSendTypeService alimtalkSendTypeService) {
        this.sessionService = sessionService;
        this.alimtalkTemplateService = alimtalkTemplateService; // 알림톡 템플릿관리
        this.alimtalkSendTypeService = alimtalkSendTypeService; // 알림톡 전송유형
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/alimtalkTemplateTab/list.sb", method = RequestMethod.GET)
    public String alimtalkTemplateTabView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        AlimtalkTemplateVO alimtalkTemplateVO = new AlimtalkTemplateVO();

        // 알림톡 템플릿등록 팝업 - 전체 #{변수} 조회
        List<DefaultMap<String>> alimtalkTemplateParamsColList = alimtalkTemplateService.getAlimtalkTemplateParamsColList(alimtalkTemplateVO, sessionInfoVO);
        model.addAttribute("alimtalkTemplateParamsColList", alimtalkTemplateParamsColList);

        // 알림톡 계정등록 팝업 - 키값 리스트 조회
        List<DefaultMap<String>> alimtalkKeyColList = alimtalkSendTypeService.getAlimtalkKeyColList(sessionInfoVO);

        String appKey = alimtalkKeyColList.get(0).getStr("appKey");
        String secretKey = alimtalkKeyColList.get(0).getStr("secretKey");
        String apiUrl = alimtalkKeyColList.get(0).getStr("apiUrl");

        model.addAttribute("appKey", appKey);
        model.addAttribute("secretKey", secretKey);
        model.addAttribute("apiUrl", apiUrl);

        System.out.println("WEB_ALIMTALK >>> appKey : " + appKey);
        System.out.println("WEB_ALIMTALK >>> secretKey : " + secretKey);
        System.out.println("WEB_ALIMTALK >>> apiUrl : " + apiUrl);

        return "adi/alimtalk/alimtalkTemplateTab/alimtalkTemplateTab";
    }

}