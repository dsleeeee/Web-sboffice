package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 패스워드 변경 내역 저장
 * table : TB_WB_PWD_CHG_HIST
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PwdChgHistVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    
    /** 사용자 아이디 */
    private String userId;

    /** 인덱스 */
    private Long idx;

    /** 이전 비밀번호 */
    private String priorPwd;

    /** 등록 IP */
    private String regIp;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;
}
