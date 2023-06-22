package kr.co.solbipos.adi.etc.alimtalk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkService;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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
 * @Class Name : AlimtalkController.java
 * @Description : 맘스터치 > 기타관리 > 매출트레킹수신자목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.06.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/etc/alimtalk")
public class AlimtalkController {

    private final AlimtalkService alimtalkService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkController(AlimtalkService alimtalkService, SessionService sessionService, CmmCodeUtil cmmCodeUtil) {
        this.alimtalkService = alimtalkService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/alimtalk/view.sb", method = RequestMethod.GET)
    public String alimtalkView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> alimtalkFgList = alimtalkService.getAlimtalkFgList(sessionInfoVO);
        model.addAttribute("alimtalkFgListAll", alimtalkFgList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(alimtalkFgList, "name", "value", UseYn.N));
        alimtalkFgList.remove(0);
        model.addAttribute("alimtalkFgList", alimtalkFgList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(alimtalkFgList, "name", "value", UseYn.N));

        return "adi/etc/alimtalk/alimtalk";
    }

    /**
     * 시스템 명칭관리 - 조회
     *
     * @param alimtalkVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.06.15
     */
    @RequestMapping(value = "/alimtalk/getAlimtalkList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkList(HttpServletRequest request, HttpServletResponse response,
                                        AlimtalkVO alimtalkVO, Model model) {

        // 세션정보 설정
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = alimtalkService.getAlimtalkList(alimtalkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, alimtalkVO);
    }

    /**
     * 시스템 명칭관리 - 저장
     *
     * @param alimtalkVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.06.15
     */
    @RequestMapping(value = "/alimtalk/getAlimtalkSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSave(@RequestBody AlimtalkVO[] alimtalkVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkService.getAlimtalkSave(alimtalkVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}