package kr.co.solbipos.sys.domain.auth.authgroup;

import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.sys.enums.IncldExcldFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리 - 리소스 정보
 * <br>TB_WB_AUTHOR_EXCEPT
 * @author 조병준
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorExcept extends Cmm {

    private static final long serialVersionUID = 1L;

    /** 사용자 아이디 */
    private String userId;

    /** 리소스 코드 */
    private String resrceCd;

    /** 포함/제외 여부 */
    private IncldExcldFg incldExcldFg;

    /** 사용여부 */
    private UseYn useYn;
}
