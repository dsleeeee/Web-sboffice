package kr.co.solbipos.application.domain.login;

import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.BaseDomain;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;
import kr.co.solbipos.application.enums.login.LoginResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SessionInfo extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /** sessionId */
    private String sessionId;
    
    /** 사용자 아이디 */
    @NotBlank( message = "{label.login.userId}{msg.cmm.require.text}" )
    @Size( max = 20, message = "{msg.cmm.size.max}" )
    private String userId;

    /** 사용자 비밀번호 */
    @NotBlank( message = "{label.login.userPasswd}{msg.cmm.require.text}" )
    @Size( min = 8, max = 25, message = "{msg.cmm.size.max}" )
    private String userPwd;
    
    /** 사용자 이름 */
    private String userName;

    /** 그룹 코드 */
    private String grpCd;

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
    
    /** 그리드 메뉴 데이터 */
    private String menuData;
    
    /** 현재 선택한 메뉴 정보 */
    private ResrceInfoBase currentMenu;

    /** 권한 있는 메뉴 */
    private List<ResrceInfo> authMenu;
    
    /** 즐겨찾기 메뉴 */
    private List<ResrceInfoBase> bkmkMenu;
    
    /** 사용한 히스토리 메뉴 */
    private List<ResrceInfoBase> histMenu;
    
    /** 고정 메뉴 */
    private List<ResrceInfoBase> fixMenu;
}
