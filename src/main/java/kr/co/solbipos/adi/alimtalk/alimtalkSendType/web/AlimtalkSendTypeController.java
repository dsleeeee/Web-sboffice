package kr.co.solbipos.adi.alimtalk.alimtalkSendType.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeVO;
// API VO
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSenderVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSenderReceiveVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiTokenVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiTokenReceiveVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiGroupVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiGroupReceiveVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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

import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : AlimtalkSendTypeController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송유형
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkSendType")
public class AlimtalkSendTypeController {

    private final SessionService sessionService;
    private final AlimtalkSendTypeService alimtalkSendTypeService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendTypeController(SessionService sessionService, AlimtalkSendTypeService alimtalkSendTypeService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.alimtalkSendTypeService = alimtalkSendTypeService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/alimtalkSendType/list.sb", method = RequestMethod.GET)
    public String alimtalkSendTypeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 알림톡 계정등록 팝업 - 키값 리스트 조회
        List<DefaultMap<String>> alimtalkKeyColList = alimtalkSendTypeService.getAlimtalkKeyColList(sessionInfoVO);

        String groupSenderKey = alimtalkKeyColList.get(0).getStr("groupKey");
        String groupSenderKeyNm = alimtalkKeyColList.get(0).getStr("groupNm");
        String appKey = alimtalkKeyColList.get(0).getStr("appKey");
        String secretKey = alimtalkKeyColList.get(0).getStr("secretKey");
        String apiUrl = alimtalkKeyColList.get(0).getStr("apiUrl");

        model.addAttribute("groupSenderKey", groupSenderKey);
        model.addAttribute("groupSenderKeyNm", groupSenderKeyNm);
        model.addAttribute("appKey", appKey);
        model.addAttribute("secretKey", secretKey);
        model.addAttribute("apiUrl", apiUrl);

        System.out.println("WEB_ALIMTALK >>> groupSenderKey : " + groupSenderKey);
        System.out.println("WEB_ALIMTALK >>> groupSenderKeyNm : " + groupSenderKeyNm);
        System.out.println("WEB_ALIMTALK >>> appKey : " + appKey);
        System.out.println("WEB_ALIMTALK >>> secretKey : " + secretKey);
        System.out.println("WEB_ALIMTALK >>> apiUrl : " + apiUrl);

        // 알림톡 전송유형 - 템플릿 치환값 리스트 조회
        List<DefaultMap<String>> templateChangeKeyColList = alimtalkSendTypeService.getAlimtalkTemplateChangeKeyColList(sessionInfoVO);
        model.addAttribute("templateChangeKeyColList", templateChangeKeyColList);
        System.out.println("WEB_ALIMTALK >>> templateChangeKeyColList : " + templateChangeKeyColList);

        // 환경설정 코드값 조회 [1228 알림톡계정기준]
        String alkIdEnvstVal1228 = StringUtil.getOrBlank(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1228"));
        model.addAttribute("alkIdEnvstVal1228", alkIdEnvstVal1228);
        System.out.println("WEB_ALIMTALK >>> 환경설정 코드값 [1228 알림톡계정기준] : " + alkIdEnvstVal1228);

        return "adi/alimtalk/alimtalkSendType/alimtalkSendType";
    }

    /**
     * 알림톡 전송유형 - 계정정보 체크 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 17.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkIdRegisterChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkIdRegisterChk(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkIdRegisterChk(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 전송유형 - 전송유형 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 전송유형 - 전송유형 상세 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 전송유형 - 전송유형 상세 저장
     *
     * @param alimtalkSendTypeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailSave(@RequestBody AlimtalkSendTypeVO[] alimtalkSendTypeVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkSendTypeService.getAlimtalkSendTypeDetailSave(alimtalkSendTypeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 전송유형 - 템플릿 상세 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailTemplate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplate(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplate(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 전송유형 - 템플릿 상세 저장
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 11.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSendTypeDetailTemplateSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplateSave(@RequestBody AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplateSave(alimtalkSendTypeVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 전송유형 - 템플릿 목록 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 15.
     */
    @RequestMapping(value = "/templatePopup/getAlimtalkSendTypeDetailTemplateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendTypeDetailTemplateList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getAlimtalkSendTypeDetailTemplateList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 계정등록 팝업 - 계정정보 체크 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 21.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkIdRegisterAllChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkIdRegisterAllChk(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkIdRegisterAllChk(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 계정등록 팝업 - 그룹-계정정보 체크 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 21.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkRegisterGroupChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkRegisterGroupChk(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkSendTypeService.getAlimtalkRegisterGroupChk(alimtalkSendTypeVO, sessionInfoVO);

        System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정정보 체크 조회 result : " + result);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 계정등록 팝업 - 사업자 카테고리 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 21.
     */
    @RequestMapping(value = "/alimtalkIdRegister/getCategoryCodeComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCategoryCodeComboList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getCategoryCodeComboList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

    /**
     * 알림톡 계정등록 팝업 - 인증요청 (발신프로필 등록 API 호출 및 저장)
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 22.
     */
    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderApiSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSenderApiSave(@RequestBody AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> API sb 진입");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        String ApiUrl = alimtalkSendTypeVO.getApiUrl();
        String AppKey = alimtalkSendTypeVO.getAppKey();
        String SecretKey = alimtalkSendTypeVO.getSecretKey();

        // NHN API 호출(발신프로필 등록)
        ApiSenderVO apiSenderVO = new ApiSenderVO();
        apiSenderVO.setPlusFriendId(alimtalkSendTypeVO.getPlusFriendId());
        apiSenderVO.setPhoneNo(alimtalkSendTypeVO.getPhoneNo());
        apiSenderVO.setCategoryCode(alimtalkSendTypeVO.getCategoryCode());

        // NHN API 응답(발신프로필 등록)
        ApiSenderReceiveVO apiSenderReceiveVO = new ApiSenderReceiveVO();

        try {
            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiSenderVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> API 호출 인자 값 : " + jsonString);

            URL url = new URL(ApiUrl + "/alimtalk/v2.1/appkeys/" + AppKey + "/senders");
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty ("X-Secret-Key", SecretKey);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(jsonString);
            osw.flush();

            BufferedReader br = null;
            //url -> json으로 던짐
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            //string으로 데이터 받음.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> API 응답 인자 값 : " +  sb.toString());

            //json 데이터를 클래스에 넣음.
            apiSenderReceiveVO = mapper.readValue(sb.toString(), ApiSenderReceiveVO.class);

            if(apiSenderReceiveVO.getHeader().getResultCode() == 0){

                // 알림톡 계정정보 저장 insert
                result = alimtalkSendTypeService.getAlimtalkSenderSaveInsert(alimtalkSendTypeVO, sessionInfoVO);
            }

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiSenderReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiSenderReceiveVO.getHeader().getResultMessage());

        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> API sb 끝");

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 계정등록 팝업 - 계정등록 (토큰인증 API 호출 및 저장)
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 25.
     */
    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderTokenApiSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSenderTokenApiSave(@RequestBody AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> API sb 진입");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        String ApiUrl = alimtalkSendTypeVO.getApiUrl();
        String AppKey = alimtalkSendTypeVO.getAppKey();
        String SecretKey = alimtalkSendTypeVO.getSecretKey();

        // NHN API 호출(발신프로필 토큰 인증)
        ApiTokenVO apiTokenVO = new ApiTokenVO();
        apiTokenVO.setPlusFriendId(alimtalkSendTypeVO.getPlusFriendId());
        apiTokenVO.setToken(alimtalkSendTypeVO.getToken());

        // NHN API 응답(발신프로필 토큰 인증)
        ApiTokenReceiveVO apiTokenReceiveVO = new ApiTokenReceiveVO();

        try {
            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiTokenVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> API 호출 인자 값 : " + jsonString);

            URL url = new URL(ApiUrl + "/alimtalk/v2.1/appkeys/" + AppKey + "/sender/token");
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty ("X-Secret-Key", SecretKey);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(jsonString);
            osw.flush();

            BufferedReader br = null;
            //url -> json으로 던짐
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            //string으로 데이터 받음.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> API 응답 인자 값 : " +  sb.toString());

            //json 데이터를 클래스에 넣음.
            apiTokenReceiveVO = mapper.readValue(sb.toString(), ApiTokenReceiveVO.class);

            if(apiTokenReceiveVO.getHeader().getResultCode() == 0){
                alimtalkSendTypeVO.setSenderKey(apiTokenReceiveVO.getSender().getSenderKey());
                alimtalkSendTypeVO.setApprFg(apiTokenReceiveVO.getSender().getStatus());

                // 알림톡 계정정보 저장 update
                result = alimtalkSendTypeService.getAlimtalkSenderSaveUpdate(alimtalkSendTypeVO, sessionInfoVO);
            }

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiTokenReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiTokenReceiveVO.getHeader().getResultMessage());
        resultMap.put("senderKey", apiTokenReceiveVO.getSender().getSenderKey());

        System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> API sb 끝");

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 계정등록 팝업 - 그룹-계정등록 (그룹에 발신프로필 추가 API 호출 및 저장)
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 28.
     */
    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderGroupApiSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSenderGroupApiSave(@RequestBody AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정등록 >>> API sb 진입");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        String ApiUrl = alimtalkSendTypeVO.getApiUrl();
        String AppKey = alimtalkSendTypeVO.getAppKey();
        String SecretKey = alimtalkSendTypeVO.getSecretKey();
        String GroupKey = alimtalkSendTypeVO.getGroupSenderKey();
        String SenderKey = alimtalkSendTypeVO.getSenderKey();

        // NHN API 호출(그룹에 발신프로필 추가)
        ApiGroupVO apiGroupVO = new ApiGroupVO();
        apiGroupVO.setAppKey(AppKey);
        apiGroupVO.setGroupSenderKey(GroupKey);
        apiGroupVO.setSenderKey(SenderKey);

        // NHN API 응답(그룹에 발신프로필 추가)
        ApiGroupReceiveVO apiGroupReceiveVO = new ApiGroupReceiveVO();

        try {
            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiGroupVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정등록 >>> API 호출 인자 값 : " + jsonString);

            URL url = new URL(ApiUrl + "/alimtalk/v2.1/appkeys/" + AppKey + "/sender-groups/" + GroupKey + "/senders/" + SenderKey);
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty ("X-Secret-Key", SecretKey);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(jsonString);
            osw.flush();

            BufferedReader br = null;
            //url -> json으로 던짐
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            //string으로 데이터 받음.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정등록 >>> API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정등록 >>> API 응답 인자 값 : " +  sb.toString());

            //json 데이터를 클래스에 넣음.
            apiGroupReceiveVO = mapper.readValue(sb.toString(), ApiGroupReceiveVO.class);

            // 해당 그룹-계정정보가 이미 NHN 사이트에 등록되있을때(-1018 This is a plusFriend that has already been added)
            if(apiGroupReceiveVO.getHeader().getResultCode() == 0 || apiGroupReceiveVO.getHeader().getResultCode() == -1018){

                // 알림톡 그룹-계정정보 저장 insert
                result = alimtalkSendTypeService.getAlimtalkSenderGroupSaveInsert(alimtalkSendTypeVO, sessionInfoVO);
            }

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiGroupReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiGroupReceiveVO.getHeader().getResultMessage());

        System.out.println("WEB_ALIMTALK >>> 알림톡 그룹-계정등록 >>> API sb 끝");

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 잔여금액 조회
     *
     * @param alimtalkSendTypeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 05. 09.
     */
    @RequestMapping(value = "/alimtalkSendType/getAlimtalkSmsAmtList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSmsAmtList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = alimtalkSendTypeService.getAlimtalkSmsAmtList(alimtalkSendTypeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}