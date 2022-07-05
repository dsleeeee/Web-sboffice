package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateService;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateVO;
// API VO
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.ApiTemplateImageVO;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.ApiTemplateImageReceiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : AlimtalkTemplateController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 템플릿관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkTemplate")
public class AlimtalkTemplateController {

    private final SessionService sessionService;
    private final AlimtalkTemplateService alimtalkTemplateService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkTemplateController(SessionService sessionService, AlimtalkTemplateService alimtalkTemplateService) {
        this.sessionService = sessionService;
        this.alimtalkTemplateService = alimtalkTemplateService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/alimtalkTemplate/list.sb", method = RequestMethod.GET)
//    public String alimtalkTemplateView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/alimtalk/alimtalkTemplate/alimtalkTemplate";
//    }

    /**
     * 알림톡 템플릿관리 - 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 08.
     */
    @RequestMapping(value = "/alimtalkTemplate/getAlimtalkTemplateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getAlimtalkTemplateList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 전송유형 콤보박스 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 08.
     */
    @RequestMapping(value = "/alimtalkTemplate/getSendTypeCdComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSendTypeCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getSendTypeCdComboList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 전송유형상세 콤보박스 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 08.
     */
    @RequestMapping(value = "/alimtalkTemplate/getSendTypeDtlCdComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSendTypeDtlCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getSendTypeDtlCdComboList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 계정 콤보박스 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 13.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getGroupKeyComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGroupKeyComboList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getGroupKeyComboList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 템플릿 카테고리(대분류) 콤보박스 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 15.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getTemplateClsCdLComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTemplateClsCdLComboList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getTemplateClsCdLComboList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 템플릿 카테고리(중분류) 콤보박스 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 15.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getTemplateClsCdMComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTemplateClsCdMComboList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getTemplateClsCdMComboList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 템플릿등록 팝업 - #{변수} 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 16.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getAlimtalkTemplateParamsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateParamsList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getAlimtalkTemplateParamsList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }

    /**
     * 알림톡 템플릿등록 팝업 - 저장
     *
     * @param alimtalkTemplateVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 14.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getAlimtalkTemplateRegisterSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateRegisterSave(@RequestBody AlimtalkTemplateVO[] alimtalkTemplateVOs, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        int result = alimtalkTemplateService.getAlimtalkTemplateRegisterSave(alimtalkTemplateVOs, sessionInfoVO);
        String result = alimtalkTemplateService.getAlimtalkTemplateRegisterSave(alimtalkTemplateVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 템플릿등록 팝업 - 이미지 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 30.
     */
    @RequestMapping(value = "alimtalkTemplateRegister/getAlimtalkTemplateImageFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdImageFileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

//        if(alimtalkTemplateService.getAlimtalkTemplateImageFileSave(request, sessionInfo)) {
//            return returnJson(Status.OK);
//        } else {
//            return returnJson(Status.FAIL);
//        }

        String result = alimtalkTemplateService.getAlimtalkTemplateImageFileSave(request, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 템플릿등록 팝업 -  템플릿 이미지 등록 (템플릿 이미지 등록 API 호출 및 저장)
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 30.
     */
    @RequestMapping(value = "/alimtalkTemplateRegister/getAlimtalkTemplateImageApiSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateImageApiSave(@RequestBody AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿 이미지 등록 >>> API sb 진입");

        int result = 0;

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        String ApiUrl = alimtalkTemplateVO.getApiUrl();
        String AppKey = alimtalkTemplateVO.getAppKey();
        String SecretKey = alimtalkTemplateVO.getSecretKey();
        String fileNm = alimtalkTemplateVO.getFileNm();
//        String filePath = "D:\\Workspace\\javaWeb\\alimtalkTemplateImg\\";
        String filePath = BaseEnv.FILE_UPLOAD_DIR + "/alimtalkTemplateImg/";
        String templateImageName = "";
        String templateImageUrl = "";

        // NHN API 호출(템플릿 이미지 등록)
        ApiTemplateImageVO apiTemplateImageVO = new ApiTemplateImageVO();
        File file = new File(filePath, fileNm);
        apiTemplateImageVO.setFile(file);

        // NHN API 응답(템플릿 이미지 등록)
        ApiTemplateImageReceiveVO apiTemplateImageReceiveVO = new ApiTemplateImageReceiveVO();

        try {
            // 객체를 JSON 타입의 String으로 변환
            String jsonString = mapper.writeValueAsString(apiTemplateImageVO);

            System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿 이미지 등록 >>> API 호출 인자 값 : " + jsonString);

            URL url = new URL(ApiUrl + "/alimtalk/v2.2/appkeys/" + AppKey + "/template-image");
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data");
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

            System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿 이미지 등록 >>> API 호출 URL : " + url);
            System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿 이미지 등록 >>> API 응답 인자 값 : " +  sb.toString());

            //json 데이터를 클래스에 넣음.
            apiTemplateImageReceiveVO = mapper.readValue(sb.toString(), ApiTemplateImageReceiveVO.class);

            if(apiTemplateImageReceiveVO.getHeader().getResultCode() == 0){
                // 저장 로직은 따로
                templateImageName = apiTemplateImageReceiveVO.getTemplateImage().getTemplateImageName();
                templateImageUrl = apiTemplateImageReceiveVO.getTemplateImage().getTemplateImageUrl();
            }

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", apiTemplateImageReceiveVO.getHeader().getResultCode());
        resultMap.put("resultMessage", apiTemplateImageReceiveVO.getHeader().getResultMessage());
        resultMap.put("templateImageName", templateImageName);
        resultMap.put("templateImageUrl", templateImageUrl);
        resultMap.put("fileNm", fileNm);
        resultMap.put("filePath", filePath);

        System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿 이미지 등록 >>> API sb 끝");

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 템플릿상세 팝업 - 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 27.
     */
    @RequestMapping(value = "/alimtalkTemplateDtl/getAlimtalkTemplateDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateDtlList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = alimtalkTemplateService.getAlimtalkTemplateDtlList(alimtalkTemplateVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 템플릿상세 팝업 - 버튼 조회
     *
     * @param alimtalkTemplateVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 06. 27.
     */
    @RequestMapping(value = "/alimtalkTemplateDtl/getAlimtalkTemplateDtlButtonsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkTemplateDtlButtonsList(AlimtalkTemplateVO alimtalkTemplateVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkTemplateService.getAlimtalkTemplateDtlButtonsList(alimtalkTemplateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkTemplateVO);
    }
}