package kr.co.solbipos.adi.dclz.dclzmanage.web;

import static kr.co.common.utils.grid.ReturnUtil.*;
import static org.springframework.util.ObjectUtils.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
 * @Class Name : DclzManageController.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
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

    @Autowired
    DclzManageService dclzManageService;

    @Autowired
    SessionService sessionService;

    /**
     * 근태 관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String dclzManage(HttpServletRequest request, HttpServletResponse response,
            Model model) {
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

        SessionInfoVO si = sessionService.getSessionInfo(request);

        // 가맹점일 경우에 세션에 저장된 세팅
        if(si.getOrgnFg() == OrgnFg.STORE) {
            dclzManageVO.setStoreCd(si.getOrgnCd());
        }

        if(!isEmpty(dclzManageVO.getStoreCd())) {
            // 선택한 매장을 arr 로 세팅한다. 쿼리에서 쓰임
            dclzManageVO.setArrStoreCd(dclzManageVO.getStoreCd().split(","));
        }

        List<DefaultMap<String>> list = dclzManageService.selectDclzManage(dclzManageVO);

        return returnListJson(Status.OK, list, dclzManageVO);
    }

    /**
     * 근태 등록
     *
     * @param dclzManage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "regist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageRegist(DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        String empInDt = dclzManageVO.getEmpInDt();
        dclzManageVO.setEmpInDate(empInDt.substring(0, 8));

        int result = dclzManageService.insertDclzManage(dclzManageVO, si.getUserId());

        return returnJson(Status.OK, result);
    }

    /**
     * 근태 삭제
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageRemove(DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        int result = dclzManageService.deleteDclzManage(dclzManageVO);
        return returnJson(Status.OK, result);
    }

    /**
     * 근태 수정
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageModify(DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO si = sessionService.getSessionInfo(request);

        String empInDt = dclzManageVO.getEmpInDt();
        dclzManageVO.setEmpInDate(empInDt.substring(0, 8));

        int result = dclzManageService.updateDclzManage(dclzManageVO, si.getUserId());
        return returnJson(Status.OK, result);
    }


    /**
     * 임직원 조회 > 근태 등록시에 해당되는 매장의 근태 등록 가능한 임직원 목록을 조회
     *
     * @param dclzManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "employee.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result employee(DclzManageVO dclzManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        List<DefaultMap<String>> list = dclzManageService.selectStoreEmployee(dclzManageVO);
        return returnJson(Status.OK, list);
    }
}
