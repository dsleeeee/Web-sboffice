package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service.NaverPlaceLinkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : NaverPlaceLinkServiceImpl.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.27  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.27
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("NaverPlaceLinkService")
@Transactional
public class NaverPlaceLinkServiceImpl implements NaverPlaceLinkService {

    // [인증 API 관련 정보]
    // (개발)
    public static final String AUTH_URL = "https://test-api.newsmartplace.naver.com";
    public static final String REFRESH_TOKEN = "xB9vX2mK5pL8rS1tU4vW7xY0zA3bC6dE9fG2hJ5kL8nN1pQ4rS7tU0vW3xY6zA9bC2dF5gH8jK1mP4qR7sT0uV3wX6yZ9aB2cE5fG8hJ1kL4nN6pQ9rS2tavx5vW8xdd";
    // (운영)
    //public static final String AUTH_URL = "https://api.newsmartplace.naver.com";
    //public static final String REFRESH_TOKEN = "";

    // [연동 API 관련 정보]
    // (개발)
    public static final String LINK_URL = "https://test-agency-api.pbp.naver.com"; //https://test-api.pbp.naver.com";
    // (운영)
    //public static final String LINK_URL = "https://api.pbp.naver.com";

    public static final String CLIENT_ID = "YOUR_CLIENT_ID";//애플리케이션 클라이언트 아이디값";
    public static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";//애플리케이션 클라이언트 시크릿값";

    private final NaverPlaceLinkMapper naverPlaceLinkMapper;

    /**
     * Constructor Injection
     */
    public NaverPlaceLinkServiceImpl(NaverPlaceLinkMapper naverPlaceLinkMapper) {
        this.naverPlaceLinkMapper = naverPlaceLinkMapper;
    }

    /**
     * 인증 API Access Token 조회
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> getAccessToken(NaverPlaceLinkVO naverPlaceLinkVO) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        String apiUrl = AUTH_URL + "/auth/v1/token?grant_type=agency_refresh_token&refresh_token=" + REFRESH_TOKEN;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("인증 API Access Token 조회 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverPlaceLinkVO);
            System.out.println("jsonData :" + jsonData);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 5. 응답 본문 읽기
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {
                    });
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
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {
                    });
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

        return resultMap;
    }

    /**
     * 동의여부확인 API 호출
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> getAgreeYn(NaverPlaceLinkVO naverPlaceLinkVO) {

        String apiFullUrl = LINK_URL + "/v1/pbp-owner-member/agency-target-summary";

        naverPlaceLinkVO.setAccessToken(getAccessToken(naverPlaceLinkVO).get("token").toString());
        naverPlaceLinkVO.setUniqueId("");
        naverPlaceLinkVO.setProjections("AGREED_PLACE_PRIVACY_AGREEMENTS, MY_BIZ_AGREEMENT"); // 스마플 개인정보 약관 동의 목록

        Map<String, Object> resultMap = getRequest(naverPlaceLinkVO, apiFullUrl);

        return resultMap;
    }

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> getPlaceList(NaverPlaceLinkVO naverPlaceLinkVO) {

        String apiFullUrl = LINK_URL + "/v1/custom/pos/place-businesses";

        //naverPlaceLinkVO.setAccessToken(getAccessToken(naverPlaceLinkVO).get("token").toString());
        naverPlaceLinkVO.setUniqueId("");
        naverPlaceLinkVO.setPage("0");

        Map<String, Object> resultMap = getRequest(naverPlaceLinkVO, apiFullUrl);

        return resultMap;

    }

    /**
     * 업체 수정/등록 API 호출
     *
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> savePlace(NaverPlaceLinkVO naverPlaceLinkVO) {

        String apiFullUrl = LINK_URL + "/v1/custom/pos/integration";

        //naverPlaceLinkVO.setAccessToken(getAccessToken(naverPlaceLinkVO).get("token").toString());
        naverPlaceLinkVO.setUniqueId("");

        Map<String, Object> resultMap = postRequest(naverPlaceLinkVO, apiFullUrl);

        return resultMap;

    }

    /**
     * 연동 추가 API
     *
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> mappingPlace(NaverPlaceLinkVO naverPlaceLinkVO) {

        String apiFullUrl = LINK_URL + "/v1/custom/pos/place-businesses/place-id/" + naverPlaceLinkVO.getPlaceId() + "/agency-mappings";

        //naverPlaceLinkVO.setAccessToken(getAccessToken(naverPlaceLinkVO).get("token").toString());
        naverPlaceLinkVO.setUniqueId("");

        Map<String, Object> resultMap = postRequest(naverPlaceLinkVO, apiFullUrl);

        return resultMap;

    }

    /**
     * 연동 해지 API
     *
     * @param naverPlaceLinkVO
     * @return
     */
    @Override
    public Map<String, Object> unMappingPlace(NaverPlaceLinkVO naverPlaceLinkVO) {

        String apiFullUrl = LINK_URL + "/v1/custom/pos/place-businesses/place-id/" + naverPlaceLinkVO.getPlaceId() + "/agency-mappings";

        //naverPlaceLinkVO.setAccessToken(getAccessToken(naverPlaceLinkVO).get("token").toString());
        naverPlaceLinkVO.setUniqueId("");

        Map<String, Object> resultMap = deleteRequest(naverPlaceLinkVO, apiFullUrl);

        return resultMap;

    }

