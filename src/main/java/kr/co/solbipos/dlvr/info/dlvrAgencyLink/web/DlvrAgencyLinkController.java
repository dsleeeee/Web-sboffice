package kr.co.solbipos.dlvr.info.dlvrAgencyLink.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.dlvrAgencyLink.service.DlvrAgencyLinkReqVO;
import kr.co.solbipos.dlvr.info.dlvrAgencyLink.service.DlvrAgencyLinkService;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DlvrAgencyLinkController.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.14  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/dlvr/manage/info/dlvrAgencyLink")
public class DlvrAgencyLinkController {

    // [OMS API 정보]
    // (개발)
    public static final String OMS_API_URL  = "https://testapi.orderpick.kr";
    public static final String ACCESS_TOKEN  = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0YXBpLm9yZGVycGljay5rciIsImV4cCI6MTkxMjM4NTU4MywianRpIjoiODVhNjM2ZTQtZTFlYS00MmRlLWJlNmUtMTFkMTg5ZjA4OGI2IiwiYnJhbmRJZCI6bnVsbCwic2hvcElkIjpudWxsLCJ0YXhObyI6bnVsbCwiY2hhbm5lbCI6bnVsbCwicG9zU2VydmVyIjoiU09MQkkiLCJyaWRlciI6bnVsbCwicm9sZSI6bnVsbCwiaXNzdWVUYXJnZXQiOiJQT1NfU0VSVkVSIn0.JMsykAaMahWR229oyWFHD58iIT_fCemvVWEbRMvYeiMMQWyKVbuwXxyBb1gyJLI65k_1GZPQ9uPaDOTEpGjeyg";
    // (운영)
    //public static final String OMS_API_URL  = "https://api.orderpick.kr";
    //public static final String ACCESS_TOKEN  = "";

    // [POS OMS API 정보]
    // (개발)
    public static final String POS_OMS_API_URL = "https://test-api.orderkit.co.kr";
    public static final String SECRET_KEY = "Pu51+1YJD50Jp0/7ZDy94aJsh0HRZcfjKQf5bHiiZtWHerPuMSY1EEbVSVXz8P7JxOEt01DMXFw5ItGT3l9hLQ==";
    // (운영)
    //public static final String POS_OMS_API_URL = "https://api.orderkit.co.kr:14000";
    //public static final String SECRET_KEY = "Sg2V9TynJ1TZf4mvCz7eOPT1nqerG0zlS5GfzeCqIN+PZnxhOd3EUhvXRMKi9oA4y0Ewbije+yzp1fSkZyDE7g==";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrAgencyLinkService dlvrAgencyLinkService;
    private final SessionService sessionService;

    @Autowired
    public DlvrAgencyLinkController(DlvrAgencyLinkService dlvrAgencyLinkService, SessionService sessionService) {
        this.dlvrAgencyLinkService = dlvrAgencyLinkService;
        this.sessionService = sessionService;
    }

