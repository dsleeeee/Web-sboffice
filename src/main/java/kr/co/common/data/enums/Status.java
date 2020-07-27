package kr.co.common.data.enums;

/**
 * Controller 의 결과에 사용
 * 
 * @author 정용길
 *
 */
public enum Status {

    /** 성공 */
    OK("OK"),
    /** 실패 */
    FAIL("FAIL"),
    /** 테이블명실패 */
    TBLNM_FAIL("TBLNM_FAIL"),
    /** 세션 종료 */
    SESSION_EXFIRE("SESSION_EXFIRE"),
    /** 서버 에러 */
    SERVER_ERROR("SERVER_ERROR");

    /** 응답 결과 ( message code ) */
    String reason;

    Status(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
