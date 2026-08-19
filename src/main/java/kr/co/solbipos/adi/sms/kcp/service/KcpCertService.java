package kr.co.solbipos.adi.sms.kcp.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 여러 SMS 화면이 공유하는 NHN KCP 본인확인 V2 서비스 계약이다.
 * 거래등록, Redis 거래 연결과 서버 간 결과조회 절차를 제공한다.
 */
public interface KcpCertService {

    /** SMS 사용자 등록/수정 화면의 본인확인 목적. */
    String PURPOSE_SMS_USER_REGIST = "SMS_USER_REGIST";
    /** SMS 발신번호 등록 화면의 본인확인 목적. */
    String PURPOSE_SMS_TEL_NO_REGIST = "SMS_TEL_NO_REGIST";
    /** 마케팅 문자 발신번호 확인 첫 번째 화면의 목적. */
    String PURPOSE_MARKETING_VERIFY = "MARKETING_VERIFY";
    /** 마케팅 문자 발신번호 확인 두 번째 화면의 목적. */
    String PURPOSE_MARKETING_VERIFY2 = "MARKETING_VERIFY2";

    /**
     * KCP 거래등록 API를 호출한다.
     * 성공 시 팝업 호출값을 반환하고 Redis 거래를 TTL 저장한다.
     *
     * @param purpose 호출 화면의 인증 목적 상수
     * @param returnPath KCP 인증 후 돌아올 애플리케이션 콜백 경로
     * @param sessionInfoVO 거래를 요청한 로그인 사용자 정보
     * @return 인증창 URL, 거래등록키, 주문번호 등을 담은 거래등록 결과
     */
    KcpCertRegistration register(String purpose, String returnPath, SessionInfoVO sessionInfoVO);

    /**
     * Redis 거래 처리권을 획득한 뒤 KCP 인증결과를 서버 간 조회·복호화한다.
     * 호출자는 업무 저장 후 {@link #removeByRegCertKey(String)}를 수행한다.
     *
     * @param callbackRegCertKey KCP 콜백의 {@code reg_cert_key}
     * @param expectedPurpose 현재 콜백 URL이 처리할 인증 목적
     * @return Redis 거래정보와 복호화된 본인확인 결과
     */
    KcpCertResult getResult(String callbackRegCertKey, String expectedPurpose);

    /**
     * 결과조회 없이 Redis의 요청자·목적 거래정보만 확인한다.
     * KCP 실패 콜백 처리에서 사용한다.
     */
    KcpCertTransaction getTransaction(String callbackRegCertKey, String expectedPurpose);

    /** 업무 처리가 끝난 거래정보와 중복 처리 방지 claim을 함께 삭제한다. */
    void removeByRegCertKey(String callbackRegCertKey);

    /** KCP 요청에서 사용할 21자리 주문번호를 새로 만든다. */
    String newOrderId();
}
