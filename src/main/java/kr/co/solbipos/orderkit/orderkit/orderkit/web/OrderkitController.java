package kr.co.solbipos.orderkit.orderkit.orderkit.web;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.orderkit.orderkit.orderkit.service.OrderkitService;
import kr.co.solbipos.orderkit.orderkit.orderkit.service.OrderkitVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * @Class Name  : OrderkitController.java
 * @Description : 오더킷 > 오더킷 > 오더킷
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/orderkit/orderkit/orderkit")
public class OrderkitController {

    private final OrderkitService orderkitService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final long EXPIRATION_TIME = 30 * 1000; // 30초

    /**
     * Constructor Injection
     */
    @Autowired
    public OrderkitController(OrderkitService orderkitService) {
        this.orderkitService = orderkitService;
    }

    /**
     * 페이지 이동 ([오더킷]-[오더킷]-[오더킷])
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "orderkit/orderkit/orderkit/orderkit";
    }

    // JWT 토큰 생성
    public DefaultMap<String> createJWT(SessionInfoVO sessionInfoVO) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        long now_unixTimestamp = now.getTime() / 1000;
        long expiration_unixTimestamp = expiration.getTime() / 1000;

        // 개발/운영 Api URL 조회
        OrderkitVO orderkitVO = new OrderkitVO();
        orderkitVO.setApiInfo("ORDERKIT_OMS_WEB_URL");
        orderkitVO.setApiUrl("API_URL");
        orderkitVO.setApiKey("SECRET_KEY");
        DefaultMap<Object> apiInfo = orderkitService.getApiUrl(orderkitVO, sessionInfoVO);

        // secretKey 생성
        Key secretKey = Keys.hmacShaKeyFor(apiInfo.getStr("secretKey").getBytes(StandardCharsets.UTF_8));

        // 매장정보 조회
        DefaultMap<Object> storeInfo = orderkitService.getStoreInfo(orderkitVO, sessionInfoVO);

        // 토큰 생성
        String token = Jwts.builder()

                // 헤더 설정
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // 페이로드(클레임) 설정
                .claim("shopName", storeInfo.getStr("storeNm"))
                .claim("businessNumber", storeInfo.getStr("bizNo"))
                .claim("representativeName", storeInfo.getStr("ownerNm"))
                .claim("phoneNumber", storeInfo.getStr("mpNo"))
                .claim("shopTelNo", storeInfo.getStr("telNo"))
                .claim("postNo", storeInfo.getStr("postNo"))
                .claim("addressBase", storeInfo.getStr("addr"))
                .claim("addressDetail", storeInfo.getStr("addrDtl"))
                .claim("posType", "SOLBI")
                .claim("posShopId", storeInfo.getStr("storeCd"))
                .claim("isOmsUser", false)
                .claim("iat", now_unixTimestamp)         // iat: 토큰 발행 시간
                .claim("exp", expiration_unixTimestamp)  // exp: 토큰 만료 시간
                .setIssuedAt(now)                           // iat: 토큰 발행 시간
                .setExpiration(expiration)                  // exp: 토큰 만료 시간

                // 서명 알고리즘 및 키 설정
                //.signWith(SECRET_KEY)
                .signWith(SignatureAlgorithm.HS256, secretKey)

                .compact();

        // return 값 셋팅
        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("token", token);
        resultMap.put("url", apiInfo.getStr("apiUrl"));

        return resultMap;
    }

    // JWT 토큰 파싱
    public void parseJWT(SessionInfoVO sessionInfoVO, String token) {

        // 개발/운영 Api URL 조회
        OrderkitVO orderkitVO = new OrderkitVO();
        orderkitVO.setApiInfo("ORDERKIT_OMS_WEB_URL");
        orderkitVO.setApiUrl("API_URL");
        orderkitVO.setApiKey("SECRET_KEY");
        DefaultMap<Object> apiInfo = orderkitService.getApiUrl(orderkitVO, sessionInfoVO);

        // secretKey 생성
        Key secretKey = Keys.hmacShaKeyFor(apiInfo.getStr("secretKey").getBytes(StandardCharsets.UTF_8));

        try {

            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(secretKey) // 서명 검증을 위해 시크릿 키 설정
                    .build()
                    .parseSignedClaims(token); // 서명된 JWT 파싱

            // 페이로드(클레임) 가져오기
            Claims claims = jws.getPayload();
            LOGGER.info("shopName: " + claims.get("shopName"));
            LOGGER.info("businessNumber: " + claims.get("businessNumber"));
            LOGGER.info("representativeName: " + claims.get("representativeName"));
            LOGGER.info("phoneNumber: " + claims.get("phoneNumber"));
            LOGGER.info("shopTelNo: " + claims.get("shopTelNo"));
            LOGGER.info("postNo: " + claims.get("postNo"));
            LOGGER.info("addressBase: " + claims.get("addressBase"));
            LOGGER.info("addressDetail: " + claims.get("addressDetail"));
            LOGGER.info("posType: " + claims.get("posType"));
            LOGGER.info("posShopId: " + claims.get("posShopId"));
            LOGGER.info("isOmsUser: " + claims.get("isOmsUser"));
            LOGGER.info("iat: " + claims.get("iat"));
            LOGGER.info("exp: " + claims.get("exp"));

        } catch (JwtException e) {
            // 서명 검증 실패, 토큰 만료 등 유효하지 않은 토큰일 경우 예외 발생
            LOGGER.error("JWT 파싱 또는 유효성 검증 실패: " + e.getMessage());
        }
    }

    // JWT 토큰 생성 - 자동 로그인 및 페이지 이동
    public DefaultMap<String> createJWT2(SessionInfoVO sessionInfoVO, String redirectUrl) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        long now_unixTimestamp = now.getTime() / 1000;
        long expiration_unixTimestamp = expiration.getTime() / 1000;

        // 개발/운영 Api URL 조회
        OrderkitVO orderkitVO = new OrderkitVO();
        orderkitVO.setApiInfo("ORDERKIT_OMS_WEB_URL");
        orderkitVO.setApiUrl("API_URL");
        orderkitVO.setApiKey("SECRET_KEY");
        DefaultMap<Object> apiInfo = orderkitService.getApiUrl(orderkitVO, sessionInfoVO);

        // secretKey 생성
        Key secretKey = Keys.hmacShaKeyFor(apiInfo.getStr("secretKey").getBytes(StandardCharsets.UTF_8));

        // 매장정보 조회
        DefaultMap<Object> storeInfo = orderkitService.getStoreInfo(orderkitVO, sessionInfoVO);

        // 토큰 생성
        String token = Jwts.builder()

                // 헤더 설정
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // 페이로드(클레임) 설정
                .claim("posType", "SOLBI")
                .claim("posShopId", storeInfo.getStr("storeCd"))
                .claim("redirectUrl", redirectUrl)
                .claim("iat", now_unixTimestamp)         // iat: 토큰 발행 시간
                .claim("exp", expiration_unixTimestamp)  // exp: 토큰 만료 시간
                .setIssuedAt(now)                           // iat: 토큰 발행 시간
                .setExpiration(expiration)                  // exp: 토큰 만료 시간

                // 서명 알고리즘 및 키 설정
                //.signWith(SECRET_KEY)
                .signWith(SignatureAlgorithm.HS256, secretKey)

                .compact();

        // return 값 셋팅
        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("token", token);
        resultMap.put("url", apiInfo.getStr("apiUrl"));

        return resultMap;
    }

    // JWT 토큰 파싱 - 자동 로그인 및 페이지 이동
    public void parseJWT2(SessionInfoVO sessionInfoVO, String token) {

        // 개발/운영 Api URL 조회
        OrderkitVO orderkitVO = new OrderkitVO();
        orderkitVO.setApiInfo("ORDERKIT_OMS_WEB_URL");
        orderkitVO.setApiUrl("API_URL");
        orderkitVO.setApiKey("SECRET_KEY");
        DefaultMap<Object> apiInfo = orderkitService.getApiUrl(orderkitVO, sessionInfoVO);

        // secretKey 생성
        Key secretKey = Keys.hmacShaKeyFor(apiInfo.getStr("secretKey").getBytes(StandardCharsets.UTF_8));

        try {

            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(secretKey) // 서명 검증을 위해 시크릿 키 설정
                    .build()
                    .parseSignedClaims(token); // 서명된 JWT 파싱

            // 페이로드(클레임) 가져오기
            Claims claims = jws.getPayload();
            LOGGER.info("posType: " + claims.get("posType"));
            LOGGER.info("posShopId: " + claims.get("posShopId"));
            LOGGER.info("redirectUrl: " + claims.get("redirectUrl"));
            LOGGER.info("iat: " + claims.get("iat"));
            LOGGER.info("exp: " + claims.get("exp"));

        } catch (JwtException e) {
            // 서명 검증 실패, 토큰 만료 등 유효하지 않은 토큰일 경우 예외 발생
            LOGGER.error("JWT2 파싱 또는 유효성 검증 실패: " + e.getMessage());
        }
    }
}
