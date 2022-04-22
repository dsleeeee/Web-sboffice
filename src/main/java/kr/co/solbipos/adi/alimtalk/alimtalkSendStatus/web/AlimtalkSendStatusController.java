package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusVO;
// API VO
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSendCancelVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.ApiSendCancelReceiveVO;
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
 * @Class Name : AlimtalkSendStatusController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkSendStatus")
public class AlimtalkSendStatusController {

    private final SessionService sessionService;
    private final AlimtalkSendStatusService alimtalkSendStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendStatusController(SessionService sessionService, AlimtalkSendStatusService alimtalkSendStatusService) {
        this.sessionService = sessionService;
        this.alimtalkSendStatusService = alimtalkSendStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/alimtalkSendStatus/list.sb", method = RequestMethod.GET)
//    public String alimtalkSendStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus";
//    }

    /**
     * 알림톡 전송결과 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 30.
     */
    @RequestMapping(value = "/alimtalkSendStatus/getAlimtalkSendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkSendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }

    /**
     * 알림톡 전송결과 - 예약취소 (메세지발송 취소 API 호출 및 저장)
     *
     * @param
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 04. 20.
     */
    @RequestMapping(value = "/alimtalkSendStatus/getAlimtalkReserveCancelApiSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkReserveCancelApiSave(@RequestBody AlimtalkSendStatusVO[] alimtalkSendStatusVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>> API sb 진입");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;
        int resultCode = 0;
        String resultMessage = "";

        System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>>  예약취소 건수 : " + alimtalkSendStatusVOs.length);

        //GET 방식 HTTPS
        HttpURLConnection conn = null;
        ObjectMapper mapper = new ObjectMapper();

        String ApiUrl = alimtalkSendStatusVOs[0].getApiUrl();
        String AppKey = alimtalkSendStatusVOs[0].getAppKey();
        String SecretKey = alimtalkSendStatusVOs[0].getSecretKey();

        for(AlimtalkSendStatusVO alimtalkSendStatusVO: alimtalkSendStatusVOs) {

            // NHN API 호출(메세지발송 취소)
            ApiSendCancelVO apiSendCancelVO = new ApiSendCancelVO();
            apiSendCancelVO.setAppKey(AppKey);
            apiSendCancelVO.setRequestId(alimtalkSendStatusVO.getRequestId());

            // NHN API 응답(메세지발송 취소)
            ApiSendCancelReceiveVO apiSendCancelReceiveVO = new ApiSendCancelReceiveVO();

            try {
                // 객체를 JSON 타입의 String으로 변환
                String jsonString = mapper.writeValueAsString(apiSendCancelVO);

                System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>> API 호출 인자 값 : " + jsonString);

                URL url = new URL(ApiUrl + "/alimtalk/v2.2/appkeys/" + AppKey + "/messages/" + apiSendCancelVO.getRequestId());
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("DELETE");
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

                System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>> API 호출 URL : " + url);
                System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>> API 응답 인자 값 : " +  sb.toString());

                //json 데이터를 클래스에 넣음.
                apiSendCancelReceiveVO = mapper.readValue(sb.toString(), ApiSendCancelReceiveVO.class);

                if(apiSendCancelReceiveVO.getHeader().getResultCode() == 0){

                    // 알림톡 전송결과 - 예약취소
                    result = alimtalkSendStatusService.getAlimtalkSendStatusReserveCancelSave(alimtalkSendStatusVO, sessionInfoVO);
                }

            }  catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            resultCode = apiSendCancelReceiveVO.getHeader().getResultCode();
            resultMessage = apiSendCancelReceiveVO.getHeader().getResultMessage();
        }

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("resultCode", resultCode);
        resultMap.put("resultMessage", resultMessage);

        System.out.println("WEB_ALIMTALK >>> 알림톡 예약취소 >>> API sb 끝");

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 알림톡 일자별 전송현황 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 04. 01.
     */
    @RequestMapping(value = "/alimtalkDaySendStatus/getAlimtalkDaySendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkDaySendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkDaySendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }

    /**
     * 알림톡 기간별 전송현황 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 04. 01.
     */
    @RequestMapping(value = "/alimtalkPeriodSendStatus/getAlimtalkPeriodSendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkPeriodSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkPeriodSendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }
}