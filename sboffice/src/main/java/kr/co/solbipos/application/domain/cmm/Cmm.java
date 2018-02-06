package kr.co.solbipos.application.domain.cmm;

import kr.co.solbipos.application.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 테이블 공통 도메인 클래스<br>
 * 등록, 수정 일시<br>
 * 등록, 수정 아이디
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Cmm extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아이디 */
    private String modId;
}
