package kr.co.solbipos.base.prod.qrOrderKeyMap.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapService;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name  : QrOrderKeyMapController.java
 * @Description : 기초관리 > 상품관리2 > QR주문키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.28  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/qrOrderKeyMap")
public class QrOrderKeyMapController {

    // (개발)
    public static final String QR_API_URL = "https://testqr-api.orderpick.kr";
    public static final String JWT_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0YXBpLm9yZGVycGljay5rciIsImV4cCI6MTg2OTAxOTg0NiwianRpIjoiYTc4OWM1NWEtYzc1Zi00MjBlLTlhY2MtZGU0ODcwYzRlOWExIiwiYnJhbmRJZCI6bnVsbCwic2hvcElkIjpudWxsLCJ0YXhObyI6bnVsbCwiY2hhbm5lbCI6bnVsbCwicG9zU2VydmVyIjoiTUVUQV9DSVRZIiwicmlkZXIiOm51bGwsInJvbGUiOm51bGwsImlzc3VlVGFyZ2V0IjoiUE9TX1NFUlZFUiJ9.NUEZjMZjmNDZdRPNbWEjiVdFDJz2mcGdaD9YQljgWrMkVpwqzIXA1RLDWsTInSP4yMnPncbgaRR-CrmPSqwjhw";
    // (운영)
    public static final String OMS_API_URL  = "https://qr-api.orderpick.kr";
    public static final String ACCESS_TOKEN  = "";

    private final SessionService sessionService;
    private final QrOrderKeyMapService qrOrderKeyMapService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final MessageService messageService;


    /**
     * Constructor Injection
     */
    public QrOrderKeyMapController(SessionService sessionService, QrOrderKeyMapService qrOrderKeyMapService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, MessageService messageService) {
        this.sessionService = sessionService;
        this.qrOrderKeyMapService = qrOrderKeyMapService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.messageService = messageService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/qrOrderKeyMap/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/prod/qrOrderKeyMap/qrOrderKeyMapRegist";
    }

    /**
     * QR오더 카테고리 (분류) 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = qrOrderKeyMapService.getQrOrderCategory(qrOrderKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * QR오더 - QR오더 카테고리(분류) 저장
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/saveQrOrderCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveQrOrderCategory(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.saveQrOrderCategory(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * QR오더 키맵 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = qrOrderKeyMapService.getQrOrderKeyMap(qrOrderKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * QR오더 - QR오더 키맵 수정
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/updateQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateQrOrderKeyMap(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.updateQrOrderKeyMap(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * QR오더 상품 조회
     *
     * @param   qrOrderKeyMapVO
     * @param   request
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQrOrderProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQrOrderProdList(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = qrOrderKeyMapService.getQrOrderProdList(qrOrderKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, qrOrderKeyMapVO);
    }

    /**
     * QR오더 - QR오더 키맵 수정
     *
     * @param   qrOrderKeyMapVOS
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 11. 28.
     */
    @RequestMapping(value = "/qrOrderKeyMap/saveQrOrderKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveQrOrderKeyMap(@RequestBody QrOrderKeyMapVO[] qrOrderKeyMapVOS, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = qrOrderKeyMapService.saveQrOrderKeyMap(qrOrderKeyMapVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * QR오더- QR동기화
     * @param qrOrderKeyMapVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 12. 03
     */
    @RequestMapping(value = "/qrOrderKeyMap/getQRSynchronize.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getQRSynchronize(QrOrderKeyMapVO qrOrderKeyMapVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String apiUrl = "";
        String apiToken = "";

        // 개발/운영 URL 조회
        List<DefaultMap<Object>> apiInfoList = qrOrderKeyMapService.getApiEnvNm(qrOrderKeyMapVO,sessionInfoVO);

        if(apiInfoList.size() > 0){
            apiUrl = apiInfoList.get(0).getStr("apiUrl");
            apiToken = apiInfoList.get(0).getStr("accessToken");
        }

        String apiFullUrl = apiUrl + "/qr/menu/v1/pos/" + sessionInfoVO.getStoreCd() + "/sync";

        Map<String, Object> resultMap = putRequest(qrOrderKeyMapVO, apiFullUrl, apiToken);

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * put 호출
     *
     * @param qrOrderKeyMapVO
     * @param apiUrl
     * @param tokenOrKey
     * @author 이다솜
     * @since  2025. 12. 03
     */
    public Map<String, Object> putRequest(@RequestBody QrOrderKeyMapVO qrOrderKeyMapVO, String apiUrl, String tokenOrKey) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("매장코드 : " + qrOrderKeyMapVO.getStoreCd());
            System.out.println("API 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + tokenOrKey);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(qrOrderKeyMapVO);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            qrOrderKeyMapVO.setAgencyFg("KCPQR");
            qrOrderKeyMapVO.setLastResponseDt(currentDateTimeString());
            qrOrderKeyMapVO.setLastStatusCode(Integer.toString(responseCode));
            System.out.println("HTTP 응답 코드: " + responseCode);

            String responseBody = null;

            // 5. 응답 본문 읽기
            if (responseCode >= 200 && responseCode <= 299) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    responseBody = response.toString();

                    // 204인 경우 body가 없으므로 JSON 강제 생성
                    if (responseBody == null || responseBody.isEmpty()) {
                        responseBody = "{\"status\":\"OK\",\"message\":\"정상 처리되었습니다.\",\"data\":{\"list\":null}}";
                    }else{
                    }
                    resultMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
                    });
                    System.out.println("서버 응답: " + response.toString());
                    qrOrderKeyMapVO.setAgencyUseYn("Y");
                    qrOrderKeyMapVO.setLastResponse(mapper.writeValueAsString(resultMap));
                    // API 호출 결과 저장
                    qrOrderKeyMapService.saveApiLog(qrOrderKeyMapVO, sessionInfoVO);
                }
            } else {
                // 에러 발생 시 에러 스트림을 읽음
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine = null;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {
                    });
                    System.out.println("에러 응답: " + errorResponse.toString());
                    qrOrderKeyMapVO.setAgencyUseYn("N");
                    qrOrderKeyMapVO.setLastResponse(mapper.writeValueAsString(resultMap));
                    // API 호출 결과 저장
                    qrOrderKeyMapService.saveApiLog(qrOrderKeyMapVO, sessionInfoVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return resultMap;
    }
}
