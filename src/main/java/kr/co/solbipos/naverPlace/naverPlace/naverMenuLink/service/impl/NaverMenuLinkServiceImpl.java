package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuApiVO;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkService;
import kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service.NaverMenuLinkVO;
import kr.co.solbipos.naverPlace.naverPlace.naverOrderLink.service.NaverOrderApiVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : NaverMenuLinkServiceImpl.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.19  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("naverMenuLinkService")
@Transactional
public class NaverMenuLinkServiceImpl implements NaverMenuLinkService {

    public static final String CLIENT_ID = "nEMag45FNxJsZUnX9ywM";
    public static final String CLIENT_SECRET = "wgHGb82SR1";

    private final NaverMenuLinkMapper naverMenuLinkMapper;

    @Autowired
    public NaverMenuLinkServiceImpl(NaverMenuLinkMapper naverMenuLinkMapper) {
        this.naverMenuLinkMapper = naverMenuLinkMapper;
    }

    /**
     * 메뉴(옵션)목록 조회
     */
    @Override
    public List<DefaultMap<Object>> getMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO) {

        NaverMenuApiVO naverMenuApiVO = new NaverMenuApiVO();

        // 네.아.로 Unique ID 조회
        naverMenuLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverMenuLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverMenuApiVO.setUniqueId(naverMenuLinkMapper.getNaverUniqueId(naverMenuLinkVO));

        // 개발/운영 Api URL 조회
        naverMenuLinkVO.setApiInfo("OMS_API_URL");
        naverMenuLinkVO.setApiUrl("API_URL");
        naverMenuLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverMenuLinkMapper.getApiUrl(naverMenuLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/pos-server/shops/" + sessionInfoVO.getStoreCd() + "/menu";

        naverMenuApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = getRequest(naverMenuApiVO, apiFullUrl);

        // 응답값에서 메뉴(옵션)목록 추출 후 저장
        Object statusObj = resultMap.get("status");
        int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 0;

        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
        if (status == 200 && data != null) {

            // 1. 재동기화 전, 기존 옵션 전체 미사용(N) 처리 (API 응답에 없는 옵션은 삭제된 것으로 간주)
            naverMenuLinkVO.setPosShopId(sessionInfoVO.getStoreCd());
            naverMenuLinkMapper.resetMenuOptionUseYn(naverMenuLinkVO);

            List<Map<String, Object>> options = (List<Map<String, Object>>) data.get("options");
            if (options != null && !options.isEmpty()) {
                List<NaverMenuLinkVO> optionList = new ArrayList<>();
                String dt = currentDateTimeString();
                for (Map<String, Object> option : options) {
                    NaverMenuLinkVO optionVO = new NaverMenuLinkVO();
                    optionVO.setPosShopId(sessionInfoVO.getStoreCd());
                    optionVO.setOptionId(String.valueOf(option.get("optionId")));
                    optionVO.setAgencyKey((String) option.get("agencyKey"));
                    optionVO.setName((String) option.get("name"));
                    optionVO.setRegDt(dt);
                    optionVO.setRegId(sessionInfoVO.getUserId());
                    optionVO.setModDt(dt);
                    optionVO.setModId(sessionInfoVO.getUserId());
                    optionList.add(optionVO);
                }
                // 2. 네이버 메뉴(옵션)목록 저장
                naverMenuLinkMapper.saveMenuOptionList(optionList);
            }
        }

        // 3. 저장된 네이버 메뉴(옵션)목록 재조회
        naverMenuLinkVO.setPosShopId(sessionInfoVO.getStoreCd());
        List<DefaultMap<Object>> resultMa2 = naverMenuLinkMapper.getMenuOptionList(naverMenuLinkVO);

        return resultMa2;
    }

    /**
     * 서브메뉴(옵션) 목록 조회
     */
    @Override
    public List<DefaultMap<Object>> getSubMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO) {

        NaverMenuApiVO naverMenuApiVO = new NaverMenuApiVO();

        // 네.아.로 Unique ID 조회
        naverMenuLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverMenuLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverMenuApiVO.setUniqueId(naverMenuLinkMapper.getNaverUniqueId(naverMenuLinkVO));

        // 개발/운영 Api URL 조회
        naverMenuLinkVO.setApiInfo("OMS_API_URL");
        naverMenuLinkVO.setApiUrl("API_URL");
        naverMenuLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverMenuLinkMapper.getApiUrl(naverMenuLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/pos-server/shops/" + sessionInfoVO.getStoreCd() + "/menu/option?optionId=" + naverMenuLinkVO.getOptionId();

        naverMenuApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        Map<String, Object> resultMap = getRequest(naverMenuApiVO, apiFullUrl);

        // 응답값에서 서브메뉴(옵션) 목록 추출
        List<DefaultMap<Object>> subOptionList = new ArrayList<>();

        // 모상품(옵션) 정보(맨 첫줄)
        DefaultMap<Object> optionRow = new DefaultMap<>();
        optionRow.put("posShopId", naverMenuLinkVO.getStoreCd());
        optionRow.put("optionId", naverMenuLinkVO.getOptionId());
        optionRow.put("name", naverMenuLinkVO.getName());
        optionRow.put("agencyKey", naverMenuLinkVO.getAgencyKey());
        optionRow.put("prodCd", naverMenuLinkVO.getProdCd());
        optionRow.put("prodNm", naverMenuLinkVO.getProdNm());
        optionRow.put("subOptionCategoryId", "");
        optionRow.put("subOptionItemSeq", "");

        subOptionList.add(optionRow);

        Object statusObj = resultMap.get("status");
        int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 0;

        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
        if (status == 200 && data != null) {

            List<Map<String, Object>> subOptionCategories = (List<Map<String, Object>>) data.get("subOptionCategories");
            if (subOptionCategories != null) {
                for (Map<String, Object> category : subOptionCategories) {

                    Object subOptionCategoryId = category.get("subOptionCategoryId");

                    List<Map<String, Object>> items = (List<Map<String, Object>>) category.get("items");
                    if (items != null) {
                        for (Map<String, Object> item : items) {

                            String agencyKey = (String) item.get("agencyKey");

                            // 링크포스 상품명 조회
                            String prodNm = "";
                            if (agencyKey != null && !agencyKey.isEmpty()) {
                                NaverMenuLinkVO prodVO = new NaverMenuLinkVO();
                                prodVO.setStoreCd(naverMenuLinkVO.getStoreCd());
                                prodVO.setProdCd(agencyKey);
                                prodNm = naverMenuLinkMapper.getProdNm(prodVO);
                            }

                            DefaultMap<Object> row = new DefaultMap<>();
                            row.put("posShopId", naverMenuLinkVO.getStoreCd());
                            row.put("optionId", naverMenuLinkVO.getOptionId());
                            row.put("name", item.get("name"));
                            row.put("agencyKey", agencyKey);
                            row.put("prodCd", agencyKey);
                            row.put("prodNm", prodNm);
                            row.put("subOptionCategoryId", subOptionCategoryId);
                            row.put("subOptionItemSeq", item.get("subOptionItemSeq"));
                            subOptionList.add(row);
                        }
                    }
                }
            }
        }

        return subOptionList;
    }

    /**
     * 상품목록조회
     */
    @Override
    public List<DefaultMap<Object>> getProdList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO) {

        naverMenuLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        return naverMenuLinkMapper.getProdList(naverMenuLinkVO);
    }

    /**
     * 메뉴 연동·해제
     */
    @Override
    public List<Map<String, Object>> mappingMenuOption(List<Map<String, Object>> mappingList, SessionInfoVO sessionInfoVO) {

        NaverMenuLinkVO naverMenuLinkVO = new NaverMenuLinkVO();
        NaverMenuApiVO naverMenuApiVO = new NaverMenuApiVO();

        // 네.아.로 Unique ID 조회
        naverMenuLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverMenuLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverMenuApiVO.setUniqueId(naverMenuLinkMapper.getNaverUniqueId(naverMenuLinkVO));

        // 개발/운영 Api URL 조회
        naverMenuLinkVO.setApiInfo("OMS_API_URL");
        naverMenuLinkVO.setApiUrl("API_URL");
        naverMenuLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverMenuLinkMapper.getApiUrl(naverMenuLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/pos-server/shops/" + sessionInfoVO.getStoreCd() + "/menu/mapping";

        naverMenuApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        return patchRequestList(naverMenuApiVO, apiFullUrl, mappingList);
    }

    /**
     * 서브메뉴(옵션) 연동·해제
     */
    @Override
    public List<Map<String, Object>> mappingSubMenuOption(List<Map<String, Object>> mappingList, SessionInfoVO sessionInfoVO) {

        NaverMenuLinkVO naverMenuLinkVO = new NaverMenuLinkVO();
        NaverMenuApiVO naverMenuApiVO = new NaverMenuApiVO();

        // 네.아.로 Unique ID 조회
        naverMenuLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        naverMenuLinkVO.setStoreCd(sessionInfoVO.getStoreCd());
        naverMenuApiVO.setUniqueId(naverMenuLinkMapper.getNaverUniqueId(naverMenuLinkVO));

        // 개발/운영 Api URL 조회
        naverMenuLinkVO.setApiInfo("OMS_API_URL");
        naverMenuLinkVO.setApiUrl("API_URL");
        naverMenuLinkVO.setApiKey("ACCESS_TOKEN");
        DefaultMap<Object> apiInfo = naverMenuLinkMapper.getApiUrl(naverMenuLinkVO);

        String apiFullUrl = apiInfo.getStr("apiUrl") + "/pos-server/shops/" + sessionInfoVO.getStoreCd() + "/menu/option/mapping";

        naverMenuApiVO.setAccessToken(apiInfo.getStr("accessToken"));

        return patchRequestList(naverMenuApiVO, apiFullUrl, mappingList);
    }


    // ==================== HTTP 공통 메서드 ====================
    /**
     * get 호출 공통
     *
     * @param naverMenuApiVO
     * @param apiUrl
     * @return
     */
    public Map<String, Object> getRequest(NaverMenuApiVO naverMenuApiVO, String apiUrl) {

        HttpURLConnection connection = null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // 1. URL
            URL url = new URL(apiUrl);
            System.out.println("get 호출 URL : " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverMenuApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverMenuApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);

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
     * patch 호출
     * @param naverMenuApiVO
     * @param apiUrl
     */
    public Map<String, Object> patchRequest(NaverMenuApiVO naverMenuApiVO, String apiUrl) {

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
            connection.setRequestProperty("Authorization", "Bearer " + naverMenuApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverMenuApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(naverMenuApiVO);
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
     * patch 호출 (요청/응답 모두 배열 형태)
     *
     * @param naverMenuApiVO
     * @param apiUrl
     * @param body
     */
    public List<Map<String, Object>> patchRequestList(NaverMenuApiVO naverMenuApiVO, String apiUrl, Object body) {

        HttpURLConnection connection = null;

        // 결과값 셋팅
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            // 1. URL 객체 생성
            URL url = new URL(apiUrl);
            System.out.println("patch(list) 호출 URL : " + url);

            // 2. HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            setPatchMethod(connection);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + naverMenuApiVO.getAccessToken());
            connection.setRequestProperty("unique-id", naverMenuApiVO.getUniqueId());
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("client-secret", CLIENT_SECRET);
            connection.setDoOutput(true); // 서버로 데이터를 전송하려면 이 설정을 true로 해야 합니다.

            // 3. 서버로 데이터 전송 (JSON payload)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonData = mapper.writeValueAsString(body);
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
            if (responseCode >= 200 && responseCode < 300) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    resultList = mapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {});
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
                    System.out.println("에러 응답: " + errorResponse.toString());

                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("status", responseCode);
                    errorMap.put("message", errorResponse.toString());
                    resultList.add(errorMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return resultList;
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
