package kr.co.solbipos.orderkit.orderkit.orderkit.web;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
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

    private final SessionService sessionService;
    private final OrderkitService orderkitService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String SECRET_KEY_STRING = "q7TgPmKtjGNMtzOfy4dsF/Ti3whTXeQZwaw84vnA9N8m8zvNU2QApYZquYZlq94uczwj9x12OSzAYiDjzEUaog==";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 1800000; // 30분 (밀리초)

    /**
     * Constructor Injection
     */
    @Autowired
    public OrderkitController(SessionService sessionService, OrderkitService orderkitService) {
        this.sessionService = sessionService;
        this.orderkitService = orderkitService;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // JWT 토큰 생성
        String token = createJWT(sessionInfoVO);
        LOGGER.info("Crate JWT Token: " + token);

        // JWT 토큰 파싱(확인용)
        parseJWT(token);

        model.addAttribute("jwtToken", token);

        return "orderkit/orderkit/orderkit/orderkit";
    }

    // JWT 토큰 생성
    public String createJWT(SessionInfoVO sessionInfoVO) {


        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        long now_unixTimestamp = now.getTime() / 1000;
        long expiration_unixTimestamp = expiration.getTime() / 1000;

        // 매장정보 조회
        OrderkitVO orderkitVO = new OrderkitVO();
        DefaultMap<Object> storeInfo = orderkitService.getStoreInfo(orderkitVO, sessionInfoVO);

        return Jwts.builder()

                // 헤더 설정
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // 페이로드(클레임) 설정
                .claim("businessNumber", storeInfo.getStr("bizNo"))
                .claim("representativeName", storeInfo.getStr("ownerNm"))
                .claim("phoneNumber", storeInfo.getStr("mpNo"))
                .claim("posShopId", storeInfo.getStr("storeCd"))
                .claim("isOmsUser", false)
                .claim("iat", now_unixTimestamp)         // iat: 토큰 발행 시간
                .claim("exp", expiration_unixTimestamp)  // exp: 토큰 만료 시간
                .setIssuedAt(now)                           // iat: 토큰 발행 시간
                .setExpiration(expiration)                  // exp: 토큰 만료 시간

                // 서명 알고리즘 및 키 설정
                //.signWith(SECRET_KEY)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)

                .compact();
    }

    // JWT 토큰 파싱
    public void parseJWT(String token) {

        try {

            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(SECRET_KEY) // 서명 검증을 위해 시크릿 키 설정
                    .build()
                    .parseSignedClaims(token); // 서명된 JWT 파싱

            // 페이로드(클레임) 가져오기
            Claims claims = jws.getPayload();
            LOGGER.info("businessNumber: " + claims.get("businessNumber"));
            LOGGER.info("representativeName: " + claims.get("representativeName"));
            LOGGER.info("phoneNumber: " + claims.get("phoneNumber"));
            LOGGER.info("posShopId: " + claims.get("posShopId"));
            LOGGER.info("isOmsUser: " + claims.get("isOmsUser"));
            LOGGER.info("iat: " + claims.get("iat"));
            LOGGER.info("exp: " + claims.get("exp"));

        } catch (JwtException e) {
            // 서명 검증 실패, 토큰 만료 등 유효하지 않은 토큰일 경우 예외 발생
            LOGGER.error("JWT 파싱 또는 유효성 검증 실패: " + e.getMessage());
        }
    }
}
