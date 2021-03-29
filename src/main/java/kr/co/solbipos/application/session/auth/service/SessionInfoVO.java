package kr.co.solbipos.application.session.auth.service;

import kr.co.common.validate.Login;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginInfoVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Class Name : SessionInfoVO.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SessionInfoVO extends CmmVO {

    private static final long serialVersionUID = -7682914174659365135L;
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
     * 소속 그룹 코드
     * 프렌차이즈인 경우 본사코드, 단독매장인 경우 자기자신의 매장코드, 시스템 or 대리점인 경우 AGENCY_CD
     */
    private String orgnGrpCd;

    /**
     * 소속 그룹명
     * 프렌차이즈인 경우 본사명, 단독매장인 경우 자기자신의 매장명, 시스템 or 대리점인 경우 AGENCY_NM
     */
    private String orgnGrpNm;

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

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 사원번호 */
    private String empNo;
    /** 최종 로그인 일시 */
    private String lastLoginDt;
    /** 마지막으로 패스워드 변경일시 */
    private String lastPwdChgDt;
    /** 로그인 실패 건수 */
    private Long loginFailCnt;
    /** 사용자상태구분 */
    private UserStatFg userStatFg;
    /** 사용 여부 */
    private String useYn;
    /** 로그인 IP */
    private String loginIp;
    /** 브라우저 정보 */
    private String brwsrInfo;
    /** 로그인 시도 결과 */
    private LoginResult loginResult;
    /** 로그인 화면 : 아이디 저장 여부 */
    private boolean chk;
    /** 로그인 화면 : 자동로그인 여부 */
    private boolean chkLoginAuto;
    /** 메뉴 관련 데이터 */
    private String startDate;
    /** 조회 종료 일자 */
    private String endDate;

    /** 메뉴 관련 데이터 */
    /** 전체메뉴 */
    private List<ResrceInfoBaseVO> menuData;
    /** 전체메뉴 : 트리구조 */
    private List<ResrceInfoBaseVO> menuTreeData;
    /** 즐겨찾기메뉴 */
    private List<ResrceInfoBaseVO> bkmkMenuData;
    /** 즐겨찾기메뉴 : 트리구조 */
    private List<ResrceInfoBaseVO> bkmkMenuTreeData;
    /** 고정 메뉴 */
    private List<ResrceInfoBaseVO> fixedMenuData;
    /** 사용한 히스토리 메뉴 */
    private List<ResrceInfoBaseVO> historyMenuData;
    /** 현재 선택한 메뉴 정보 */
    private ResrceInfoBaseVO currentMenu;
    /** 가상로그인ID */
    private String vUserId;
    /** 가상로그인정보 */
    private List<VirtualLoginInfoVO> vLogindIds;

    /** POS 로그인을 위한 하드웨어인증키 */
    private String hwAuthKey;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;

    /** 사용자의 권역 코드 */
    private String areaFg;

    /** 모바일 로그인 여부 */
    private String loginFg;

    /** 모바일 자동로그인 Serial No */
    private String loginAutoSerial;


	
	/**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }
    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the userPwd
     */
    public String getUserPwd() {
        return userPwd;
    }
    /**
     * @param userPwd the userPwd to set
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    /**
     * @return the userNm
     */
    public String getUserNm() {
        return userNm;
    }
    /**
     * @param userNm the userNm to set
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }
    /**
     * @return the authGrpCd
     */
    public String getAuthGrpCd() {
        return authGrpCd;
    }
    /**
     * @param authGrpCd the authGrpCd to set
     */
    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }
    /**
     * @return the arrStoreCdList
     */
    public List<String> getArrStoreCdList() {
        return arrStoreCdList;
    }
    /**
     * @param arrStoreCdList the arrStoreCdList to set
     */
    public void setArrStoreCdList(List<String> arrStoreCdList) {
        this.arrStoreCdList = arrStoreCdList;
    }
    /**
     * @return the orgnFg
     */
    public OrgnFg getOrgnFg() {
        return orgnFg;
    }
    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }
    /**
     * @return the orgnGrpCd
     */
    public String getOrgnGrpCd() {
        return orgnGrpCd;
    }

    /**
     * @param orgnGrpCd the orgnGrpCd to set
     */
    public void setOrgnGrpCd(String orgnGrpCd) {
        this.orgnGrpCd = orgnGrpCd;
    }

    /**
     * @return the orgnGrpNm
     */
    public String getOrgnGrpNm() {
        return orgnGrpNm;
    }

    /**
     * @param orgnGrpNm the orgnGrpNm to set
     */
    public void setOrgnGrpNm(String orgnGrpNm) {
        this.orgnGrpNm = orgnGrpNm;
    }
    /**
     * @return the orgnCd
     */
    public String getOrgnCd() {
        return orgnCd;
    }
    /**
     * @param orgnCd the orgnCd to set
     */
    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }
    /**
     * @return the orgnNm
     */
    public String getOrgnNm() {
        return orgnNm;
    }
    /**
     * @param orgnNm the orgnNm to set
     */
    public void setOrgnNm(String orgnNm) {
        this.orgnNm = orgnNm;
    }
    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }
    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }
    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }
    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }
    /**
     * @param empNo the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    /**
     * @return the lastLoginDt
     */
    public String getLastLoginDt() {
        return lastLoginDt;
    }
    /**
     * @param lastLoginDt the lastLoginDt to set
     */
    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }
    /**
     * @return the lastPwdChgDt
     */
    public String getLastPwdChgDt() {
        return lastPwdChgDt;
    }
    /**
     * @param lastPwdChgDt the lastPwdChgDt to set
     */
    public void setLastPwdChgDt(String lastPwdChgDt) {
        this.lastPwdChgDt = lastPwdChgDt;
    }
    /**
     * @return the loginFailCnt
     */
    public Long getLoginFailCnt() {
        return loginFailCnt;
    }
    /**
     * @param loginFailCnt the loginFailCnt to set
     */
    public void setLoginFailCnt(Long loginFailCnt) {
        this.loginFailCnt = loginFailCnt;
    }
    /**
     * @return the userStatFg
     */
    public UserStatFg getUserStatFg() {
        return userStatFg;
    }
    /**
     * @param userStatFg the userStatFg to set
     */
    public void setUserStatFg(UserStatFg userStatFg) {
        this.userStatFg = userStatFg;
    }
    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    /**
     * @return the loginIp
     */
    public String getLoginIp() {
        return loginIp;
    }
    /**
     * @param loginIp the loginIp to set
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    /**
     * @return the brwsrInfo
     */
    public String getBrwsrInfo() {
        return brwsrInfo;
    }
    /**
     * @param brwsrInfo the brwsrInfo to set
     */
    public void setBrwsrInfo(String brwsrInfo) {
        this.brwsrInfo = brwsrInfo;
    }
    /**
     * @return the loginResult
     */
    public LoginResult getLoginResult() {
        return loginResult;
    }
    /**
     * @param loginResult the loginResult to set
     */
    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }
    /**
     * @return the chk
     */
    public boolean isChk() {
        return chk;
    }
    /**
     * @param chk the chk to set
     */
    public void setChk(boolean chk) {
        this.chk = chk;
    }

    public boolean isChkLoginAuto() {
        return chkLoginAuto;
    }

    public void setChkLoginAuto(boolean chkLoginAuto) {
        this.chkLoginAuto = chkLoginAuto;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the menuData
     */
    public List<ResrceInfoBaseVO> getMenuData() {
        return menuData;
    }
    /**
     * @param menuData the menuData to set
     */
    public void setMenuData(List<ResrceInfoBaseVO> menuData) {
        this.menuData = menuData;
    }
    /**
     * @return the menuTreeData
     */
    public List<ResrceInfoBaseVO> getMenuTreeData() {
        return menuTreeData;
    }
    /**
     * @param menuTreeData the menuTreeData to set
     */
    public void setMenuTreeData(List<ResrceInfoBaseVO> menuTreeData) {
        this.menuTreeData = menuTreeData;
    }
    /**
     * @return the bkmkMenuData
     */
    public List<ResrceInfoBaseVO> getBkmkMenuData() {
        return bkmkMenuData;
    }
    /**
     * @param bkmkMenuData the bkmkMenuData to set
     */
    public void setBkmkMenuData(List<ResrceInfoBaseVO> bkmkMenuData) {
        this.bkmkMenuData = bkmkMenuData;
    }
    /**
     * @return the bkmkMenuTreeData
     */
    public List<ResrceInfoBaseVO> getBkmkMenuTreeData() {
        return bkmkMenuTreeData;
    }
    /**
     * @param bkmkMenuTreeData the bkmkMenuTreeData to set
     */
    public void setBkmkMenuTreeData(List<ResrceInfoBaseVO> bkmkMenuTreeData) {
        this.bkmkMenuTreeData = bkmkMenuTreeData;
    }
    /**
     * @return the fixedMenuData
     */
    public List<ResrceInfoBaseVO> getFixedMenuData() {
        return fixedMenuData;
    }
    /**
     * @param fixedMenuData the fixedMenuData to set
     */
    public void setFixedMenuData(List<ResrceInfoBaseVO> fixedMenuData) {
        this.fixedMenuData = fixedMenuData;
    }
    /**
     * @return the historyMenuData
     */
    public List<ResrceInfoBaseVO> getHistoryMenuData() {
        return historyMenuData;
    }
    /**
     * @param historyMenuData the historyMenuData to set
     */
    public void setHistoryMenuData(List<ResrceInfoBaseVO> historyMenuData) {
        this.historyMenuData = historyMenuData;
    }
    /**
     * @return the currentMenu
     */
    public ResrceInfoBaseVO getCurrentMenu() {
        return currentMenu;
    }
    /**
     * @param currentMenu the currentMenu to set
     */
    public void setCurrentMenu(ResrceInfoBaseVO currentMenu) {
        this.currentMenu = currentMenu;
    }
    /**
     * @return the vUserId
     */
    public String getvUserId() {
        return vUserId;
    }
    /**
     * @param vUserId the vUserId to set
     */
    public void setvUserId(String vUserId) {
        this.vUserId = vUserId;
    }

    /**
     * @return the vLogindIds
     */
    public List<VirtualLoginInfoVO> getvLogindIds() {
        return vLogindIds;
    }

    /**
     * @param vLogindIds the vLogindIds to set
     */
    public void setvLogindIds(List<VirtualLoginInfoVO> vLogindIds) {
        this.vLogindIds = vLogindIds;
    }

    /**
     * @return the hwAuthKey
     */
    public String getHwAuthKey() { return hwAuthKey; }

    /**
     * @param hwAuthKey the hwAuthKey to set
     */
    public void setHwAuthKey(String hwAuthKey) { this.hwAuthKey = hwAuthKey; }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }
    
    public String getAreaFg() {
		return areaFg;
	}

	public void setAreaFg(String areaFg) {
		this.areaFg = areaFg;
	}

    public String getLoginFg() {
        return loginFg;
    }

    public void setLoginFg(String loginFg) {
        this.loginFg = loginFg;
    }

    public String getLoginAutoSerial() {
        return loginAutoSerial;
    }

    public void setLoginAutoSerial(String loginAutoSerial) {
        this.loginAutoSerial = loginAutoSerial;
    }
}
