package kr.co.common.data.enums;

/**
 * Controller 의 결과에 사용
 *
 * @author 정용길
 *
 */
public enum CodeType {

    /** 본사 환경변수 */
    HQ_ENV("HQ_ENV"),
    /** 매장환경변수 */
    ST_ENV("ST_ENV"),
    /** 공통코드 */
    CMM("CMM");

    /** 응답 결과 ( message code ) */
    String reason;

    CodeType(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
