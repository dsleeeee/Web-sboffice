package kr.co.solbipos.adi.sms.smsTelNoManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
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

/**
 * @Class Name : SmsTelNoManageController.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsTelNoManage")
public class SmsTelNoManageController {

    private final SessionService sessionService;
    private final SmsTelNoManageService smsTelNoManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsTelNoManageController(SessionService sessionService, SmsTelNoManageService smsTelNoManageService) {
        this.sessionService = sessionService;
        this.smsTelNoManageService = smsTelNoManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsTelNoManage/list.sb", method = RequestMethod.GET)
    public String smsTelNoManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsTelNoManage/smsTelNoManage";
    }

    /**
     * 발신번호관리 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 09. 15.
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoManageList(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsTelNoManageService.getSmsTelNoManageList(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /**
     * 발신번호관리 - 발신번호 등록 요청 저장
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.10.14
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoManageSave(@RequestBody SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsTelNoManageSave(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoRegisterRequest.sb", method = RequestMethod.GET)
    public String getSmsTelNoRegister(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("결과값 받음");
        System.out.println("res_cd : " + request.getParameterValues("res_cd"));
        System.out.println("res_msg : " + request.getParameterValues("res_msg"));

        System.out.println("cert_no : " + request.getParameterValues("cert_no"));
        System.out.println("ordr_idxx : " + request.getParameterValues("ordr_idxx"));
        System.out.println("enc_cert_data2 : " + request.getParameterValues("enc_cert_data2"));
        System.out.println("up_hash : " + request.getParameterValues("up_hash"));
        System.out.println("dn_hash : " + request.getParameterValues("dn_hash"));

        System.out.println("phone_no : " + request.getParameterValues("phone_no"));
        System.out.println("comm_id : " + request.getParameterValues("comm_id"));
        System.out.println("user_name : " + request.getParameterValues("user_name"));
        System.out.println("birth_day : " + request.getParameterValues("birth_day"));
        System.out.println("sex_code : " + request.getParameterValues("sex_code"));
        System.out.println("local_code : " + request.getParameterValues("local_code"));
        System.out.println("ci : " + request.getParameterValues("ci"));
        System.out.println("di : " + request.getParameterValues("di"));
        System.out.println("ci_url : " + request.getParameterValues("ci_url"));
        System.out.println("di_url : " + request.getParameterValues("di_url"));
        System.out.println("web_siteid : " + request.getParameterValues("web_siteid"));

        if( request.getParameterValues("res_cd").equals( "0000" ) ){
            SmsTelNoManageVO smsTelNoManageVO = new SmsTelNoManageVO();
            smsTelNoManageVO.setCertId(request.getParameterValues("cert_no").toString());
            smsTelNoManageVO.setTelNo(request.getParameterValues("phone_no").toString());

            int result = smsTelNoManageService.getSmsTelNoManageUpdate(smsTelNoManageVO, sessionInfoVO);
        }
        model.addAttribute("res_cd", request.getParameterValues("res_cd").toString());

        return "adi/sms/smsSend/smsTelNoRequest";
    }

    /**
     * 발신번호관리 저장
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 09. 15.
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoManageSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoManageSaveUpdate(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsTelNoManageSaveUpdate(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}