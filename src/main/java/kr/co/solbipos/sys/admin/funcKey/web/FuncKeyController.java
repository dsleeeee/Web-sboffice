package kr.co.solbipos.sys.admin.funcKey.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.funcKey.service.FuncKeyService;
import kr.co.solbipos.sys.admin.funcKey.service.FuncKeyVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : FuncKeyController.java
 * @Description : 시스템관리 > 관리자기능 > 기능키적용버전등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.09  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.09
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin/funcKey")
public class FuncKeyController {

    private final SessionService sessionService;
    private final FuncKeyService funcKeyService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    public FuncKeyController(SessionService sessionService, FuncKeyService funcKeyService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.funcKeyService = funcKeyService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  김유승
     * @since   2024. 01. 09.
     */
    @RequestMapping(value="/funcKey/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model){
        // 기능구분 그룹코드 조회(콤보박스용)
        model.addAttribute("nmcodeGrpCdList", cmmCodeUtil.assmblObj(funcKeyService.getNmcodeGrpCdList(), "name", "value", UseYn.ALL));
        return "sys/admin/funcKey/funcKey";
    }

    /**
     * 기능키적용버전 리스트 조회
     * @param funcKeyVO
     * @param request
     * @return
     * @author  김유승
     * @since   2024. 01. 09.
     */
    @RequestMapping(value = "/getFuncKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFuncKeyList(FuncKeyVO funcKeyVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = funcKeyService.getFuncKeyList(funcKeyVO, sessionInfoVO);

        return returnListJson(Status.OK, list, funcKeyVO);
    }

    /**
     * 기능키적용버전 저장
     * @param funcKeyVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024. 01. 09.
     */
    @RequestMapping(value = "/saveFuncKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveFuncKey(@RequestBody FuncKeyVO[] funcKeyVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = funcKeyService.saveFuncKey(funcKeyVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 기능키적용버전 삭제
     * @param funcKeyVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김유승
     * @since   2024. 01. 09.
     */
    @RequestMapping(value = "/deleteFuncKey.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteFuncKey(@RequestBody FuncKeyVO[] funcKeyVOs, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = funcKeyService.deleteFuncKey(funcKeyVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
