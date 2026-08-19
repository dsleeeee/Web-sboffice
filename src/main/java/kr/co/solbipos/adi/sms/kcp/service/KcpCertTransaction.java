package kr.co.solbipos.adi.sms.kcp.service;

import java.io.Serializable;

/**
 * Redis에 TTL로 보관하는 요청자·목적·주문번호 거래정보다.
 * DI, CI, 전화번호 같은 복호화 개인정보는 저장하지 않는다.
 */
public class KcpCertTransaction implements Serializable {

    private static final long serialVersionUID = 2209354069584989818L;

    /** 거래등록을 마치고 KCP 콜백을 기다리는 상태. */
    public static final String STATUS_PENDING = "PENDING";
    /** 결과조회 처리권을 얻어 콜백을 처리 중인 상태. */
    public static final String STATUS_PROCESSING = "PROCESSING";

    /** 애플리케이션 내부 추적용 ID. KCP 요청의 param_opt_1에도 전달하지만 콜백 조회키로 의존하지 않는다. */
    private final String transactionId;
    /** KcpCertService를 사용하는 화면을 구분하고 다른 용도의 콜백 재사용을 막는 값. */
    private final String purpose;
    /** 거래등록을 요청한 로그인 사용자 ID. */
    private final String userId;
    /** 거래등록을 요청한 사용자의 소속코드. */
    private final String orgnCd;
    /** 거래등록 시점의 애플리케이션 세션 ID. */
    private final String sessionId;
    /** KCP 거래등록과 결과조회 양쪽에 동일하게 전달하는 주문번호. */
    private final String ordrIdxx;
    /** 현재 내부 처리 상태({@link #STATUS_PENDING}, {@link #STATUS_PROCESSING}). */
    private final String status;
    /** 거래 생성 시각(epoch milliseconds). */
    private final long createdAt;
    /** Redis TTL과 함께 검사하는 논리 만료 시각(epoch milliseconds). */
    private final long expiresAt;

    /** 거래등록 시점의 요청자, 주문번호, 상태와 유효시간을 초기화한다. */
    public KcpCertTransaction(String transactionId, String purpose, String userId, String orgnCd,
                              String sessionId, String ordrIdxx, String status,
                              long createdAt, long expiresAt) {
        this.transactionId = transactionId;
        this.purpose = purpose;
        this.userId = userId;
        this.orgnCd = orgnCd;
        this.sessionId = sessionId;
        this.ordrIdxx = ordrIdxx;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    /** 원본 거래정보는 유지하고 상태만 바꾼 불변 객체를 반환한다. */
    public KcpCertTransaction withStatus(String newStatus) {
        return new KcpCertTransaction(transactionId, purpose, userId, orgnCd, sessionId,
                ordrIdxx, newStatus, createdAt, expiresAt);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getOrdrIdxx() {
        return ordrIdxx;
    }

    public String getStatus() {
        return status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