    /**
     * 배달대행사 연동 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "dlvr/info/dlvrAgencyLink/dlvrAgencyLink";
    }

    /**
     * 배달대행사 코드 조회
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/getDlvrAgency.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrAgency(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        String apiFullUrl = OMS_API_URL + "/oms/v1/delivery-agencies";

        Map<String, Object> resultMap = getRequest(dlvrAgencyLinkReqVO, apiFullUrl, ACCESS_TOKEN, "omsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 배달대행사 연동 현황 조회
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/getAgencyLink.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAgencyLink(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String apiFullUrl = OMS_API_URL + "/v1/shops/" + sessionInfoVO.getStoreCd() + "/rider";

        Map<String, Object> resultMap = getRequest(dlvrAgencyLinkReqVO, apiFullUrl, ACCESS_TOKEN, "omsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 배달대행사 매장 조회
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/getDlvrAgencyStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrAgencyStore(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String apiFullUrl = OMS_API_URL + "/oms/v1/shops/" + sessionInfoVO.getStoreCd() + "/delivery-agency/stores";

        Map<String, Object> resultMap = getRequest(dlvrAgencyLinkReqVO, apiFullUrl, ACCESS_TOKEN, "omsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 배달대행사 매핑 등록
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/regAgencyLink.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regAgencyLink(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String apiFullUrl = OMS_API_URL + "/oms/v1/shops/" + sessionInfoVO.getStoreCd() + "/delivery-agency";

        Map<String, Object> resultMap = postRequest(dlvrAgencyLinkReqVO, apiFullUrl, ACCESS_TOKEN, "omsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 배달대행사 매핑 삭제
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/deleteAgencyLink.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAgencyLink(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String apiFullUrl = OMS_API_URL + "/oms/v1/shops/" + sessionInfoVO.getStoreCd() + "/delivery-agency";

        Map<String, Object>resultMap = deleteRequest(dlvrAgencyLinkReqVO, apiFullUrl, ACCESS_TOKEN, "omsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 유저 상태 조회(구독여부, 주문 중개 서비스 사용여부, 배달앱 연동 정보)
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/getOmsUserStatus.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOmsUserStatus(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {


        //dlvrAgencyLinkReqVO.setPos_shop_id("shop123");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dlvrAgencyLinkReqVO.setPos_shop_id(sessionInfoVO.getStoreCd());

        String apiFullUrl = POS_OMS_API_URL + "/open/api/v1/oms_seller/status";

        Map<String, Object> resultMap = getRequest(dlvrAgencyLinkReqVO, apiFullUrl, SECRET_KEY, "posOmsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * 주문 중개 서비스 변경
     * @param dlvrAgencyLinkReqVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 14.
     */
    @RequestMapping(value = "/saveOrderAndRider.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOrderAndRider(DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        //dlvrAgencyLinkReqVO.setPos_shop_id("shop123");

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        dlvrAgencyLinkReqVO.setPos_shop_id(sessionInfoVO.getStoreCd());

        String apiFullUrl = POS_OMS_API_URL + "/open/api/v1/oms_seller/orderandrider";

        Map<String, Object> resultMap = postRequest(dlvrAgencyLinkReqVO, apiFullUrl, SECRET_KEY, "posOmsApi");

        return ReturnUtil.returnListJson(Status.OK, resultMap);
    }

