package kr.co.solbipos.sys.domain.auth.authgroup;

import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.sys.enums.TargetAllFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthGroup {

    /** 그룹 코드 */
    private String grpCd;

    /** 그룹 명 */
    private String grpNm;

    /** 전체 적용 구분 */
    private TargetAllFg targetAllFg;

    /** 대상 소속 */
    private String targetOrgn;

    /** 비고 */
    private String remark;

    /** 사용여부 */
    private UseYn useYn;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;
}
