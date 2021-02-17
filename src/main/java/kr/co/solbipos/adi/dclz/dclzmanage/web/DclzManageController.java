package kr.co.solbipos.adi.dclz.dclzmanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DclzManageController.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/dclz/dclzmanage/dclzmanage/")
public class DclzManageController {

    private final DclzManageService dclzManageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public DclzManageController(DclzManageService dclzManageService, SessionService sessionService) {
        this.dclzManageService = dclzManageService;
        this.sessionService = sessionService;
    }

    /**
     * 근태 관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.GET)
    public String dclzManage(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        DclzManageVO dclzManageVO = new DclzManageVO();

        // 매장 사원 목록 조회 (매장권한만 근태등록 가능)
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            List<DefaultMap<String>> list = dclzManageService.selectStoreEmployee(dclzManageVO, sessionInfoVO);
            model.addAttribute("empList", convertToJson(list));
        }

        return "adi/dclz/dclzmanage/dclzManage";
    }

    /**
     * 근태 관리 리스트 조회
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageList(DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dclzManageService.selectDclzManage(dclzManageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, dclzManageVO);
    }

    /**
     * 근태등록
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageRegist(@RequestBody DclzManageVO dclzManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dclzManageService.insertDclzManage(dclzManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 근태수정
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageModify(@RequestBody DclzManageVO dclzManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dclzManageService.updateDclzManage(dclzManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 근태삭제
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageRemove(@RequestBody DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dclzManageService.deleteDclzManage(dclzManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 근태상세정보
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageDetail(DclzManageVO dclzManageVO, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        DefaultMap<String> result = dclzManageService.selectDclzManageDtl(dclzManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
