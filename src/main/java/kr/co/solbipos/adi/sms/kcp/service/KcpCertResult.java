package kr.co.solbipos.adi.sms.kcp.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link KcpCertService#getResult(String, String)}가 반환하는 복호화 결과다.
 * Redis 거래정보와 DI·CI·전화번호·이름 등의 인증 데이터를 제공한다.
 */
public class KcpCertResult {

    /** 거래등록 시 Redis에 저장해 둔 요청자 및 주문 정보. */
    private final KcpCertTransaction transaction;
    /** KCP의 암호화 결과를 서버에서 복호화한 본인확인 데이터. */
    private final Map<String, Object> certData;

    /** Redis 거래정보와 복호화된 인증 데이터를 읽기 전용 결과로 만든다. */
    public KcpCertResult(KcpCertTransaction transaction, Map<String, Object> certData) {
        this.transaction = transaction;
        // 호출 화면이 KcpCertResult의 인증 데이터를 변경하지 못하도록 복사 후 읽기 전용으로 노출한다.
        this.certData = Collections.unmodifiableMap(new LinkedHashMap<String, Object>(certData));
    }

    public KcpCertTransaction getTransaction() {
        return transaction;
    }

    public Map<String, Object> getCertData() {
        return certData;
    }
}
