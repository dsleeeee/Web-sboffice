package kr.co.solbipos.adi.sms.smsTelNoManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertRegistration;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertResult;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertService;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertTransaction;
import kr.co.solbipos.adi.sms.kcp.web.KcpPopupResponse;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    // 프로필의 returnBaseUrl과 조합할 KCP 콜백 경로
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsTelNoManageController.class);
    private static final String RETURN_PATH =
            "/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
    private final SessionService sessionService;
    private final SmsTelNoManageService smsTelNoManageService;
    private final KcpCertService kcpCertService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsTelNoManageController(SessionService sessionService,
                                    SmsTelNoManageService smsTelNoManageService,
                                    KcpCertService kcpCertService) {
        this.sessionService = sessionService;
        this.smsTelNoManageService = smsTelNoManageService;
        this.kcpCertService = kcpCertService;
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
     * 발신번호관리 - 휴대폰 본인인증 대기 요청 저장
     * 팝업 제출 전에 주문번호를 CERT_ID로 저장하고 callback에서 결과를 갱신한다.
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

        // 발신번호 인증 대기 행 저장
        int result = smsTelNoManageService.getSmsTelNoManageSave(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 발신번호관리 - 휴대폰 발신번호 KCP 결과 콜백
     * KCP 거래를 복원하고 인증 결과를 조회해 발신번호 등록 결과를 저장한다.
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
        // 콜백 결과코드와 거래키 수신
        String resCd = trim(request.getParameter("res_cd"));
        String regCertKey = trim(request.getParameter("reg_cert_key"));
        // 콜백 수신 확인용(개인정보 아님). site_cd/cert_no/enc_cert_data2/up_hash/dn_hash는 V2에서 없어졌다.
        LOGGER.info("KCP V2 콜백 수신 [화면=SMS_TEL_NO_REGIST, res_cd={}, res_msg={}, reg_cert_key={}]",
                resCd, trim(request.getParameter("res_msg")), regCertKey);

        // 실패 거래 복원 및 결과 저장
        if (!"0000".equals(resCd)) {
            // 실패한 거래정보 조회 및 DB 결과 저장
            updateFailedVerification(regCertKey, resCd);
            // 거래정보 삭제
            removeQuietly(regCertKey);
            // 인증 실패 알림 후 팝업 종료
            KcpPopupResponse.alertAndClose(response,
                    "본인인증이 완료되지 않았습니다." + codeSuffix(resCd), false);
            return;
        }

        if (isBlank(regCertKey)) {
            // 인증 실패 알림 후 팝업 종료
            KcpPopupResponse.alertAndClose(response, "본인인증 거래정보가 없습니다.", false);
            return;
        }

        try {
            // 인증 결과 조회 및 복호화
            KcpCertResult certResult = kcpCertService.getResult(
                    regCertKey, KcpCertService.PURPOSE_SMS_TEL_NO_REGIST);
            KcpCertTransaction transaction = certResult.getTransaction();
            String telNo = value(certResult, "phone_no");
            if (isBlank(telNo)) {
                // 거래정보 삭제
                removeQuietly(regCertKey);
                // 인증 실패 알림 후 팝업 종료
                KcpPopupResponse.alertAndClose(response,
                        "본인인증 결과에 휴대폰 번호가 없습니다.", false);
                return;
            }

            // 거래 요청자 정보 복원
            SessionInfoVO sessionInfoVO = toSessionInfo(transaction);
            SmsTelNoManageVO smsTelNoManageVO = new SmsTelNoManageVO();
            smsTelNoManageVO.setCertId(transaction.getOrdrIdxx());
            smsTelNoManageVO.setResCd(resCd);
            smsTelNoManageVO.setTelNo(telNo);

            // 인증된 휴대폰 번호 중복 확인
            if (smsTelNoManageService.getSmsTelNoManageChk(smsTelNoManageVO, sessionInfoVO) != 0) {
                // 거래정보 삭제
                removeQuietly(regCertKey);
                // 중복 번호 알림 후 팝업 종료
                KcpPopupResponse.alertAndClose(response, "기존에 등록된 전화번호입니다.", false);
                return;
            }

            // 발신번호 인증 결과 저장
            if (smsTelNoManageService.getSmsTelNoManageUpdate(smsTelNoManageVO, sessionInfoVO) != 1) {
                // 저장 실패 알림 후 팝업 종료
                KcpPopupResponse.alertAndClose(response,
                        "본인인증은 성공했으나 저장에 문제가 있습니다. 고객센터로 문의해주세요.", false);
                return;
            }

            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
            // 인증 성공 알림 후 팝업 종료
            KcpPopupResponse.alertAndClose(response, "정상등록되었습니다.", true);
        } catch (Exception e) {
            LOGGER.error("KCP V2 result processing failed for SMS sender number registration", e);
            // 처리 오류 알림 후 팝업 종료
            KcpPopupResponse.alertAndClose(response,
                    "본인인증 결과 처리 중 오류가 발생했습니다. 다시 시도해주세요.", false);
        }
    }

    /**
     * 발신번호관리 - 저장
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
     * 일반번호 서류인증에 사용할 주문번호를 채번한다.
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
        sessionService.getSessionInfo(request);
        DefaultMap<String> result = new DefaultMap<>();
        // 주문번호 채번
        result.put("ordr_idxx", kcpCertService.newOrderId());
        return returnJson(Status.OK, result);
    }

    /**
     * 휴대폰 발신번호 등록용 KCP 거래를 등록한다.
     * 인증 팝업에 제출할 POST 값을 반환한다.
     */
    @RequestMapping(value = "/smsTelNoManage/getKcpVerifyVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKcpVerifyVal(HttpServletRequest request) {
        DefaultMap<String> result = new DefaultMap<>();
        try {
            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            // KCP 거래등록
            KcpCertRegistration registration = kcpCertService.register(
                    KcpCertService.PURPOSE_SMS_TEL_NO_REGIST, RETURN_PATH, sessionInfoVO);
            // 인증 팝업 호출값 구성
            result.put("call_url", registration.getCallUrl());
            result.put("reg_cert_key", registration.getRegCertKey());
            result.put("ordr_idxx", registration.getOrdrIdxx());
            result.put("kcp_page_submit_yn", registration.getKcpPageSubmitYn());
        } catch (Exception e) {
            LOGGER.warn("KCP V2 transaction registration failed for SMS sender number: {}", e.getMessage());
            result.put("error", "본인인증 거래등록에 실패했습니다.");
        }
        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호차단 탭 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/smsTelNoStop/getSmsTelNoStopList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoStopList(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsTelNoManageService.getSmsTelNoStopList(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /**
     * 발신번호차단 탭 - 저장
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 08.
     */
    @RequestMapping(value = "/smsTelNoStop/getSmsTelNoStopSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoStopSaveUpdate(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsTelNoStopSaveUpdate(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일반번호 인증요청 처리 팝업 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 01. 27.
     */
    @RequestMapping(value = "/smsGeneralNoManage/getSmsGeneralNoManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoManageList(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsTelNoManageService.getSmsGeneralNoManageList(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /**
     * 일반번호 인증요청 처리 팝업 - 첨부파일 다운로드
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 02. 07.
     */
    @RequestMapping(value="/smsGeneralNoManage/getSmsGeneralNoManageDownload.sb")
    @ResponseBody
    public void getSmsGeneralNoManageDownload(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) throws Exception {

//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\addSmsNo\\", smsTelNoManageVO.getFileName());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "board/addSmsNo/", smsTelNoManageVO.getFileName());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(smsTelNoManageVO.getFileName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(smsTelNoManageVO.getFileName().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 일반번호 인증요청 처리 팝업 - 저장
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 02. 07.
     */
    @RequestMapping(value = "/smsGeneralNoManage/getSmsGeneralNoManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoManageSave(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsGeneralNoManageSave(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일반번호 인증요청 처리 팝업 - 발신번호 중복체크
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 02. 07.
     */
    @RequestMapping(value = "/smsGeneralNoManage/getSmsGeneralNoManageCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoManageCount(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsGeneralNoManageCount(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일반번호 인증요청 처리2 팝업 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 15.
     */
    @RequestMapping(value = "/smsGeneralNoManage2/getSmsGeneralNoManage2List.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoManage2List(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsTelNoManageService.getSmsGeneralNoManage2List(smsTelNoManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /**
     * 일반번호 인증요청 처리2 팝업 - 첨부파일 다운로드
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 15.
     */
    @RequestMapping(value="/smsGeneralNoManage2/getSmsGeneralNoManageDownload2.sb")
    @ResponseBody
    public void getSmsGeneralNoManageDownload2(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) throws Exception {

//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\addSmsNo\\", smsTelNoManageVO.getFileName());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "board/addSmsNo/", smsTelNoManageVO.getFileName());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
//            fileName = URLEncoder.encode(smsTelNoManageVO.getFileName(), "UTF-8");
            fileName = URLEncoder.encode(smsTelNoManageVO.getDownloadFileName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
//            fileName = new String(smsTelNoManageVO.getFileName().getBytes("UTF-8"), "iso-8859-1");
            fileName = new String(smsTelNoManageVO.getDownloadFileName().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 일반번호 인증요청 처리2 팝업 - 저장
     *
     * @param smsTelNoManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 15.
     */
    @RequestMapping(value = "/smsGeneralNoManage2/getSmsGeneralNoManage2Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoManage2Save(@RequestBody SmsTelNoManageVO[] smsTelNoManageVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsTelNoManageService.getSmsGeneralNoManage2Save(smsTelNoManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * SMS 발신번호 서류인증 미리보기 팝업 - 조회
     *
     * @param smsTelNoManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 20.
     */
    @RequestMapping(value = "/smsPreview/getSmsPreviewFileNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsPreviewFileNm(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = smsTelNoManageService.getSmsPreviewFileNm(smsTelNoManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 실패한 KCP 거래를 복원해 발신번호 인증 대기 행에 결과 코드를 저장한다. */
    private void updateFailedVerification(String regCertKey, String resCd) {
        if (isBlank(regCertKey)) {
            return;
        }
        try {
            // 거래정보 조회
            KcpCertTransaction transaction = kcpCertService.getTransaction(
                    regCertKey, KcpCertService.PURPOSE_SMS_TEL_NO_REGIST);
            SmsTelNoManageVO smsTelNoManageVO = new SmsTelNoManageVO();
            smsTelNoManageVO.setCertId(transaction.getOrdrIdxx());
            smsTelNoManageVO.setResCd(resCd);
            smsTelNoManageVO.setTelNo("");
            // 최초 요청자 정보 복원 및 실패 결과 저장
            smsTelNoManageService.getSmsTelNoManageUpdate(
                    smsTelNoManageVO, toSessionInfo(transaction));
        } catch (Exception e) {
            LOGGER.warn("Failed to persist rejected KCP V2 sender-number verification: {}", e.getMessage());
        }
    }

    /** Redis 거래에 저장된 최초 요청자 정보로 업무 처리용 세션 객체를 복원한다. */
    private SessionInfoVO toSessionInfo(KcpCertTransaction transaction) {
        // Redis 거래의 사용자·소속·세션 정보를 복원한다.
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        sessionInfoVO.setSessionId(transaction.getSessionId());
        sessionInfoVO.setUserId(transaction.getUserId());
        sessionInfoVO.setOrgnCd(transaction.getOrgnCd());
        return sessionInfoVO;
    }

    /** KCP 복호화 결과에서 지정한 인증 항목을 문자열로 꺼낸다. */
    private String value(KcpCertResult result, String key) {
        // 복호화 결과에서 필요한 업무 필드만 꺼낸다.
        Object value = result.getCertData().get(key);
        return value == null ? "" : String.valueOf(value);
    }

    /** 콜백 응답을 방해하지 않도록 Redis 거래 정리 중 발생한 예외를 로그로만 남긴다. */
    private void removeQuietly(String regCertKey) {
        if (isBlank(regCertKey)) {
            return;
        }
        try {
            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
        } catch (Exception e) {
            LOGGER.warn("Failed to remove rejected KCP V2 sender-number transaction: {}", e.getMessage());
        }
    }

    /** 음성파일등록 - 목록 조회 */
    @RequestMapping(value = "/smsGeneralNoManage2/getVoiceFileList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVoiceFileList(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> result = smsTelNoManageService.getVoiceFileList(smsTelNoManageVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result, smsTelNoManageVO);
    }

    /** 음성파일등록 - 저장(multipart) */
    @RequestMapping(value = "/smsGeneralNoManage2/saveVoiceFile.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVoiceFile(org.springframework.web.multipart.MultipartHttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = smsTelNoManageService.saveVoiceFile(request, sessionInfoVO);
        return returnJson(Status.OK, result);
    }

    /** 음성파일등록 - 삭제 */
    @RequestMapping(value = "/smsGeneralNoManage2/delVoiceFile.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delVoiceFile(@RequestBody SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = smsTelNoManageService.delVoiceFile(smsTelNoManageVO, sessionInfoVO);
        return returnJson(Status.OK, result);
    }

    /** 음성파일등록 - 다운로드 (게시판과 동일 /FileRoot/board/) */
    @RequestMapping(value = "/smsGeneralNoManage2/downloadVoiceFile.sb")
    @ResponseBody
    public void downloadVoiceFile(SmsTelNoManageVO smsTelNoManageVO, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        // (2026.09.03) 게시판 다운로드와 동일하게 파일명에서 경로 문자를 제거 : 상위 폴더 접근(경로 조작) 방지
        String fileNm = smsTelNoManageVO.getFileName();

        if (fileNm == null) { fileNm = ""; }

        fileNm = fileNm.replaceAll("../", "").replaceAll("/", "").replaceAll("\\\\", "");

        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "board/", fileNm);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        
        String header = request.getHeader("User-Agent");
        String fileName;

        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            fileName = URLEncoder.encode(smsTelNoManageVO.getDownloadFileName(), "UTF-8");
        } else {
            fileName = new String(smsTelNoManageVO.getDownloadFileName().getBytes("UTF-8"), "iso-8859-1");
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /** KCP 결과 코드가 있을 때만 사용자 안내 문구 뒤에 붙일 문자열을 만든다. */
    private String codeSuffix(String code) {
        return isBlank(code) ? "" : " (" + code + ")";
    }

    /** 콜백 파라미터의 null을 빈 문자열로 바꾸고 앞뒤 공백을 제거한다. */
    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    /** 값이 null이거나 공백뿐인지 확인한다. */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
