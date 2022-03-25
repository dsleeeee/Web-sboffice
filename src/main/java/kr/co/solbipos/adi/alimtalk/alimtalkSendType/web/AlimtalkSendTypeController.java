package kr.co.solbipos.adi.alimtalk.alimtalkSendType.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSenderVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSenderReceiveVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiTokenVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiTokenReceiveVO;
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

    private final String AppKey = "SOfDZwJzUpJKjIFG";
    private final String SecretKey = "uYtgbHyO";
    private final String ApiUrl = "https://api-alimtalk.cloud.toast.com";
    private final String GroupKey = "aa4fdaba4e8087b8e3773ac7321d0e73664fa5d3";
    private final String SenderKey = "57e3cbf116e20ca20091142ed2623dd19926865b";

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendTypeController(SessionService sessionService, AlimtalkSendTypeService alimtalkSendTypeService) {
        this.sessionService = sessionService;
        this.alimtalkSendTypeService = alimtalkSendTypeService;
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
     * 템플릿 선택변경 팝업 - 템플릿 조회
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
    public Result getSmsTelNoComboList(AlimtalkSendTypeVO alimtalkSendTypeVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendTypeService.getCategoryCodeComboList(alimtalkSendTypeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendTypeVO);
    }

//    /**
//     * 알림톡 계정등록 팝업 - 인증요청 API (참고용)
//     *
//     * @param
//     * @param request
//     * @param response
//     * @param model
//     * @return  Object
//     * @author  김설아
//     * @since   2022. 03. 22.
//     */
//    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderApiSample.sb", method = RequestMethod.POST)
//    public String getAlimtalkSenderApiSample(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> sb 진입");
//        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> request plusFriendId : " + request.getParameter( "plusFriendId"));
//        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> request phoneNo : " + request.getParameter( "phoneNo"));
//        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> request categoryCode : " + request.getParameter( "categoryCode"));
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        ApiSenderVO apiSenderVO = new ApiSenderVO();
//        String result = "";
//        int resultA = 0;
//
//        //GET 방식 HTTPS
//        HttpURLConnection conn = null;
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            apiSenderVO.setPlusFriendId(request.getParameter( "plusFriendId"));
//            apiSenderVO.setPhoneNo(request.getParameter( "phoneNo"));
//            apiSenderVO.setCategoryCode(request.getParameter( "categoryCode"));
//
//            // 객체를 JSON 타입의 String으로 변환
//            String jsonString = mapper.writeValueAsString(apiSenderVO);
//
//            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 호출 인자 값 : " + jsonString);
//
//            URL url = new URL(ApiUrl + "/alimtalk/v2.1/appkeys/" + AppKey + "/senders");
//            conn = (HttpURLConnection)url.openConnection();
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestProperty ("X-Secret-Key", SecretKey);
//
//            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            osw.write(jsonString);
//            osw.flush();
//
//            BufferedReader br = null;
//            //url -> json으로 던짐
//            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            //string으로 데이터 받음.
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 호출 URL : " + url);
//            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 응답 인자 값 : " +  sb.toString());
//
//            AlimtalkSenderReceiveVO header = new AlimtalkSenderReceiveVO();
//            //json 데이터를 클래스에 넣음.
//            header = mapper.readValue(sb.toString(), AlimtalkSenderReceiveVO.class);
//
//            result = header.getHeader().getResultCode().toString();
//
//            if(header.getHeader().getResultCode() == 0){
//                AlimtalkSendTypeVO alimtalkSendTypeVO = new AlimtalkSendTypeVO();
//                alimtalkSendTypeVO.setPlusFriendId(apiSenderVO.getPlusFriendId());
//                alimtalkSendTypeVO.setPhoneNo(apiSenderVO.getPhoneNo());
//                alimtalkSendTypeVO.setCategoryCode(apiSenderVO.getCategoryCode());
//                alimtalkSendTypeVO.setAppKey(AppKey);
//                alimtalkSendTypeVO.setSecretKey(SecretKey);
//
//                // 알림톡 계정정보 저장
////                resultA = alimtalkSendTypeService.getAlimtalkSenderSave(alimtalkSendTypeVO, sessionInfoVO);
//            }
//
//        }  catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // API 호출
        String resultApi = getAlimtalkSenderApi(request, response, model, alimtalkSendTypeVO, sessionInfoVO);

        // 결과 안내시 쓰려고
        ObjectMapper mapper = new ObjectMapper();
        // NHN API 응답(발신프로필 등록)
        ApiSenderReceiveVO apiSenderReceiveVO = new ApiSenderReceiveVO();
        try {
            //json 데이터를 클래스에 넣음.
            apiSenderReceiveVO = mapper.readValue(resultApi, ApiSenderReceiveVO.class);
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiSenderReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiSenderReceiveVO.getHeader().getResultMessage());

        return returnJson(Status.OK, resultMap);
    }
    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderApi.sb", method = RequestMethod.POST)
    public String getAlimtalkSenderApi(HttpServletRequest request, HttpServletResponse response, Model model,
                                       AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> Api sb 진입");

        String result = "";
        int resultDB = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            // NHN API 호출(발신프로필 등록)
            ApiSenderVO apiSenderVO = new ApiSenderVO();
            apiSenderVO.setPlusFriendId(alimtalkSendTypeVO.getPlusFriendId());
            apiSenderVO.setPhoneNo(alimtalkSendTypeVO.getPhoneNo());
            apiSenderVO.setCategoryCode(alimtalkSendTypeVO.getCategoryCode());

            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiSenderVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 호출 인자 값 : " + jsonString);

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

            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> NHN API 응답 인자 값 : " +  sb.toString());

            // NHN API 응답(발신프로필 등록)
            ApiSenderReceiveVO apiSenderReceiveVO = new ApiSenderReceiveVO();
            //json 데이터를 클래스에 넣음.
            apiSenderReceiveVO = mapper.readValue(sb.toString(), ApiSenderReceiveVO.class);

            if(apiSenderReceiveVO.getHeader().getResultCode() == 0){
                alimtalkSendTypeVO.setAppKey(AppKey);
                alimtalkSendTypeVO.setSecretKey(SecretKey);

                // 알림톡 계정정보 저장 insert
                resultDB = alimtalkSendTypeService.getAlimtalkSenderSaveInsert(alimtalkSendTypeVO, sessionInfoVO);
            }

            // 결과 안내시 쓰려고
            result = sb.toString();

            System.out.println("WEB_ALIMTALK >>> 알림톡 계정등록 >>> Api sb 끝");

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 알림톡 계정등록 팝업 - 계정등록 (토큰인증 및 API 호출 및 저장)
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // API 호출
        String resultApi = getAlimtalkSenderTokenApi(request, response, model, alimtalkSendTypeVO, sessionInfoVO);

        // 결과 안내시 쓰려고
        ObjectMapper mapper = new ObjectMapper();
        // NHN API 응답(발신프로필 토큰 인증)
        ApiTokenReceiveVO apiTokenReceiveVO = new ApiTokenReceiveVO();
        try {
            //json 데이터를 클래스에 넣음.
            apiTokenReceiveVO = mapper.readValue(resultApi, ApiTokenReceiveVO.class);
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiTokenReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiTokenReceiveVO.getHeader().getResultMessage());
        resultMap.put("apprFg", apiTokenReceiveVO.getSender().getStatus());

        return returnJson(Status.OK, resultMap);
    }
    @RequestMapping(value = "/alimtalkIdRegister/getAlimtalkSenderTokenApi.sb", method = RequestMethod.POST)
    public String getAlimtalkSenderTokenApi(HttpServletRequest request, HttpServletResponse response, Model model,
                                       AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> Api sb 진입");

        String result = "";
        int resultDB = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            // NHN API 호출(발신프로필 토큰 인증)
            ApiTokenVO apiTokenVO = new ApiTokenVO();
            apiTokenVO.setPlusFriendId(alimtalkSendTypeVO.getPlusFriendId());
            apiTokenVO.setToken(alimtalkSendTypeVO.getToken());

            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiTokenVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> NHN API 호출 인자 값 : " + jsonString);

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

            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> NHN API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> NHN API 응답 인자 값 : " +  sb.toString());

            // NHN API 응답(발신프로필 토큰 인증)
            ApiTokenReceiveVO apiTokenReceiveVO = new ApiTokenReceiveVO();
            //json 데이터를 클래스에 넣음.
            apiTokenReceiveVO = mapper.readValue(sb.toString(), ApiTokenReceiveVO.class);

            if(apiTokenReceiveVO.getHeader().getResultCode() == 0){
                alimtalkSendTypeVO.setSenderKey(apiTokenReceiveVO.getSender().getSenderKey());
                alimtalkSendTypeVO.setApprFg(apiTokenReceiveVO.getSender().getStatus());

                // 알림톡 계정정보 저장 update
                resultDB = alimtalkSendTypeService.getAlimtalkSenderSaveUpdate(alimtalkSendTypeVO, sessionInfoVO);
            }

            // 결과 안내시 쓰려고
            result = sb.toString();

            System.out.println("WEB_ALIMTALK >>> 알림톡 토큰등록 >>> Api sb 끝");

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}