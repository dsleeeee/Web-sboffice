package kr.co.solbipos.kookmin.workStudent.workStudent.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangVO;
import kr.co.solbipos.kookmin.workStudent.workStudent.service.WorkStudentKookminService;
import kr.co.solbipos.kookmin.workStudent.workStudent.service.WorkStudentKookminVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
/**
 * @Class Name  : WorkStudentKookminController.java
 * @Description : 국민대 > 근로학생관리 > 근로학생관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/workStudent/workStudent")
public class WorkStudentKookminController {

    private final SessionService sessionService;
    private final WorkStudentKookminService workStudentKookminService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkStudentKookminController(SessionService sessionService, WorkStudentKookminService workStudentKookminService) {
        this.sessionService = sessionService;
        this.workStudentKookminService = workStudentKookminService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workStudent/view.sb", method = RequestMethod.GET)
    public String workStudent(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workStudent/workStudentKookmin";
    }

    /**
     * 근로학생관리 조회
     *
     * @param   workStudentKookminVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 04.
     */
    @RequestMapping(value = "/workStudent/getWorkStudentKookminList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkStudentKookminList(WorkStudentKookminVO workStudentKookminVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = workStudentKookminService.getWorkStudentKookminList(workStudentKookminVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workStudentKookminVO);
    }

    /**
     * 근로학생관리 저장
     * @param   workStudentKookminVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 04.
     * @return
     */
    @RequestMapping(value = "/workStudent/saveWorkStudent.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWorkStudent(@RequestBody WorkStudentKookminVO[] workStudentKookminVOs, HttpServletRequest request,
                                  HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workStudentKookminService.saveWorkStudent(workStudentKookminVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 근로학생관리 엑셀업로드
     * @param   workStudentKookminVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 04.
     * @return
     */
    @RequestMapping(value = "/workStudent/saveWorkStudentExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWorkStudentExcelUpload(@RequestBody WorkStudentKookminVO[] workStudentKookminVOs, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workStudentKookminService.saveWorkStudentExcelUpload(workStudentKookminVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
