package kr.co.solbipos.membr.reportKwu.instructorMembr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrService;
import kr.co.solbipos.membr.reportKwu.instructorMembr.service.InstructorMembrVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : InstructorMembrController.java
 * @Description : 광운대 > 리포트 > 강사별회원관리내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.19  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/membr/reportKwu/instructorMembr")
public class InstructorMembrController {
    private final SessionService sessionService;
    private final InstructorMembrService instructorMembrService;

    public InstructorMembrController(SessionService sessionService, InstructorMembrService instructorMembrService){
        this.sessionService = sessionService;
        this.instructorMembrService = instructorMembrService;
    }

    /**
     * 강사별회원관리내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.09.19
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model, InstructorMembrVO instructorMembrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 명칭 콤보조회 - 강사명 : 139
        instructorMembrVO.setNmcodeGrpCd("139");
        model.addAttribute("teacherCd", convertToJson(instructorMembrService.selectHqCodeList(instructorMembrVO, sessionInfoVO)));

        // 본사 명칭 콤보조회 - 강습구분 : 140
        instructorMembrVO.setNmcodeGrpCd("140");
        model.addAttribute("classFg", convertToJson(instructorMembrService.selectHqCodeList(instructorMembrVO, sessionInfoVO)));

        return "membr/reportKwu/instructorMembr/instructorMembr";
    }

    /**
     * 강사별회원관리내역 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param instructorMembrVO
     * @return
     * @author  이다솜
     * @since   2022.09.19
     */
    @RequestMapping(value = "/getInstructorMembrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstructorMembrList(HttpServletRequest request, HttpServletResponse response, Model model, InstructorMembrVO instructorMembrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = instructorMembrService.getInstructorMembrList(instructorMembrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, instructorMembrVO);
    }
}
