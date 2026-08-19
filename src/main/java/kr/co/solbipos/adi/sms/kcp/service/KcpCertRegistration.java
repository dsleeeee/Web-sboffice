package kr.co.solbipos.adi.sms.kcp.service;

/**
 * {@link KcpCertService#register(String, String, kr.co.solbipos.application.session.auth.service.SessionInfoVO)} 결과다.
 * {@code call_url}, {@code reg_cert_key}, 주문번호 등 팝업 호출값을 담는다.
 */
public class KcpCertRegistration {

    /** 거래등록 후 KCP가 반환한 인증 팝업 호출 URL. */
    private final String callUrl;
    /** 인증창 호출, 콜백 식별 및 서버 간 결과조회에 사용하는 KCP 거래등록키. */
    private final String regCertKey;
    /** 거래등록 요청과 결과조회 요청을 연결하는 KCP 주문번호. */
    private final String ordrIdxx;
    /** 기존 화면의 KCP 팝업 제출 분기와 호환하기 위해 전달하는 값. */
    private final String kcpPageSubmitYn;
    /** KCP param_opt_1에도 보내는 내부 추적 ID이며 콜백 상관관계는 regCertKey로 판단한다. */
    private final String transactionId;

    /** KCP 거래등록 응답과 내부 추적 ID를 불변 결과 객체로 묶는다. */
    public KcpCertRegistration(String callUrl, String regCertKey, String ordrIdxx,
                               String kcpPageSubmitYn, String transactionId) {
        this.callUrl = callUrl;
        this.regCertKey = regCertKey;
        this.ordrIdxx = ordrIdxx;
        this.kcpPageSubmitYn = kcpPageSubmitYn;
        this.transactionId = transactionId;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public String getRegCertKey() {
        return regCertKey;
    }

    public String getOrdrIdxx() {
        return ordrIdxx;
    }

    public String getKcpPageSubmitYn() {
        return kcpPageSubmitYn;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