    /**
     * get 호출
     * @param dlvrAgencyLinkReqVO
     * @param apiUrl
     * @param tokenOrKey
     * @author 이다솜
     * @since 2025.09.12
     */
    public Map<String, Object> getRequest(@RequestBody DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, String apiUrl, String tokenOrKey, String apiType) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(dlvrAgencyLinkReqVO.getLinkType());

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {

            // 0. VO 객체를 Map으로 변환하여 쿼리 파라미터 생성
            Map<String, String> queryParams = convertVoToMap(dlvrAgencyLinkReqVO);

            // 1. URL과 쿼리 파라미터 조합
            String fullUrl = buildUrlWithQueryParams(apiUrl, queryParams);
            URL url = new URL(fullUrl);
            apiLinkVO.setUrl(fullUrl);
            System.out.println("API 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            apiLinkVO.setRequestDt(currentDateTimeString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            // 호출 API에 따른 header 정보 셋팅
            if (apiType == "omsApi") {
                connection.setRequestProperty("Authorization", "Bearer " + tokenOrKey);
            } else {
                connection.setRequestProperty("x-api-key", tokenOrKey);
            }

            apiLinkVO.setRequestMethod(connection.getRequestMethod());

            // 3. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            apiLinkVO.setResponseDt(currentDateTimeString());
            apiLinkVO.setStatusCode(String.valueOf(responseCode));
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 4. 응답 본문 읽기
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    apiLinkVO.setResponse(response.toString());
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>(){});
                    System.out.println("서버 응답: " + response.toString());
                }
            } else {
                // 에러 발생 시 에러 스트림을 읽음
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine = null;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    apiLinkVO.setResponse(errorResponse.toString());
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>(){});
                    System.out.println("에러 응답: " + errorResponse.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        // API 호출 로그 저장
        //omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);

        //
        return resultMap;
    }

    /**
     * post 호출
     * @param dlvrAgencyLinkReqVO
     * @param apiUrl
     * @param tokenOrKey
     * @author  이다솜
     * @since   2025.09.12
     */
    public Map<String, Object> postRequest(@RequestBody DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, String apiUrl, String tokenOrKey, String apiType) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(dlvrAgencyLinkReqVO.getLinkType());

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            apiLinkVO.setUrl(apiUrl);
            System.out.println("API 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            apiLinkVO.setRequestDt(currentDateTimeString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            // 호출 API에 따른 header 정보 셋팅
            if (apiType == "omsApi") {
                connection.setRequestProperty("Authorization", "Bearer " + tokenOrKey);
            } else {
                connection.setRequestProperty("x-api-key", tokenOrKey);
            }

            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.
            apiLinkVO.setRequestMethod(connection.getRequestMethod());

            // 데이터 초기화(JSON payload 생성에는 필요없는 값, null 처리)
            dlvrAgencyLinkReqVO.setLinkType(null);

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(dlvrAgencyLinkReqVO);
            apiLinkVO.setRequest(jsonData);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            apiLinkVO.setResponseDt(currentDateTimeString());
            apiLinkVO.setStatusCode(String.valueOf(responseCode));
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 5. 응답 본문 읽기
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    apiLinkVO.setResponse(response.toString());
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
                    System.out.println("서버 응답: " + response.toString());
                }
            } else {
                // 에러 발생 시 에러 스트림을 읽음
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine = null;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    apiLinkVO.setResponse(errorResponse.toString());
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {});
                    System.out.println("에러 응답: " + errorResponse.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        // API 호출 로그 저장
        //omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);

        //
        return resultMap;
    }

    /**
     * delete 호출
     *
     * @param dlvrAgencyLinkReqVO
     * @param apiUrl
     * @param tokenOrKey
     * @author 이다솜
     * @since 2025.09.12
     */
    public Map<String, Object> deleteRequest(@RequestBody DlvrAgencyLinkReqVO dlvrAgencyLinkReqVO, String apiUrl, String tokenOrKey, String apiType) {


        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(dlvrAgencyLinkReqVO.getLinkType());

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            apiLinkVO.setUrl(apiUrl);
            System.out.println("API 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            apiLinkVO.setRequestDt(currentDateTimeString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            // 호출 API에 따른 header 정보 셋팅
            if (apiType == "omsApi") {
                connection.setRequestProperty("Authorization", "Bearer " + tokenOrKey);
            } else {
                connection.setRequestProperty("x-api-key", tokenOrKey);
            }

            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.
            apiLinkVO.setRequestMethod(connection.getRequestMethod());

            // 데이터 초기화(JSON payload 생성에는 필요없는 값, null 처리)
            dlvrAgencyLinkReqVO.setLinkType(null);

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(dlvrAgencyLinkReqVO);
            apiLinkVO.setRequest(jsonData);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            apiLinkVO.setResponseDt(currentDateTimeString());
            apiLinkVO.setStatusCode(String.valueOf(responseCode));
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 5. 응답 본문 읽기
            // DELETE 요청은 보통 200(OK), 204(No Content) 등을 반환하며,
            // 응답 본문이 없을 수도 있습니다.
            if (responseCode >= 200 && responseCode < 300) {
                // 성공적인 응답 (2xx)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    if (response.length() > 0) {
                        apiLinkVO.setResponse(response.toString());
                        resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
                        System.out.println("서버 응답: " + response.toString());
                    }
                }
            } else {
                // 에러 발생 시 에러 스트림을 읽음
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine = null;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    apiLinkVO.setResponse(errorResponse.toString());
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {});
                    System.out.println("에러 응답: " + errorResponse.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        // API 호출 로그 저장
        //omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);

        //
        return resultMap;
    }

    /**
     * VO 객체의 필드를 Map으로 변환하는 헬퍼 메소드
     * @param vo
     * @return
     * @throws Exception
     */
    private static Map<String, String> convertVoToMap(Object vo) throws Exception {
        if (vo == null) {
            return new HashMap<>();
        }

        Map<String, String> map = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(vo.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if(!pd.getName().equals("linkType")) { // 파라미터로 사용하지 않는 값 제외
                Method reader = pd.getReadMethod();
                if (reader != null && !pd.getName().equals("class")) {
                    Object value = reader.invoke(vo);
                    if (value != null && value != "") {
                        System.out.println("parameter : " + pd.getName() + "=" + value);
                        map.put(pd.getName(), String.valueOf(value));
                    }
                }
            }
        }
        return map;
    }

    /**
     * 쿼리 파라미터를 URL에 추가하는 헬퍼 메소드
     * @param baseUrl
     * @param params
     * @return
     * @throws Exception
     */
    private static String buildUrlWithQueryParams(String baseUrl, Map<String, String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return baseUrl;
        }

        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
            String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
            sj.add(key + "=" + value);
        }

        return baseUrl + "?" + sj.toString();
    }

}
