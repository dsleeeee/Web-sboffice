package kr.co.solbipos.adi.sms.smsTelNoManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.kcp.CT_CLI;
import kr.co.solbipos.adi.sms.smsSendTab.web.SmsSendTabController;
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
import java.net.URLDecoder;
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
     * 발신번호관리 - 발신번호 등록 요청 결과 저장
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.10.14
     */
    @RequestMapping(value = "/smsTelNoManage/getSmsTelNoRegisterRequest.sb", method = RequestMethod.POST)
    public String getSmsTelNoRegisterRequest(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("결과 : " + request.getQueryString());
        System.out.println("site_cd : " + request.getParameter("site_cd"));
        System.out.println("ordr_idxx : " + request.getParameter("ordr_idxx"));
        System.out.println("res_cd : " + request.getParameter("res_cd"));
        System.out.println("res_msg : " + request.getParameter("res_msg"));
        System.out.println("req_tx : " + request.getParameter("req_tx"));
        System.out.println("cert_no : " + request.getParameter("cert_no"));
        System.out.println("enc_cert_data2 : " + request.getParameter("enc_cert_data2"));
        System.out.println("up_hash : " + request.getParameter("up_hash"));
        System.out.println("dn_hash : " + request.getParameter("dn_hash"));

        String siteCd = request.getParameter("site_cd");
        String ordrIdxx = request.getParameter("ordr_idxx");
        String resCd = request.getParameter("res_cd");
        String resMsg = request.getParameter("res_msg");
        String reqTx = request.getParameter("req_tx");
        String certNo = request.getParameter("cert_no");
        String encCertData2 = request.getParameter("enc_cert_data2");
        String upHash = request.getParameter("up_hash");
        String dnHash = request.getParameter("dn_hash");

        CT_CLI cc = new CT_CLI();

        String result = "";
        if( resCd.equals( "0000" ) ){

            // dn_hash 검증
            // KCP 가 리턴해 드리는 dn_hash 와 사이트 코드, 요청번호 , 인증번호를 검증하여
            // 해당 데이터의 위변조를 방지합니다
            if(!cc.checkValidHash(SmsSendTabController.ENC_KEY, dnHash, (siteCd + ordrIdxx + certNo))){
                // 검증실패
                result = "-2";
            }

            // 인증데이터 복호화 함수
            // 해당 함수는 암호화된 enc_cert_data2 를
            // site_cd 와 cert_no 를 가지고 복화화 하는 함수 입니다.
            // 정상적으로 복호화 된경우에만 인증데이터를 가져올수 있습니다.
            cc.decryptEncCert( SmsSendTabController.ENC_KEY, siteCd, certNo, encCertData2 );

            System.out.println("-------- 복호화 결과 --------");
            System.out.println("phone_no : " + cc.getKeyValue("phone_no"));
            System.out.println("comm_id : " + cc.getKeyValue("comm_id"));
            System.out.println("user_name : " + cc.getKeyValue("user_name"));
            System.out.println("birth_day : " + cc.getKeyValue("birth_day"));
            System.out.println("sex_code : " + cc.getKeyValue("sex_code"));
            System.out.println("local_code : " + cc.getKeyValue("local_code"));
            System.out.println("ci : " + cc.getKeyValue("ci"));
            System.out.println("di : " + cc.getKeyValue("di"));
            System.out.println("ci_url : " + URLDecoder.decode(cc.getKeyValue("ci_url")));
            System.out.println("di_url : " + URLDecoder.decode(cc.getKeyValue("di_url")));
            System.out.println("web_siteid : " + cc.getKeyValue("web_siteid"));

            SmsTelNoManageVO smsTelNoManageVO = new SmsTelNoManageVO();
            smsTelNoManageVO.setCertId(ordrIdxx);
            smsTelNoManageVO.setTelNo(cc.getKeyValue("phone_no"));

           if(smsTelNoManageService.getSmsTelNoManageUpdate(smsTelNoManageVO, sessionInfoVO) == -1){
               // 기등록번호
               result = "-1";
           } else {
               // 정상등록
               result = "0";
           }
        }
        model.addAttribute("result", result);

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