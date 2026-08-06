package kr.co.solbipos.adi.sms.smsUserRegist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.kcp.CT_CLI;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistService;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistVO;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.adi.sms.smsTelNoManage.web.SmsTelNoManageController.*;

/**
 * @Class Name : SmsUserRegistController.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.31
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsUserRegist")
public class SmsUserRegistController {

    /** 본인인증 결과 콜백(KCP) URL */
    // TODO: 로컬 테스트용으로 임시 변경함 - 배포 전 아래 운영주소로 반드시 되돌릴 것!!
    // private static final String VERIFY_RET_URL = "https://neo.solbipos.com/adi/sms/smsUserRegist/smsUserRegist/updateVerify.sb";
    private static final String VERIFY_RET_URL = "http://localhost:8080/adi/sms/smsUserRegist/smsUserRegist/updateVerify.sb";

    private final SessionService sessionService;
    private final SmsUserRegistService smsUserRegistService;

    @Autowired
    public SmsUserRegistController(SessionService sessionService, SmsUserRegistService smsUserRegistService) {
        this.sessionService = sessionService;
        this.smsUserRegistService = smsUserRegistService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/smsUserRegist/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/smsUserRegist/smsUserRegist";
    }

    /**
     * 진입시 체크 - 세션 사용자의 등록정보 조회
     */
    @RequestMapping(value = "/smsUserRegist/getUserRegistInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRegistInfo(HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = smsUserRegistService.getUserRegistInfo(sessionInfoVO);

        if (result != null && result.getStr("telNo") != null && !result.getStr("telNo").isEmpty()) {
            result.put("telNo", maskTelNo(result.getStr("telNo")));
        }

        return returnJson(Status.OK, result);
    }

    /**
     * 본인인증 값 가져옴 (KCP 인증창 오픈용 파라미터)
     */
    @RequestMapping(value = "/smsUserRegist/getVerifyVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyVal(SmsUserRegistVO smsUserRegistVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);


        String ordrIdxx = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS").format(new Date());

        CT_CLI cc = new CT_CLI();

        String upHash = cc.makeHashData(ENC_KEY, SITE_CD +
                ordrIdxx +
                "" +
                "" +
                "00" +
                "00" +
                "00" +
                "" +
                ""
        );

        DefaultMap<String> result = new DefaultMap<>();
        result.put("site_cd", SITE_CD);
        result.put("web_siteid", WEB_SITEID);
        result.put("gw_url", GW_URL);
        result.put("Ret_URL", VERIFY_RET_URL);
        result.put("ordr_idxx", ordrIdxx);
        result.put("up_hash", upHash);
        result.put("sessionId", sessionInfoVO.getSessionId());

        return returnJson(Status.OK, result);
    }

    /**
     * 본인인증 결과 콜백 (KCP → 서버)
     */
    @RequestMapping(value = "/smsUserRegist/updateVerify.sb", method = RequestMethod.POST)
    public void updateVerify(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        String siteCd = request.getParameter("site_cd");
        String ordrIdxx = request.getParameter("ordr_idxx");
        String resCd = request.getParameter("res_cd");
        String certNo = request.getParameter("cert_no");
        String encCertData2 = request.getParameter("enc_cert_data2");
        String dnHash = request.getParameter("dn_hash");

        CT_CLI cc = new CT_CLI();

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if ("0000".equals(resCd)) {

            // dn_hash 검증 (위변조 방지)
            if (!cc.checkValidHash(ENC_KEY, dnHash, (siteCd + ordrIdxx + certNo))) {
                out.println("<script>window.opener.smsUserRegistVerifyCallback('fail', {reason: '위변조 의심 데이터입니다. 고객센터로 문의해주세요.'}); window.close();</script>");
                out.flush();
                return;
            }

            if (encCertData2 == null) {
                // 복호화할 인증데이터 자체가 없음 - 실패
                out.println("<script>window.opener.smsUserRegistVerifyCallback('fail', {reason: '인증 정보가 확인되지 않았습니다.'}); window.close();</script>");
                out.flush();
                return;
            }

            cc.decryptEncCert(ENC_KEY, siteCd, certNo, encCertData2);

            System.out.println("SUR : -------- 본인인증 복호화 결과 --------");
            System.out.println("SUR : phone_no : " + cc.getKeyValue("phone_no"));
            System.out.println("SUR : comm_id : " + cc.getKeyValue("comm_id"));
            System.out.println("SUR : user_name : " + cc.getKeyValue("user_name"));
            System.out.println("SUR : birth_day : " + cc.getKeyValue("birth_day"));
            System.out.println("SUR : sex_code : " + cc.getKeyValue("sex_code"));
            System.out.println("SUR : local_code : " + cc.getKeyValue("local_code"));
            System.out.println("SUR : foreigner_yn : " + cc.getKeyValue("foreigner_yn"));
            System.out.println("SUR : nation_info : " + cc.getKeyValue("nation_info"));
            System.out.println("SUR : ci : " + cc.getKeyValue("ci"));
            System.out.println("SUR : di : " + cc.getKeyValue("di"));
            System.out.println("SUR : ci_url : " + URLDecoder.decode(cc.getKeyValue("ci_url")));
            System.out.println("SUR : di_url : " + URLDecoder.decode(cc.getKeyValue("di_url")));
            System.out.println("SUR : web_siteid : " + cc.getKeyValue("web_siteid"));
            System.out.println("SUR : ------------------------------------");

            String di = cc.getKeyValue("di");

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

            SmsUserRegistVO smsUserRegistVO = new SmsUserRegistVO();
            smsUserRegistVO.setDi(di);

            String existingUserId = smsUserRegistService.getUserIdByDi(smsUserRegistVO);

            if (existingUserId != null && !existingUserId.equals(sessionInfoVO.getUserId())) {
                // 중복 - 이미 다른 계정에 등록된 DI
                System.out.println("SUR : 중복 - 이미 등록된 사용자아이디 : " + existingUserId);
                out.println("<script>window.opener.smsUserRegistVerifyCallback('duplicate', {dupUserId: '" + existingUserId + "'}); window.close();</script>");
                out.flush();
                return;
            }

            // 성공 - 신규 등록 저장
            String telNo = cc.getKeyValue("phone_no");

            SmsUserRegistVO saveVO = new SmsUserRegistVO();
            saveVO.setDi(di);
            saveVO.setTelNo(telNo);

            try {
                smsUserRegistService.saveUserRegist(saveVO, sessionInfoVO);

                System.out.println("SUR : 저장 완료 - 신규 등록");

                String userNm = cc.getKeyValue("user_name");
                String maskedTelNo = maskTelNo(telNo);
                String verifyDt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                out.println("<script>window.opener.smsUserRegistVerifyCallback('success', {userNm: '" + userNm + "', telNo: '" + maskedTelNo + "', verifyDt: '" + verifyDt + "'}); window.close();</script>");
                out.flush();

            } catch (Exception e) {
                System.out.println("SUR : 저장 중 오류 발생");
                e.printStackTrace();

                out.println("<script>window.opener.smsUserRegistVerifyCallback('error', {reason: '저장 중 오류가 발생했습니다. 다시 시도해주세요.'}); window.close();</script>");
                out.flush();
            }

        } else {
            out.println("<script>window.opener.smsUserRegistVerifyCallback('fail', {reason: '인증 시간이 초과되었거나 인증 정보가 확인되지 않았습니다.'}); window.close();</script>");
            out.flush();
        }
    }

    /**
     * SMS 사용등록 - SMS사용자 삭제
     *
     * @param   request
     * @param   response
     * @param   smsUserRegistVO
     * @param   model Model
     * @return  Result
     * @author  김유승
     * @since   2026. 08. 04.
     */
    @RequestMapping(value = "/smsUserRegist/deleteUserRegist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserRegist(@RequestBody SmsUserRegistVO smsUserRegistVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsUserRegistService.deleteUserRegist(smsUserRegistVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 전화번호 마스킹 (010-****-5821 형태) */
    private String maskTelNo(String telNo) {
        if (telNo == null || telNo.length() < 8) {
            return telNo;
        }
        int len = telNo.length();
        return telNo.substring(0, 3) + "-" + "****" + "-" + telNo.substring(len - 4);
    }
}
