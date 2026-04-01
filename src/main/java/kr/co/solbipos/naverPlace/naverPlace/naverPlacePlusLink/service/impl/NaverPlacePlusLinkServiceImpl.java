package kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverPlacePlusLink.service.NaverPlacePlusLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : NaverPlacePlusLinkServiceImpl.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 플러스 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.02.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.02.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("naverPlacePlusLinkService")
@Transactional
public class NaverPlacePlusLinkServiceImpl implements NaverPlacePlusLinkService {

    public static final String CLIENT_ID = "nEMag45FNxJsZUnX9ywM";  // 애플리케이션 클라이언트 아이디값
    public static final String CLIENT_SECRET = "wgHGb82SR1";        // 애플리케이션 클라이언트 시크릿값

    private final NaverPlacePlusLinkMapper naverPlacePlusLinkMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlacePlusLinkServiceImpl(NaverPlacePlusLinkMapper naverPlacePlusLinkMapper) {
        this.naverPlacePlusLinkMapper = naverPlacePlusLinkMapper;
    }

    /**
     * 개발/운영 Api URL 조회
     *
     * @param naverPlacePlusLinkVO
     * @return
     */
    @Override
    public DefaultMap<Object> getApiUrl(NaverPlacePlusLinkVO naverPlacePlusLinkVO) {
        return naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);
    }

    /**
     * 인증 API Access Token 조회
     *
     * @return
     */
    @Override
    public Map<String, Object> getAccessToken(String storeCd) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        // 개발/운영 Api URL 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setStoreCd(storeCd);
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_AUTH_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiUrl = apiInfo.getStr("apiUrl") + "/auth/v1/token?grant_type=agency_refresh_token&refresh_token=" + apiInfo.getStr("accessToken");

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
            String jsonData = mapper.writeValueAsString(null);

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
     * 네이버 로그인 state 값 저장
     *
     * @param naverPlacePlusLinkVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public int saveNaverState(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        naverPlacePlusLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverPlacePlusLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverPlacePlusLinkVO.setUserId(sessionInfoVO.getUserId());
        naverPlacePlusLinkVO.setRegDt(currentDt);
        naverPlacePlusLinkVO.setRegId(sessionInfoVO.getUserId());
        naverPlacePlusLinkVO.setModDt(currentDt);
        naverPlacePlusLinkVO.setModId(sessionInfoVO.getUserId());

        result = naverPlacePlusLinkMapper.saveNaverState(naverPlacePlusLinkVO);

        return result;
    }

    /**
     * 네이버 로그인 성공 후, 네.아.로 Unique ID 저장
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    @Override
    public String saveNaverUniqueId(NaverPlacePlusApiVO naverPlacePlusApiVO) {

        // 1. [네이버 회원 프로필 조회 API] 사용을 위한 Access Token 조회
        String accessToken = "";
        String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
        apiUrl += "&client_id=" + CLIENT_ID;
        apiUrl += "&client_secret=" + CLIENT_SECRET;
        apiUrl += "&code=" + naverPlacePlusApiVO.getCode();
        apiUrl += "&state=" + naverPlacePlusApiVO.getState();
        System.out.println("getNaverUniqueId Access Token Url = " + apiUrl);

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
                System.out.println(res.toString());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(res.toString());
                accessToken = jsonNode.get("access_token").asText();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // 2. [네이버 회원 프로필 조회 API] 조회(회원 프로필에 Unique ID 있음)
        String uniqueId = "";
        apiUrl = "https://openapi.naver.com/v1/nid/me";
        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

        try {
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", header);

            String responseBody = get(apiUrl, requestHeaders);
            long end = System.currentTimeMillis();
            System.out.println("네이버 회원 프로필 = " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);

            // 3. 네이버 로그인 state 값 비교 및 기존 세션정보 조회
            DefaultMap result = naverPlacePlusLinkMapper.getNaverState(naverPlacePlusApiVO);

            if (result != null && !result.isEmpty()) {

                String dt = currentDateTimeString();

                // 4. 네.아.로 Unique ID 저장
                NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
                naverPlacePlusLinkVO.setHqOfficeCd(result.getStr("hqOfficeCd"));
                naverPlacePlusLinkVO.setStoreCd(result.getStr("storeCd"));
                naverPlacePlusLinkVO.setUniqueId(jsonNode.get("response").get("id").asText());
                naverPlacePlusLinkVO.setLastResponseDt(dt);
                naverPlacePlusLinkVO.setMpNo(jsonNode.get("response").get("mobile").asText());
                naverPlacePlusLinkVO.setRegDt(dt);
                naverPlacePlusLinkVO.setRegId(result.getStr("userId"));
                naverPlacePlusLinkVO.setModDt(dt);
                naverPlacePlusLinkVO.setModId(result.getStr("userId"));

                naverPlacePlusLinkMapper.saveNaverUniqueId(naverPlacePlusLinkVO);
                uniqueId = naverPlacePlusLinkVO.getUniqueId();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return uniqueId;
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

    /**
     * 네이버 플레이스 연동매장 조회
     *
     * @param naverPlacePlusLinkVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public DefaultMap<String> getNaverStore(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO){

        naverPlacePlusLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverPlacePlusLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return naverPlacePlusLinkMapper.getNaverStore(naverPlacePlusLinkVO);
    }

    /**
     * 네.아.로 Unique ID 조회
     * @param naverPlacePlusLinkVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public String getNaverUniqueId(NaverPlacePlusLinkVO naverPlacePlusLinkVO, SessionInfoVO sessionInfoVO) {

        naverPlacePlusLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverPlacePlusLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return naverPlacePlusLinkMapper.getNaverUniqueId(naverPlacePlusLinkVO);
    }

    /**
     * 동의여부확인 API 호출
     *
     * @param naverPlacePlusApiVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public Map<String, Object> getAgreeYn(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO) {

        // 세션정보 또는 파라미터에서 받아온 정보 사용
        String sHqOfficeCd = "";
        String sStoreCd = "";
        String sUserId = "";

        if(sessionInfoVO.getStoreCd() != null && sessionInfoVO.getStoreCd() != "") {
            sHqOfficeCd = sessionInfoVO.getHqOfficeCd();
            sStoreCd = sessionInfoVO.getStoreCd();
            sUserId = sessionInfoVO.getUserId();
        }else{
            sHqOfficeCd = naverPlacePlusApiVO.getHqOfficeCd();
            sStoreCd = naverPlacePlusApiVO.getStoreCd();
            sUserId = naverPlacePlusApiVO.getUserId();
        }

        // 네.아.로 Unique ID 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setHqOfficeCd(sHqOfficeCd);
        naverPlacePlusLinkVO.setStoreCd(sStoreCd);
        String uniqueId = naverPlacePlusLinkMapper.getNaverUniqueId(naverPlacePlusLinkVO);

        // 개발/운영 Api URL 조회
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/pbp-owner-member/agency-target-summary";

        naverPlacePlusApiVO.setAccessToken(getAccessToken(sStoreCd).get("token").toString());
        naverPlacePlusApiVO.setUniqueId(uniqueId);
        naverPlacePlusApiVO.setProjections("AGREED_PLACE_PRIVACY_AGREEMENTS,MY_BIZ_AGREEMENT"); // 스마플 개인정보 약관 동의 목록

        Map<String, Object> resultMap = getRequest(naverPlacePlusApiVO, apiFullUrl);

        // 네이버 동의여부 저장
        String dt = currentDateTimeString();
        naverPlacePlusLinkVO.setUniqueId(uniqueId);
        naverPlacePlusLinkVO.setLastResponseDt(dt);
        naverPlacePlusLinkVO.setAgreementType((String.valueOf(resultMap)));
        naverPlacePlusLinkVO.setRegDt(dt);
        naverPlacePlusLinkVO.setRegId(sUserId);
        naverPlacePlusLinkVO.setModDt(dt);
        naverPlacePlusLinkVO.setModId(sUserId);

        naverPlacePlusLinkMapper.saveNaverAgreement(naverPlacePlusLinkVO);

        return resultMap;
    }

    /**
     * 업체목록조회 API 호출
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    @Override
    public List<Map<String, Object>> getPlaceList(NaverPlacePlusApiVO naverPlacePlusApiVO) {

        // 개발/운영 Api URL 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setStoreCd(naverPlacePlusApiVO.getStoreCd());
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/custom/pos/place-businesses";

        naverPlacePlusApiVO.setAccessToken(getAccessToken(naverPlacePlusApiVO.getStoreCd()).get("token").toString());

        List<Map<String, Object>> resultMap = getListRequest(naverPlacePlusApiVO, apiFullUrl);

        return resultMap;
    }

    /**
     * 연동 조회 API 호출
     *
     * @param naverPlacePlusApiVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public Map<String, Object> getNaverLinkYn(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO) {

        // 네.아.로 Unique ID 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverPlacePlusLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        String uniqueId = naverPlacePlusLinkMapper.getNaverUniqueId(naverPlacePlusLinkVO);

        // 개발/운영 Api URL 조회
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/custom/pos/place-businesses/place-id/" + naverPlacePlusApiVO.getPlaceId() + "/agency-mappings";

        naverPlacePlusApiVO.setAccessToken(getAccessToken(sessionInfoVO.getStoreCd()).get("token").toString());
        naverPlacePlusApiVO.setUniqueId(uniqueId);

        Map<String, Object> resultMap = getRequest(naverPlacePlusApiVO, apiFullUrl);

        return resultMap;
    }

    /**
     * 연동 추가 API 호출
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    @Override
    public Map<String, Object> mappingPlace(NaverPlacePlusApiVO naverPlacePlusApiVO) {

        // 개발/운영 Api URL 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setStoreCd(naverPlacePlusApiVO.getStoreCd());
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/custom/pos/place-businesses/place-id/" + naverPlacePlusApiVO.getPlaceId() + "/agency-mappings";

        naverPlacePlusApiVO.setAccessToken(getAccessToken(naverPlacePlusApiVO.getStoreCd()).get("token").toString());

        Map<String, Object> resultMap = postRequest(naverPlacePlusApiVO, apiFullUrl);

        // 연동일자 변환
        ZonedDateTime zdt = ZonedDateTime.parse(resultMap.get("regDateTime").toString());
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String regDateTime = zdt.format(outputFormatter);

        // 연동 매장 정보 저장
        String dt = currentDateTimeString();
        naverPlacePlusLinkVO.setUniqueId(naverPlacePlusApiVO.getUniqueId());
        naverPlacePlusLinkVO.setNaverStoreNm(naverPlacePlusApiVO.getBusinessName());
        naverPlacePlusLinkVO.setNaverPlaceId(resultMap.get("placeId").toString());
        naverPlacePlusLinkVO.setNaverLinkDt(regDateTime);
        naverPlacePlusLinkVO.setModDt(dt);
        naverPlacePlusLinkVO.setModId(naverPlacePlusApiVO.getUserId());

        naverPlacePlusLinkMapper.updateNaverStore(naverPlacePlusLinkVO);

        return resultMap;
    }

    /**
     * 연동 해지 API 호출
     *
     * @param naverPlacePlusApiVO
     * @return
     */
    @Override
    public Map<String, Object> unMappingPlace(NaverPlacePlusApiVO naverPlacePlusApiVO, SessionInfoVO sessionInfoVO) {

        // 네.아.로 Unique ID 조회
        NaverPlacePlusLinkVO naverPlacePlusLinkVO = new NaverPlacePlusLinkVO();
        naverPlacePlusLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverPlacePlusLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        String uniqueId = naverPlacePlusLinkMapper.getNaverUniqueId(naverPlacePlusLinkVO);

        // 개발/운영 Api URL 조회
        naverPlacePlusLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverPlacePlusLinkVO.setApiUrl("API_URL");
        naverPlacePlusLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverPlacePlusLinkMapper.getApiUrl(naverPlacePlusLinkVO);

        // url setting
        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/custom/pos/place-businesses/place-id/" + naverPlacePlusApiVO.getPlaceId() + "/agency-mappings";

        naverPlacePlusApiVO.setAccessToken(getAccessToken(sessionInfoVO.getStoreCd()).get("token").toString());
        naverPlacePlusApiVO.setUniqueId(uniqueId);

        Map<String, Object> resultMap = deleteRequest(naverPlacePlusApiVO, apiFullUrl);

        // 연동 매장 정보 삭제
        String dt = currentDateTimeString();
        naverPlacePlusLinkVO.setNaverStoreNm(null);
        naverPlacePlusLinkVO.setNaverPlaceId(null);
        naverPlacePlusLinkVO.setNaverLinkDt(null);
        naverPlacePlusLinkVO.setModDt(dt);
        naverPlacePlusLinkVO.setModId(sessionInfoVO.getUserId());

        naverPlacePlusLinkMapper.updateNaverStore(naverPlacePlusLinkVO);

        return resultMap;
    }

    /**
     * get 호출
     *
     * @param naverPlacePlusApiVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> getRequest(@RequestBody NaverPlacePlusApiVO naverPlacePlusApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // 0. VO 객체를 Map으로 변환하여 쿼리 파라미터 생성
            Map<String, String> queryParams = convertVoToMap(naverPlacePlusApiVO);

            // 1. URL과 쿼리 파라미터 조합
            String fullUrl = buildUrlWithQueryParams(apiUrl, queryParams);
            URL url = new URL(fullUrl);
            System.out.println("get 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverPlacePlusApiVO.getAccessToken());
            connection.setRequestProperty("X-Naver-Unique-Id", naverPlacePlusApiVO.getUniqueId());
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
     * get 호출 (return List 형태)
     * @param naverPlacePlusApiVO
     * @param apiUrl
     * @return
     */
    public List<Map<String, Object>> getListRequest(@RequestBody NaverPlacePlusApiVO naverPlacePlusApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> resultMap = null;

        try {

            // 0. VO 객체를 Map으로 변환하여 쿼리 파라미터 생성
            Map<String, String> queryParams = convertVoToMap(naverPlacePlusApiVO);

            // 1. URL과 쿼리 파라미터 조합
            String fullUrl = buildUrlWithQueryParams(apiUrl, queryParams);
            URL url = new URL(fullUrl);
            System.out.println("get list 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverPlacePlusApiVO.getAccessToken());
            connection.setRequestProperty("X-Naver-Unique-Id", naverPlacePlusApiVO.getUniqueId());
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
                    resultMap = mapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {
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
                    resultMap = mapper.readValue(errorResponse.toString(), new TypeReference<List<Map<String, Object>>>() {
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

        // 총 리스트 숫자 보내기(페이징을 위해)
        HashMap<String, Object> m = new HashMap<>();
        m.put("totCnt", connection.getHeaderField("X-Total-Count"));
        resultMap.add(m);

        return resultMap;
    }

    /**
     * post 호출
     *
     * @param naverPlacePlusApiVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> postRequest(@RequestBody NaverPlacePlusApiVO naverPlacePlusApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("post 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverPlacePlusApiVO.getAccessToken());
            connection.setRequestProperty("X-Naver-Unique-Id", naverPlacePlusApiVO.getUniqueId());
            connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverPlacePlusApiVO);
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
     *
     * @param naverPlacePlusApiVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> deleteRequest(@RequestBody NaverPlacePlusApiVO naverPlacePlusApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("delete 호출 URL: " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverPlacePlusApiVO.getAccessToken());
            connection.setRequestProperty("X-Naver-Unique-Id", naverPlacePlusApiVO.getUniqueId());
            connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverPlacePlusApiVO);
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

        return resultMap;
    }

    /**
     * VO 객체의 필드를 Map으로 변환하는 헬퍼 메소드
     *
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
            if (!pd.getName().equals("accessToken") && !pd.getName().equals("uniqueId") &&
                    !pd.getName().equals("placeId") && !pd.getName().equals("storeCd")) { // 파라미터로 사용하지 않는 값 제외
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

}
