package kr.co.solbipos.adi.controller.dclz.dclzmanage;

import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage;
import kr.co.solbipos.adi.service.dclz.dclzmanage.DclzManageService;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.user.OrgnFg;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;

/**
 * 부가서비스 > 근태 관리 > 근태 관리
 * 
 * @author 정용길
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
     * @param dclzManage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageList(DclzManage dclzManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        SessionInfo si = sessionService.getSessionInfo(request);
        
        // 가맹점일 경우에 세션에 저장된 세팅
        if(si.getOrgnFg() == OrgnFg.STORE) {
            dclzManage.setStoreCd(si.getOrgnCd());
        }
        
        // 선택한 매장을 arr 로 세팅한다. 쿼리에서 쓰임
        dclzManage.setArrStoreCd(dclzManage.getStoreCd().split(","));

        List<DefaultMap<String>> list = dclzManageService.selectDclzManage(dclzManage);

        return returnListJson(Status.OK, list, dclzManage);
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
    public Result dclzManageRegist(DclzManage dclzManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfo si = sessionService.getSessionInfo(request);

        String empInDt = dclzManage.getEmpInDt();
        dclzManage.setEmpInDate(empInDt.substring(0, 8));

        int result = dclzManageService.insertDclzManage(dclzManage, si.getUserId());
        
        return returnJson(Status.OK, result);
    }

    /**
     * 근태 삭제
     * 
     * @param dclzManage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageRemove(DclzManage dclzManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        int result = dclzManageService.deleteDclzManage(dclzManage);
        return returnJson(Status.OK, result);
    }

    /**
     * 근태 수정
     * 
     * @param dclzManage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dclzManageModify(DclzManage dclzManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfo si = sessionService.getSessionInfo(request);

        String empInDt = dclzManage.getEmpInDt();
        dclzManage.setEmpInDate(empInDt.substring(0, 8));

        int result = dclzManageService.updateDclzManage(dclzManage, si.getUserId());
        return returnJson(Status.OK, result);
    }


    /**
     * 임직원 조회 > 근태 등록시에 해당되는 매장의 근태 등록 가능한 임직원 목록을 조회
     * 
     * @param dclzManage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "employee.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result employee(DclzManage dclzManage, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        List<DefaultMap<String>> list = dclzManageService.selectStoreEmployee(dclzManage);
        return returnJson(Status.OK, list);
    }
}