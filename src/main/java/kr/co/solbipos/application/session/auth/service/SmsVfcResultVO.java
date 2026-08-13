package kr.co.solbipos.application.session.auth.service;
/**
 * @Class Name : SmsVfcResultVO.java
 * @Description : SMS 인증 함수 처리 결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

/** SMS 인증 함수 처리 결과 */
public class SmsVfcResultVO {

    /** DB 함수의 처리 결과코드 (00: 성공, 그 외 실패/제한 사유) */
    private final String code;
    /** 사용자 화면에 표시할 DB 함수의 결과 메시지 */
    private final String message;
    /** 인증번호 요청으로 SMS가 실제 발송되어 타이머를 시작해야 하는지 여부 */
    private final boolean sent;

    public SmsVfcResultVO(String code, String message) {
        this(code, message, false);
    }

    public SmsVfcResultVO(String code, String message, boolean sent) {
        this.code = code;
        this.message = message;
        this.sent = sent;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSent() {
        return sent;
    }

    public boolean isSuccess() {
        return "00".equals(code);
    }
}
