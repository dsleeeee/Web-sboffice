package kr.co.solbipos.application.domain.user;

import kr.co.solbipos.application.domain.cmm.Cmm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 패스워드 변경시에 otp 저장
 * table : TB_WB_OTP_AUTH_HIST
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OtpAuth extends Cmm {

    private static final long serialVersionUID = 1L;

    /** 순서 */
    private String seq;

    /** 사용자 아이디 */
    private String userId;

    /** 인증 구분(904) */
    private String authFg;

    /** 인증 번호 */
    private String authNo;

    /** 수신 휴대폰 번호 */
    private String recvMpNo;

    /** 요청 IP */
    private String reqIp;

    /** 요청 일자 */
    private String reqDate;

    /** 요청 일시 */
    private String reqDt;

    /** 요청 일시 + 분 */
    private int otpLimit;
}
