package kr.co.solbipos.adi.sms.kcp.service;

/**
 * KCP 거래등록·결과조회와 Redis 거래 처리 오류를 분류하는 예외다.
 * 내부 errorCode와 KCP res_cd인 kcpResultCode를 구분한다.
 */
public class KcpCertException extends RuntimeException {

    private static final long serialVersionUID = 5070436491859593520L;

    /** 컨트롤러와 운영 로그에서 사용하는 내부 오류 분류 코드. */
    private final String errorCode;
    /** KCP 응답 원문 중 결과코드({@code res_cd}); 내부 오류이면 {@code null}. */
    private final String kcpResultCode;

    /** 내부 오류코드와 사용자용 메시지만 있는 예외를 만든다. */
    public KcpCertException(String errorCode, String message) {
        this(errorCode, message, null, null);
    }

    /** 내부 오류코드와 원인 예외를 보존하는 예외를 만든다. */
    public KcpCertException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, null, cause);
    }

    /** KCP res_cd를 함께 보존하는 거절 예외를 만든다. */
    public KcpCertException(String errorCode, String message, String kcpResultCode) {
        this(errorCode, message, kcpResultCode, null);
    }

    /** 내부 코드, KCP 결과코드와 원인 예외를 모두 초기화한다. */
    public KcpCertException(String errorCode, String message, String kcpResultCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.kcpResultCode = kcpResultCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getKcpResultCode() {
        return kcpResultCode;
    }
}
