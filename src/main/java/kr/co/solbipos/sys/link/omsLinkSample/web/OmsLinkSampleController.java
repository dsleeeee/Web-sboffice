package kr.co.solbipos.sys.link.omsLinkSample.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.OmsLinkSampleService;
import kr.co.solbipos.sys.link.omsLinkSample.service.OmsLinkSampleVO;
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
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : OmsLinkSampleController.java
 * @Description : 시스템관리 > 연동 > OMS연동샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/link/omsLinkSample")
public class OmsLinkSampleController {

    private final SessionService sessionService;
    private final OmsLinkSampleService omsLinkSampleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public OmsLinkSampleController(SessionService sessionService, OmsLinkSampleService omsLinkSampleService) {
        this.sessionService = sessionService;
        this.omsLinkSampleService = omsLinkSampleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/link/omsLinkSample/omsLinkSample";
    }

    /**
     * OMS연동샘플 API 호출 목록 조회
     * @param apiLinkVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025.09.12
     */
    @RequestMapping(value = "/getOmsLinkSampleReqList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOmsLinkSampleReqList(ApiLinkVO apiLinkVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> list = omsLinkSampleService.getOmsLinkSampleReqList(apiLinkVO);

        return returnListJson(Status.OK, list, apiLinkVO);
    }

    /**
     * API 호출
     * @param omsLinkSampleVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025.09.12
     */
    @RequestMapping(value = "/getOmsLinkSampleReqApi.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOmsLinkSampleReqApi(@RequestBody OmsLinkSampleVO omsLinkSampleVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        int seq = 0;             // 호출 성공시, api 로그 seq값
        String apiUrl = "";      // api 주소
        String accessToken = ""; // accessToken 값

        if (omsLinkSampleVO.getSearchType().equals("test")) { // 개발조회
            apiUrl = "https://testapi.orderpick.kr";
            accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0YXBpLm9yZGVycGljay5rciIsImV4cCI6MTkxMjM4NTU4MywianRpIjoiODVhNjM2ZTQtZTFlYS00MmRlLWJlNmUtMTFkMTg5ZjA4OGI2IiwiYnJhbmRJZCI6bnVsbCwic2hvcElkIjpudWxsLCJ0YXhObyI6bnVsbCwiY2hhbm5lbCI6bnVsbCwicG9zU2VydmVyIjoiU09MQkkiLCJyaWRlciI6bnVsbCwicm9sZSI6bnVsbCwiaXNzdWVUYXJnZXQiOiJQT1NfU0VSVkVSIn0.JMsykAaMahWR229oyWFHD58iIT_fCemvVWEbRMvYeiMMQWyKVbuwXxyBb1gyJLI65k_1GZPQ9uPaDOTEpGjeyg";

        } else { // 운영조회
            apiUrl = "https://api.orderpick.kr";
            accessToken = "";
        }

        if (omsLinkSampleVO.getLinkType().equals("001")) { // 매장등록
            apiUrl += "/oms/v1/shops";
            seq = postRequest(omsLinkSampleVO, apiUrl, accessToken);

        } else if (omsLinkSampleVO.getLinkType().equals("002")) { // 매장 배대사 등록

        } else if (omsLinkSampleVO.getLinkType().equals("003")) { // 배대사 코드 조회
            apiUrl += "/oms/v1/delivery-agencies";
            seq = getRequest(omsLinkSampleVO, apiUrl, accessToken);

        } else if (omsLinkSampleVO.getLinkType().equals("004")) { // 배대사 매장 조회
            apiUrl += "/oms/v1/shops/" + omsLinkSampleVO.getPosShopId() + "/delivery-agency/stores";
            seq = getRequest(omsLinkSampleVO, apiUrl, accessToken);

        } else if (omsLinkSampleVO.getLinkType().equals("005")) { // 배대사 매핑 등록
            apiUrl += "/oms/v1/shops/" + omsLinkSampleVO.getPosShopId() + "/delivery-agency";
            seq = postRequest(omsLinkSampleVO, apiUrl, accessToken);

        } else if (omsLinkSampleVO.getLinkType().equals("006")) { // 배대사 매핑 삭제
            apiUrl += "/oms/v1/shops/" + omsLinkSampleVO.getPosShopId() + "/delivery-agency";
            seq = deleteRequest(omsLinkSampleVO, apiUrl, accessToken);
        }

        // api 로그 seq값 전달(api 호출 로그 조회시 사용)
        return returnJson(Status.OK, seq);
    }

    /**
     * get 호출
     * @param omsLinkSampleVO
     * @param apiUrl
     * @param accessToken
     * @author  이다솜
     * @since   2025.09.12
     */
    public int getRequest(@RequestBody OmsLinkSampleVO omsLinkSampleVO, String apiUrl, String accessToken) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(omsLinkSampleVO.getLinkType());

        try {

            // 0. VO 객체를 Map으로 변환하여 쿼리 파라미터 생성
            Map<String, String> queryParams = convertVoToMap(omsLinkSampleVO);

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
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
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
        return omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);
    }

    /**
     * post 호출
     * @param omsLinkSampleVO
     * @param apiUrl
     * @param accessToken
     * @author  이다솜
     * @since   2025.09.12
     */
    public int postRequest(@RequestBody OmsLinkSampleVO omsLinkSampleVO, String apiUrl, String accessToken) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(omsLinkSampleVO.getLinkType());

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            apiLinkVO.setUrl(apiUrl);

            // 2. HttpURLConnection 객체 생성 및 설정
            apiLinkVO.setRequestDt(currentDateTimeString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.
            apiLinkVO.setRequestMethod(connection.getRequestMethod());

            // 데이터 초기화(JSON payload 생성에는 필요없는 값, null 처리)
            omsLinkSampleVO.setLinkType(null);
            omsLinkSampleVO.setSearchType(null);

            // 3. 서버로 데이터 전송 (JSON payload)
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(omsLinkSampleVO);
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
        return omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);
    }

    /**
     * delete 호출
     * @param omsLinkSampleVO
     * @param apiUrl
     * @param accessToken
     * @author  이다솜
     * @since   2025.09.12
     */
    public int deleteRequest(@RequestBody OmsLinkSampleVO omsLinkSampleVO, String apiUrl, String accessToken) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // API 호출 로그 저장을 위한 셋팅
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType(omsLinkSampleVO.getLinkType());

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            apiLinkVO.setUrl(apiUrl);

            // 2. HttpURLConnection 객체 생성 및 설정
            apiLinkVO.setRequestDt(currentDateTimeString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.
            apiLinkVO.setRequestMethod(connection.getRequestMethod());

            // 데이터 초기화(JSON payload 생성에는 필요없는 값, null 처리)
            omsLinkSampleVO.setLinkType(null);
            omsLinkSampleVO.setSearchType(null);

            // 3. 서버로 데이터 전송 (JSON payload)
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(omsLinkSampleVO);
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
                        System.out.println("서버 응답: " + response.toString());
                    } else {
                        apiLinkVO.setResponse("성공: 응답 본문이 없습니다.");
                        System.out.println("성공: 응답 본문이 없습니다.");
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
        return omsLinkSampleService.saveApiLog(apiLinkVO, sessionInfoVO);
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
            if(!pd.getName().equals("linkType") && !pd.getName().equals("searchType") && !pd.getName().equals("posShopId")) { // 파라미터로 사용하지 않는 값 제외
                Method reader = pd.getReadMethod();
                if (reader != null && !pd.getName().equals("class")) {
                    Object value = reader.invoke(vo);
                    if (value != null && value != "") {
                        System.out.println("parameter : " + pd.getName() + "-" + value);
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
