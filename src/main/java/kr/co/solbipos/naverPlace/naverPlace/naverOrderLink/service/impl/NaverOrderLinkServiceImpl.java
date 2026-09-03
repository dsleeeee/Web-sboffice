package kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : NaverOrderLinkServiceImpl.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.13  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("naverOrderLinkService")
@Transactional
public class NaverOrderLinkServiceImpl implements NaverOrderLinkService {

    public static final String CLIENT_ID = "nEMag45FNxJsZUnX9ywM";
    public static final String CLIENT_SECRET = "wgHGb82SR1";

    private final NaverOrderLinkMapper naverOrderLinkMapper;

    @Autowired
    public NaverOrderLinkServiceImpl(NaverOrderLinkMapper naverOrderLinkMapper) {
        this.naverOrderLinkMapper = naverOrderLinkMapper;
    }

    /**
     * 개발/운영 Api URL 조회
     */
    @Override
    public DefaultMap<Object> getApiUrl(NaverOrderLinkVO naverOrderLinkVO, SessionInfoVO sessionInfoVO) {

        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);
    }

    /**
     * 인증 API Access Token 조회
     */
    @Override
    public Map<String, Object> getAccessToken(String storeCd) {

        HttpURLConnection connection = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        // 개발/운영 Api URL 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setStoreCd(storeCd);
        naverOrderLinkVO.setApiInfo("NAVER_PLACE_AUTH_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiUrl = apiInfo.getStr("apiUrl") + "/auth/v1/token?grant_type=agency_refresh_token&refresh_token=" + apiInfo.getStr("accessToken");

        try {
            URL url = new URL(apiUrl);
            System.out.println("인증 API Access Token 조회 URL: " + url);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(null);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
                    System.out.println("서버 응답: " + response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
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

        return resultMap;
    }

    /**
     * (네이버 주문연동용) 네.아.로 Unique ID 조회
     */
    @Override
    public String getNaverUniqueId(NaverOrderLinkVO naverOrderLinkVO, SessionInfoVO sessionInfoVO) {

        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO);
    }

    /**
     * 동의여부확인 API 호출 (NAVER API)
     */
    @Override
    public Map<String, Object> getAgreeYn(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO) {

        String uniqueId = naverOrderApiVO.getUniqueId();

        // 개발/운영 Api URL 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderLinkVO.setApiInfo("NAVER_PLACE_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/v1/pbp-owner-member/agency-target-summary"
                + "?uniqueId=" + uniqueId
                + "&projections=AGREED_PLACE_PRIVACY_AGREEMENTS,MY_BIZ_AGREEMENT";

        naverOrderApiVO.setAccessToken(getAccessToken(sessionInfoVO.getStoreCd()).get("token").toString());

        Map<String, Object> resultMap = getRequest(naverOrderApiVO, apiFullUrl, "NAVER");

        // 네이버 동의여부 저장
        String dt = currentDateTimeString();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setUniqueId(uniqueId);
        naverOrderLinkVO.setLastResponseDt(dt);
        naverOrderLinkVO.setAgreementType(String.valueOf(resultMap));
        naverOrderLinkVO.setRegDt(dt);
        naverOrderLinkVO.setRegId(sessionInfoVO.getUserId());
        naverOrderLinkVO.setModDt(dt);
        naverOrderLinkVO.setModId(sessionInfoVO.getUserId());

        naverOrderLinkMapper.saveNaverAgreement(naverOrderLinkVO);

        return resultMap;
    }

    /**
     * 업체리스트조회
     */
    @Override
    public Map<String, Object> getPlaceList(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO) {

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops"
                + "?channelType=" + naverOrderApiVO.getChannelType()
                + "&page=" + naverOrderApiVO.getPage()
                + "&size=" + naverOrderApiVO.getSize();

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = getRequest(naverOrderApiVO, apiFullUrl, "ORDERPICK");

        return resultMap;
    }

    /**
     * 매장등록
     */
    @Override
    public Map<String, Object> regPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO){

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops";

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        // 매장정보 조회
        DefaultMap<Object> storeInfo = naverOrderLinkMapper.getStoreInfo(naverOrderLinkVO);
        naverOrderApiVO.setShopName(storeInfo.getStr("storeNm"));
        naverOrderApiVO.setTaxNo(storeInfo.getStr("bizNo"));
        naverOrderApiVO.setCeoName(storeInfo.getStr("ownerNm"));
        naverOrderApiVO.setShopTelNo(storeInfo.getStr("telNo"));
        naverOrderApiVO.setCeoTelNo(storeInfo.getStr("telNo"));
        naverOrderApiVO.setPostNo(storeInfo.getStr("postNo"));
        naverOrderApiVO.setAddrBase(storeInfo.getStr("addr"));
        naverOrderApiVO.setAddrDetail(storeInfo.getStr("addrDtl"));

        Map<String, Object> resultMap = postRequest(naverOrderApiVO, apiFullUrl);

        // API 응답값 추출하여 내부 DB 저장
        Object statusObj = resultMap.get("status");
        int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 0;

        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
        if (status == 201 && data != null) {

            String channelShopId = (String) data.get("channelShopId");
            String tableChannelServiceId = null;
            String pickupChannelServiceId = null;

            List<Map<String, Object>> services = (List<Map<String, Object>>) data.get("services");
            if (services != null) {
                for (Map<String, Object> service : services) {

                    String serviceType = (String) service.get("serviceType");
                    Object channelServiceIdObj = service.get("channelServiceId");
                    String channelServiceId = channelServiceIdObj != null ? channelServiceIdObj.toString() : null;

                    if ("TABLE".equals(serviceType)) {
                        tableChannelServiceId = channelServiceId;
                    } else if ("PICKUP".equals(serviceType)) {
                        pickupChannelServiceId = channelServiceId;
                    }
                }
            }

            // 네이버 주문연동 정보 저장
            String dt = currentDateTimeString();
            naverOrderLinkVO.setModDt(dt);
            naverOrderLinkVO.setModId(sessionInfoVO.getUserId());
            naverOrderLinkVO.setOrderBusinessId(channelShopId);
            naverOrderLinkVO.setOrderChannelServiceIdTable(tableChannelServiceId);
            naverOrderLinkVO.setOrderChannelServiceIdPickup(pickupChannelServiceId);
            naverOrderLinkMapper.saveNaverOrderLink(naverOrderLinkVO);
        }

        return resultMap;
    }

    /**
     * 매장수정(미사용)
     */
    @Override
    public Map<String, Object> modPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO){

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops";

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = patchRequest(naverOrderApiVO, apiFullUrl);

        return resultMap;
    }

    /**
     * 매장 단건조회
     */
    @Override
    public Map<String, Object> getPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO) {

        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());

        // Unique ID가 없는 경우
        if ("".equals(naverOrderApiVO.getUniqueId()) || naverOrderApiVO.getUniqueId() == null) {
            // 네.아.로 Unique ID 조회
            naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));
        }

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops/" + sessionInfoVO.getStoreCd()
                + "?channelType=" + naverOrderApiVO.getChannelType();

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = getRequest(naverOrderApiVO, apiFullUrl, "ORDERPICK");

        return resultMap;
    }

    /**
     * 서비스 활성화/비활성화
     */
    @Override
    public Map<String, Object> regServiceActive(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO){

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops/service-activation";

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        // 네이버 주문 유형 ChannelServiceId 조회
        DefaultMap<Object> channelServiceIdInfo = naverOrderLinkMapper.getNaverOrderChannelServiceId(naverOrderLinkVO);
        String tableChannelServiceId = channelServiceIdInfo.getStr("orderChannelServiceIdTable");
        String pickupChannelServiceId = channelServiceIdInfo.getStr("orderChannelServiceIdPickup");

        // API 호출 데이터 셋팅
        if (naverOrderApiVO.getServices() != null) {
            for (NaverOrderApiVO.ServiceItem service : naverOrderApiVO.getServices()) {
                if ("TABLE".equals(service.getServiceType())) {
                    service.setChannelServiceId(Long.valueOf(tableChannelServiceId));
                } else if ("PICKUP".equals(service.getServiceType())) {
                    service.setChannelServiceId(Long.valueOf(pickupChannelServiceId));
                }
            }
        }

        Map<String, Object> resultMap = postRequest(naverOrderApiVO, apiFullUrl);

        return resultMap;
    }

    /**
     * 매핑해제
     */
    @Override
    public Map<String, Object> delPlace(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO){

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/api/v1/external/pos-server/shops/channel-mapping";

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = deleteRequest(naverOrderApiVO, apiFullUrl);

        // 매핑해제 성공 시, 주문연동 매핑 정보 초기화
        Object statusObj = resultMap.get("status");
        int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 0;

        if (status == 200) {
            String dt = currentDateTimeString();
            naverOrderLinkVO.setModDt(dt);
            naverOrderLinkVO.setModId(sessionInfoVO.getUserId());
            naverOrderLinkVO.setOrderBusinessId(null);
            naverOrderLinkVO.setOrderChannelServiceIdTable(null);
            naverOrderLinkVO.setOrderChannelServiceIdPickup(null);
            naverOrderLinkMapper.saveNaverOrderLink(naverOrderLinkVO);
        }

        return resultMap;
    }

    /**
     * 서비스별 일시중지 상태 조회
     */
    @Override
    public Map<String, Object> getServiceActive(NaverOrderApiVO naverOrderApiVO, SessionInfoVO sessionInfoVO) {

        // 네.아.로 Unique ID 조회
        NaverOrderLinkVO naverOrderLinkVO = new NaverOrderLinkVO();
        naverOrderLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverOrderLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverOrderApiVO.setUniqueId(naverOrderLinkMapper.getNaverUniqueId(naverOrderLinkVO));

        // 개발/운영 Api URL 조회
        naverOrderLinkVO.setApiInfo("OMS_API_URL");
        naverOrderLinkVO.setApiUrl("API_URL");
        naverOrderLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverOrderLinkMapper.getApiUrl(naverOrderLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/pos-server/shops/" + sessionInfoVO.getStoreCd() + "/naver/services";

        naverOrderApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = getRequest(naverOrderApiVO, apiFullUrl, "ORDERPICK");

        return resultMap;
    }

    // ==================== HTTP 공통 메서드 ====================
    /**
     * get 호출 공통 (apiType: "NAVER" | "ORDERPICK")
     *
     * @param naverOrderApiVO
     * @param apiUrl
     * @param apiType
     * @return
     */
    public Map<String, Object> getRequest(NaverOrderApiVO naverOrderApiVO, String apiUrl, String apiType) {

        HttpURLConnection connection = null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // 1. URL
            URL url = new URL(apiUrl);
            System.out.println("get 호출 URL (" + apiType + "): " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverOrderApiVO.getAccessToken());

            // API 종류별 헤더 분기
            if ("NAVER".equals(apiType)) {
                connection.setRequestProperty("X-Naver-Unique-Id", naverOrderApiVO.getUniqueId());
                connection.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
                connection.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            } else if ("ORDERPICK".equals(apiType)) {
                connection.setRequestProperty("unique-id", naverOrderApiVO.getUniqueId());
                connection.setRequestProperty("client-id", CLIENT_ID);
                connection.setRequestProperty("client-secret", CLIENT_SECRET);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resultMap = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
                    System.out.println("서버 응답: " + response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine = null;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
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

        return resultMap;
    }

    /**
     * post 호출
     * @param naverOrderApiVO
     * @param apiUrl
     */
    public Map<String, Object> postRequest(NaverOrderApiVO naverOrderApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("post 호출 URL : " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverOrderApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverOrderApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverOrderApiVO);
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

        return resultMap;
    }

    /**
     * patch 호출
     * @param naverOrderApiVO
     * @param apiUrl
     */
    public Map<String, Object> patchRequest(NaverOrderApiVO naverOrderApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = null;

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("patch 호출 URL : " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            setPatchMethod(connection);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverOrderApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverOrderApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverOrderApiVO);
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

        return resultMap;
    }

    /**
     * delete 호출
     *
     * @param naverOrderApiVO
     * @param apiUrl
     */
    public Map<String, Object> deleteRequest(NaverOrderApiVO naverOrderApiVO, String apiUrl) {

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
            connection.setRequestProperty("Authorization", "Bearer " + naverOrderApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverOrderApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverOrderApiVO);
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

        //
        return resultMap;
    }

    /**
     * JDK의 HttpURLConnection은 PATCH 메서드를 화이트리스트에 두지 않아
     * setRequestMethod("PATCH") 호출 시 ProtocolException이 발생한다.
     * 리플렉션으로 내부 method 필드를 직접 세팅해서 우회한다.
     */
    private void setPatchMethod(HttpURLConnection connection) throws Exception {

        Object target = connection;

        // HTTPS 연결은 실제 method 필드가 내부 delegate 객체에 있음
        if (connection instanceof HttpsURLConnection) {
            try {
                Field delegateField = connection.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                target = delegateField.get(connection);
            } catch (NoSuchFieldException e) {
                Field delegateField = connection.getClass().getSuperclass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                target = delegateField.get(connection);
            }
        }

        try {
            Field methodField = HttpURLConnection.class.getDeclaredField("method");
            methodField.setAccessible(true);
            methodField.set(target, "PATCH");
        } catch (NoSuchFieldException e) {
            Field methodField = target.getClass().getSuperclass().getDeclaredField("method");
            methodField.setAccessible(true);
            methodField.set(target, "PATCH");
        }
    }

}