    /**
     * get 호출
     * @param naverPlaceLinkVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> getRequest(@RequestBody NaverPlaceLinkVO naverPlaceLinkVO, String apiUrl) {

        //SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {

            // 0. VO 객체를 Map으로 변환하여 쿼리 파라미터 생성
            Map<String, String> queryParams = convertVoToMap(naverPlaceLinkVO);

            // 1. URL과 쿼리 파라미터 조합
            String fullUrl = buildUrlWithQueryParams(apiUrl, queryParams);
            URL url = new URL(fullUrl);
            System.out.println("get 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            if (naverPlaceLinkVO.getAccessToken() != null && naverPlaceLinkVO.getAccessToken() != "") {
                connection.setRequestProperty("Authorization", "Bearer " + naverPlaceLinkVO.getAccessToken());
            }

            connection.setRequestProperty("X-Naver-Unique-Id", naverPlaceLinkVO.getUniqueId());
            connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);

            // 3. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 4. 응답 본문 읽기
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
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

        return resultMap;
    }

    /**
     * post 호출
     * @param naverPlaceLinkVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> postRequest(@RequestBody NaverPlaceLinkVO naverPlaceLinkVO, String apiUrl) {

        //SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("post 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            if (naverPlaceLinkVO.getAccessToken() != null && naverPlaceLinkVO.getAccessToken() != "") {
                connection.setRequestProperty("Authorization", "Bearer " + naverPlaceLinkVO.getAccessToken());
            }

            connection.setRequestProperty("X-Naver-Unique-Id", naverPlaceLinkVO.getUniqueId());
            connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverPlaceLinkVO);
            System.out.println("jsonData :" + jsonData);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 5. 응답 본문 읽기
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {
                    });
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
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {
                    });
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

        return resultMap;
    }

    /**
     * delete 호출
     * @param naverPlaceLinkVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> deleteRequest(@RequestBody NaverPlaceLinkVO naverPlaceLinkVO, String apiUrl) {

        //SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("delete 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            if (naverPlaceLinkVO.getAccessToken() != null && naverPlaceLinkVO.getAccessToken() != "") {
                connection.setRequestProperty("Authorization", "Bearer " + naverPlaceLinkVO.getAccessToken());
            }

            connection.setRequestProperty("X-Naver-Unique-Id", naverPlaceLinkVO.getUniqueId());
            connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverPlaceLinkVO);
            System.out.println("jsonData :" + jsonData);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            // 4. 응답 코드 확인
            int responseCode = connection.getResponseCode();
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
                        resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {
                        });
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
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<Map<String, Object>>() {
                    });
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
            if(!pd.getName().equals("accessToken") && !pd.getName().equals("uniqueId")) { // 파라미터로 사용하지 않는 값 제외
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
     *
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









    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     * @param naverPlaceLinkVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public int saveNaverUniqueId(NaverPlaceLinkVO naverPlaceLinkVO, SessionInfoVO sessionInfoVO) {

        // 1. [네이버 회원 프로필 조회 API] 사용을 위한 Access Token 조회
        String accessToken = "";
        //String redirectURI = URLEncoder.encode("YOUR_CALLBACK_URL", "UTF-8");
        String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiUrl += "client_id=" + CLIENT_ID;
        apiUrl += "&client_secret=" + CLIENT_SECRET;
        //apiUrl += "&redirect_uri=" + redirectURI;
        apiUrl += "&code=" + naverPlaceLinkVO.getCode();
        apiUrl += "&state=" + naverPlaceLinkVO.getState();
        System.out.println("saveNaverUniqueId Access Token Url=" + apiUrl);

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.print("responseCode=" + responseCode);
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if (responseCode == 200) {
                accessToken = res.toString();
                System.out.println(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // 2. [네이버 회원 프로필 조회 API] 조회(회원 프로필에 Unique ID 있음)
        apiUrl = "https://openapi.naver.com/v1/nid/me";
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        String responseBody = get(apiUrl,requestHeaders);
        System.out.println(responseBody);

        // 3. 네.아.로 Unique ID 저장
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        naverPlaceLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverPlaceLinkVO.setUniqueId(responseBody);
        naverPlaceLinkVO.setRegDt(currentDt);
        naverPlaceLinkVO.setRegId(sessionInfoVO.getUserId());
        naverPlaceLinkVO.setModDt(currentDt);
        naverPlaceLinkVO.setModId(sessionInfoVO.getUserId());

        procCnt = naverPlaceLinkMapper.saveNaverUniqueId(naverPlaceLinkVO);

        return procCnt;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
