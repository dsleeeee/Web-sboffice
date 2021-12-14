package kr.co.solbipos.adi.sms.marketingSmsSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.kcp.CT_CLI;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.web.SmsTelNoManageController;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.adi.sms.smsTelNoManage.web.SmsTelNoManageController.*;

/**
 * @Class Name : MarketingSmsSendController.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/marketingSmsSend")
public class MarketingSmsSendController {

    private final SessionService sessionService;
    private final MarketingSmsSendService marketingSmsSendService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendController(SessionService sessionService, MarketingSmsSendService marketingSmsSendService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.marketingSmsSendService = marketingSmsSendService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/marketingSmsSend/list.sb", method = RequestMethod.GET)
    public String marketingSmsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {

//        MarketingSmsSendVOdudfhd marketingSmsSendVO = new MarketingSmsSendVO();
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        // 마케팅용 SMS전송 - 메세지그룹 조회
//        List<DefaultMap<String>> msgGrpAddColList = marketingSmsSendService.getMsgGrpColList(marketingSmsSendVO, sessionInfoVO);
//        model.addAttribute("msgGrpAddColList", msgGrpAddColList);
//
//        // 마케팅용 SMS전송 - 회원등급 리스트 조회
//        List membrClassList = marketingSmsSendService.getMembrClassList(marketingSmsSendVO, sessionInfoVO);
//        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
//        model.addAttribute("memberClassList", membrClassListAll);

        return "adi/sms/marketingSmsSend/marketingSmsSend";
    }

    /**
     * 메세지관리 - 메세지서식 조회(최근이력)
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendMsgManageDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendMsgManageDtlList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 마케팅용 SMS전송 - 회원 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 마케팅용 SMS전송 - 검색 결과 저장
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 07.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendListSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendListSave(@RequestBody MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = marketingSmsSendService.getMarketingSmsSendListSave(marketingSmsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 마케팅용 SMS전송 - 1000건 이상 전송시 전송테이블에 몇건 Insert 됬는지 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 22.
     */
    @RequestMapping(value = "/marketingSmsSend/getSmsSendInsert1000Count.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendInsert1000Count(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = marketingSmsSendService.getSmsSendInsert1000Count(marketingSmsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }


    /**
     * 본인인증 여부 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/getVerifyChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyChk(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = marketingSmsSendService.getVerifyChk(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 본인인증 값 가져감
     *
     * @param
     * @param
     * @param
     * @param
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/getVerifyVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyVal(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

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
        result.put("Ret_URL", VERIFY_RET_URL);
        result.put("ordr_idxx", ORDR_IDXX);
        result.put("up_hash", UP_HASH);

        System.out.println("세션 ID " + sessionInfoVO.getSessionId());

        result.put("sessionId", sessionInfoVO.getSessionId());

        System.out.println("결과1 " + result);

        return returnJson(Status.OK, result);
    }

    /**
     * 본인인증 요청 저장
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/saveVerify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVerify(@RequestBody MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = marketingSmsSendService.saveVerify(marketingSmsSendVO, sessionInfoVO);

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
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/updateVerify.sb", method = RequestMethod.POST)
    public void updateVerify(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        MarketingSmsSendVO marketingSmsSendVO = new MarketingSmsSendVO();

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

        marketingSmsSendVO.setCertId(ordrIdxx);
        marketingSmsSendVO.setResCd(resCd);

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
                marketingSmsSendVO.setTelNo(cc.getKeyValue("phone_no"));
            }

            if(marketingSmsSendService.getVerifyChk(marketingSmsSendVO, sessionInfoVO) != 0){
                // 기등록번호
                out.println("<script>window.resizeTo(800,500);alert('기존에 등록된 전화번호입니다.'); window.close();</script>");
                out.flush();
            } else {
                if(marketingSmsSendService.updateVerify(marketingSmsSendVO, sessionInfoVO) == 1){

                    // 정상등록
                    out.println("<script>window.resizeTo(800,500);alert('정상등록되었습니다.'); window.close(); window.opener.location.reload(); </script>");
                    out.flush();
                } else {

                    // 인증성공 + DB저장실패
                    out.println("<script>window.resizeTo(800,500);alert('본인인증에 성공했으나 저장에 문제가 있습니다. 고객센터로 문의해주세요.'); window.close();</script>");
                    out.flush();
                }
            }
        } else {
            // 실패코드 저장
            marketingSmsSendVO.setTelNo("");
            marketingSmsSendService.updateVerify(marketingSmsSendVO, sessionInfoVO);

            out.println("<script>window.resizeTo(800,500);alert('본인인증 에러가 발생하였습니다. 고객센터로 문의해주세요.'); window.close();</script>");
            out.flush();
        }
    }
}