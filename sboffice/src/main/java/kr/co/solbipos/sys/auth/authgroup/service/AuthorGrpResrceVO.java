package kr.co.solbipos.sys.auth.authgroup.service;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리 - 리소스 정보
 * <br>TB_WB_AUTHOR_GRP_RESRCE
 * @author 조병준
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorGrpResrceVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 그룹 코드 */
    private String authGrpCd;

    /** 리소스 코드 */
    private String resrceCd;

    /** 사용여부 */
    private UseYn useYn;


    /** 상위 리소스 */
    private String pResrce;

    /** 리소스 명 */
    private String resrceNm;

    /** URL */
    private String url;

    /** 리소스 화면 표시 내용 */
    private String resrceDisp;

    /** 권한 보유 여부 */
    private Boolean authFg;

    /** 최상단 메뉴 아이콘 명 */
    private String iconNm;

    /** Child Items */
    private List<AuthorGrpResrceVO> items;
}
