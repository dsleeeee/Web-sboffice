package kr.co.solbipos.adi.controller.dclz.dclzmanage;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage;
import kr.co.solbipos.adi.enums.DclzInFg;
import kr.co.solbipos.adi.service.dclz.dclzmanage.DclzManageService;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.JsonException;
import kr.co.solbipos.service.message.MessageService;
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

    @Autowired
    MessageService messageService;

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

        // 선택한 매장을 arr 로 세팅한다. 쿼리에서 쓰임
        dclzManage.setArrStoreCd(dclzManage.getStoreCd().split(","));

        List<DefaultMap<Object>> list = dclzManageService.selectDclzManage(dclzManage);

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

        dclzManage.setPosNo("01");
        dclzManage.setInFg(DclzInFg.WEB);
        String empInDt = dclzManage.getEmpInDt();
        dclzManage.setEmpInDate(empInDt.substring(0, 8));

        // 해당 근무일에 근태가 있는지 확인
        int check = dclzManageService.selectWorkCheck(dclzManage);

        if (check > 0) {
            String arg[] = {dclzManage.getEmpInDate()};
            String msg = messageService.get("msg.dclz.empty.emp", arg);
            // 해당 사원의 {0}일의 근태가 존재합니다.
            return genJsonResultMsg(Status.FAIL, msg);
        }

        dclzManage.setRegDt(currentDateTimeString());
        dclzManage.setModDt(currentDateTimeString());
        dclzManage.setRegId(si.getUserId());
        dclzManage.setModId(si.getUserId());
        dclzManage.setWorkTime(calWorkMinute(dclzManage.getEmpInDt(), dclzManage.getEmpOutDt()));

        int result = dclzManageService.insertDclzManage(dclzManage);
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
        dclzManage.setModDt(currentDateTimeString());
        dclzManage.setModId(si.getUserId());

        // 해당 근무일에 근태가 있는지 확인
        int check = dclzManageService.selectWorkCheck(dclzManage);

        if (check == 0) {
            String arg[] = {dclzManage.getEmpInDate()};
            String msg = messageService.get("msg.dclz.empty.dclz", arg);
            // 해당 사원의 {0}일의 근태가 존재하지 않습니다.
            return genJsonResultMsg(Status.FAIL, msg);
        }

        dclzManage.setWorkTime(calWorkMinute(dclzManage.getEmpInDt(), dclzManage.getEmpOutDt()));

        int result = dclzManageService.updateDclzManage(dclzManage);
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
        List<DefaultMap<Object>> list = dclzManageService.selectStoreEmployee(dclzManage);
        return returnJson(Status.OK, list);
    }

    /**
     * 출근일시 퇴근일시로 근무시간을 계산
     * 
     * @param startDt YYYYMMDDHHmm 형태의 출근일시
     * @param endDt YYYYMMDDHHmm 형태의 퇴근일시
     * @return 분단위의 근무시간<br>
     *         0 아래의 값은 잘못된값임<br>
     */
    public long calWorkMinute(String startDt, String endDt) {

        long workMinute = 0;

        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);

            Date d1 = f.parse(endDt);
            Date d2 = f.parse(startDt);

            long diff = d1.getTime() - d2.getTime();
            workMinute = diff / 60000;

        } catch (Exception e) {
            String msg = messageService.get("label.registFail");
            throw new JsonException(Status.FAIL, msg, "");
        }
        // 퇴근일시 또는 출근일시가 잘못 선택 되었습니다.
        if (workMinute < 0) {
            String msg = messageService.get("msg.dclz.wrong.date");
            throw new JsonException(Status.FAIL, msg, "");
        }
        return workMinute;
    }
}


