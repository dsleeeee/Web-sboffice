package kr.co.solbipos.adi.sms.smsTelNoManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.kcp.CT_CLI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

//        https로만 결과값 전송이 가능한데 개발서버는 http라 테스트 불가능
//        SITE_CD = "S6186";
//        WEB_SITEID = "";
//        ENC_KEY = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0";
//        RET_URL = "https://192.168.0.85:10001/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
//        RET_URL      = "https://neo.solbipos.com/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
//        GW_URL = "https://testcert.kcp.co.kr/kcp_cert/cert_view.jsp";

    public static final  String SITE_CD         = "AGSVU";
    public static final  String WEB_SITEID      = "J21101407426";
    public static final  String ENC_KEY         = "beba66643a50ad06b9bd92b6bcf6239d8199071bc8ffd361a81441f651f8efd2";
    // 발신번호 결과 URL
    public static final  String RET_URL         = "https://neo.solbipos.com/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
    // 본인인증 결과 URL
    public static final  String VERIFY_RET_URL  = "https://neo.solbipos.com/adi/sms/marketingSmsSend/marketingSmsSend/updateVerify.sb";
    public static final  String GW_URL          = "https://cert.kcp.co.kr/kcp_cert/cert_view.jsp";

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
    public void getSmsTelNoRegisterRequest(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        SmsTelNoManageVO smsTelNoManageVO = new SmsTelNoManageVO();

        System.out.println("JH : 결과 : " + request.getQueryString());
        System.out.println("JH : site_cd : " + request.getParameter("site_cd"));
        System.out.println("JH : ordr_idxx : " + request.getParameter("ordr_idxx"));
        System.out.println("JH : res_cd : " + request.getParameter("res_cd"));
        System.out.println("JH : res_msg : " + request.getParameter("res_msg"));
        System.out.println("JH : req_tx : " + request.getParameter("req_tx"));
        System.out.println("JH : cert_no : " + request.getParameter("cert_no"));
        System.out.println("JH : enc_cert_data2 : " + request.getParameter("enc_cert_data2"));
        System.out.println("JH : up_hash : " + request.getParameter("up_hash"));
        System.out.println("JH : dn_hash : " + request.getParameter("dn_hash"));

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

        smsTelNoManageVO.setCertId(ordrIdxx);
        smsTelNoManageVO.setResCd(resCd);
        smsTelNoManageVO.setTelNo(cc.getKeyValue("phone_no"));

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String result = "";
        if( resCd.equals( "0000" ) ){

            // dn_hash 검증
            // KCP 가 리턴해 드리는 dn_hash 와 사이트 코드, 요청번호 , 인증번호를 검증하여
            // 해당 데이터의 위변조를 방지합니다
            if(!cc.checkValidHash(ENC_KEY, dnHash, (siteCd + ordrIdxx + certNo))){
                // 검증실패
                result = "-2";
                out.println("<script>window.resizeTo(800,500);alert('검증에 실패하였습니다.<br>위변조된 데이터로 의심됩니다.<br>고객센터로 문의해주세요.'); window.close();</script>");
                out.flush();
            }

            if(encCertData2 != null){
                // 인증데이터 복호화 함수
                // 해당 함수는 암호화된 enc_cert_data2 를
                // site_cd 와 cert_no 를 가지고 복화화 하는 함수 입니다.
                // 정상적으로 복호화 된경우에만 인증데이터를 가져올수 있습니다.
                cc.decryptEncCert( ENC_KEY, siteCd, certNo, encCertData2 );

                System.out.println("JH : -------- 복호화 결과 --------");
                System.out.println("JH : phone_no : " + cc.getKeyValue("phone_no"));
                System.out.println("JH : comm_id : " + cc.getKeyValue("comm_id"));
                System.out.println("JH : user_name : " + cc.getKeyValue("user_name"));
                System.out.println("JH : birth_day : " + cc.getKeyValue("birth_day"));
                System.out.println("JH : sex_code : " + cc.getKeyValue("sex_code"));
                System.out.println("JH : local_code : " + cc.getKeyValue("local_code"));
                System.out.println("JH : ci : " + cc.getKeyValue("ci"));
                System.out.println("JH : di : " + cc.getKeyValue("di"));
                System.out.println("JH : ci_url : " + URLDecoder.decode(cc.getKeyValue("ci_url")));
                System.out.println("JH : di_url : " + URLDecoder.decode(cc.getKeyValue("di_url")));
                System.out.println("JH : web_siteid : " + cc.getKeyValue("web_siteid"));

                System.out.println("---------------------------");
            }

            if(smsTelNoManageService.getSmsTelNoManageChk(smsTelNoManageVO, sessionInfoVO) != 0){
                // 기등록번호
                out.println("<script>window.resizeTo(800,500);alert('기존에 등록된 전화번호입니다.'); window.close();</script>");
                out.flush();
            } else {
                if(smsTelNoManageService.getSmsTelNoManageUpdate(smsTelNoManageVO, sessionInfoVO) == 1){

                    // 정상등록
                    out.println("<script>window.resizeTo(800,500);alert('정상등록되었습니다.'); window.close(); window.opener.location.reload(); </script>");
                    out.flush();
                } else {

                    // 인증성공 + DB저장실패
                    out.println("<script>window.resizeTo(800,500);alert('본인인증에 성공했으나 저장에 문제가 있습니다.<br>고객센터로 문의해주세요.'); window.close();</script>");
                    out.flush();
                }
            }
        } else {
            smsTelNoManageService.getSmsTelNoManageUpdate(smsTelNoManageVO, sessionInfoVO);

            out.println("<script>window.resizeTo(800,500);alert('본인인증 에러가 발생하였습니다.<br>고객센터로 문의해주세요.'); window.close();</script>");
            out.flush();
        }
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

    /**
     * 값 가져감
     *
     * @param
     * @param
     * @param
     * @param
     * @return  Object
     * @author  권지현
     * @since   2021.11.12
     */
    @RequestMapping(value = "/smsTelNoManage/getVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVal(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("컨트롤러 진입");

        String ORDR_IDXX = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS").format(new Date());

        System.out.println("주문번호 " + ORDR_IDXX);

        CT_CLI       cc      = new CT_CLI();

        String UP_HASH       = "";
        UP_HASH = cc.makeHashData( ENC_KEY, SITE_CD   +
                ORDR_IDXX +
                ""   +
                ""   +
                "00" +
                "00" +
                "00" +
                ""   +
                ""
        );

        System.out.println("주문번호 " + UP_HASH);

        DefaultMap<String> result = new DefaultMap<>();
        result.put("site_cd", SITE_CD);
        result.put("web_siteid", WEB_SITEID);
        result.put("gw_url", GW_URL);
        result.put("Ret_URL", RET_URL);
        result.put("ordr_idxx", ORDR_IDXX);
        result.put("up_hash", UP_HASH);

        System.out.println("세션 ID " + sessionInfoVO.getSessionId());

        result.put("sessionId", sessionInfoVO.getSessionId());

        System.out.println("결과1 " + result);

        return returnJson(Status.OK, result);
    }
}