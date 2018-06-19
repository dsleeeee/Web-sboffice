package kr.co.solbipos.application.session.auth.service;

import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.validate.Login;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 레디스 세션에 저장 되는 객체
 *
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SessionInfoVO extends CmmVO {

    private static final long serialVersionUID = -7358789950591364589L;

    /** sessionId */
    private String sessionId;

    /** 사용자 아이디 */
    @NotBlank(groups = {Login.class}, message = "{login.userId}{cmm.require.text}")
    @Size(groups = {Login.class}, max = 20, message = "{cmm.size.max}")
    private String userId;

    /** 사용자 비밀번호 */
    @NotBlank(groups = {Login.class}, message = "{login.userPasswd}{cmm.require.text}")
    @Size(groups = {Login.class}, min = 4, max = 25, message = "{cmm.size.max}")
    private String userPwd;

    /** 사용자 이름 */
    private String userNm;

    /** 그룹 코드 */
    private String authGrpCd;

    /**
     * 본사는 소속된 가맹점<br>
     * {@link OrgnFg} {@code HQ} 타입만 저장
     * */
    private List<String> arrStoreCdList;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private OrgnFg orgnFg;

    /**
     * 소속 코드<br>
     * 테이블 마다 쓰이는 컬럼이 다르다<br>
     * {@link OrgnFg} type:{@code H}, table:{@code TB_HQ_EMPLOYEE}(본사), column:{@code HQ_OFFICE_CD}(본사사업장코드)<br>
     * {@link OrgnFg} type:{@code S}, table:{@code TB_MS_EMPLOYEE}(가맹점), column:{@code STORE_CD}(본사사업장코드)<br>
     * {@link OrgnFg} type:{@code M,A}, table:{@code TB_CM_EMPLOYEE}(시스템/대리점), column:{@code AGENCY_CD}(대리점코드)<br>
     */
    private String orgnCd;

    /**
     * 소속 명<br>
     * 테이블 마다 쓰이는 컬럼이 다르다<br>
     * {@link OrgnFg} type:{@code H}, table:{@code TB_HQ_EMPLOYEE}(본사), column:{@code HQ_OFFICE_NM}(본사명)<br>
     * {@link OrgnFg} type:{@code S}, table:{@code TB_MS_EMPLOYEE}(가맹점), column:{@code STORE_NM}(본사명)<br>
     * {@link OrgnFg} type:{@code M,A}, table:{@code TB_CM_EMPLOYEE}(시스템/대리점), column:{@code AGENCY_NM}(대리점명)<br>
     */
    private String orgnNm;

    /**
     * 로그인 유져가 가맹점 일 경우 해당 본사의 코드<br>
     * {@link SessionInfoVO} : {@code orgnFg} S 상태 일때만 값 저장
     */
    private String storeCd;

    /**
     * 로그인 유져가 가맹점 일 경우 해당 본사의 이름<br>
     * {@link SessionInfoVO} : {@code orgnFg} S 상태 일때만 값 저장
     */
    private String storeNm;

    /** 사원번호 */
    private String empNo;

    /** 최종 로그인 일자 */
    private String lastLoginDate;

    /** 마지막으로 패스워드 변경 날짜 */
    private String lastPwdChg;

    /** 로그인 실패 건수 */
    private Long loginFailCnt;

    /** 잠금 코드 */
    private String lockCd;

    /** 로그인 IP */
    private String loginIp;

    /** 브라우저 정보 */
    private String brwsrInfo;

    /** 로그인 시도 결과 */
    private LoginResult loginResult;

    /** 로그인 화면 : 아이디 저장 여부 */
    private boolean chk;

    /**
     * 메뉴 관련 데이터
     * */
    private String startDt;

    /** 조회 종료 일자 */
    private String endDt;

    /**
     * 메뉴 관련 데이터
     */

    /** 전체메뉴 조회(JSON) */
    private String menuData;

    /** 즐겨찾기메뉴 조회 (JSON) */
    private String bkmkData;

    /** 고정 메뉴 (JSON) */
    private String fixData;

    /** 현재 선택한 메뉴 정보 */
    private ResrceInfoBaseVO currentMenu;

    /** 권한 있는 메뉴 */
    private List<ResrceInfoVO> authMenu;

    /** 즐겨찾기 메뉴 */
    private List<ResrceInfoBaseVO> bkmkMenu;

    /** 사용한 히스토리 메뉴 */
    private List<ResrceInfoBaseVO> histMenu;

    /** 고정 메뉴 */
    private List<ResrceInfoBaseVO> fixMenu;
    
    /** 가상로그인 ID */
    private String vUserId;
}
