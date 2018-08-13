package kr.co.solbipos.application.session.auth.service;

import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
 * @Class Name : SessionInfoVO.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
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
    /** 메뉴 관련 데이터 */
    private String startDt;
    /** 조회 종료 일자 */
    private String endDt;
    /** 메뉴 관련 데이터 */
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
    /** 가상로그인ID */
    private String vUserId;
    
    
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
     * @return the lastLoginDate
     */
    public String getLastLoginDate() {
        return lastLoginDate;
    }
    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    /**
     * @return the lastPwdChg
     */
    public String getLastPwdChg() {
        return lastPwdChg;
    }
    /**
     * @param lastPwdChg the lastPwdChg to set
     */
    public void setLastPwdChg(String lastPwdChg) {
        this.lastPwdChg = lastPwdChg;
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
     * @return the lockCd
     */
    public String getLockCd() {
        return lockCd;
    }
    /**
     * @param lockCd the lockCd to set
     */
    public void setLockCd(String lockCd) {
        this.lockCd = lockCd;
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
    /**
     * @return the startDt
     */
    public String getStartDt() {
        return startDt;
    }
    /**
     * @param startDt the startDt to set
     */
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    /**
     * @return the endDt
     */
    public String getEndDt() {
        return endDt;
    }
    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
    /**
     * @return the menuData
     */
    public String getMenuData() {
        return menuData;
    }
    /**
     * @param menuData the menuData to set
     */
    public void setMenuData(String menuData) {
        this.menuData = menuData;
    }
    /**
     * @return the bkmkData
     */
    public String getBkmkData() {
        return bkmkData;
    }
    /**
     * @param bkmkData the bkmkData to set
     */
    public void setBkmkData(String bkmkData) {
        this.bkmkData = bkmkData;
    }
    /**
     * @return the fixData
     */
    public String getFixData() {
        return fixData;
    }
    /**
     * @param fixData the fixData to set
     */
    public void setFixData(String fixData) {
        this.fixData = fixData;
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
     * @return the authMenu
     */
    public List<ResrceInfoVO> getAuthMenu() {
        return authMenu;
    }
    /**
     * @param authMenu the authMenu to set
     */
    public void setAuthMenu(List<ResrceInfoVO> authMenu) {
        this.authMenu = authMenu;
    }
    /**
     * @return the bkmkMenu
     */
    public List<ResrceInfoBaseVO> getBkmkMenu() {
        return bkmkMenu;
    }
    /**
     * @param bkmkMenu the bkmkMenu to set
     */
    public void setBkmkMenu(List<ResrceInfoBaseVO> bkmkMenu) {
        this.bkmkMenu = bkmkMenu;
    }
    /**
     * @return the histMenu
     */
    public List<ResrceInfoBaseVO> getHistMenu() {
        return histMenu;
    }
    /**
     * @param histMenu the histMenu to set
     */
    public void setHistMenu(List<ResrceInfoBaseVO> histMenu) {
        this.histMenu = histMenu;
    }
    /**
     * @return the fixMenu
     */
    public List<ResrceInfoBaseVO> getFixMenu() {
        return fixMenu;
    }
    /**
     * @param fixMenu the fixMenu to set
     */
    public void setFixMenu(List<ResrceInfoBaseVO> fixMenu) {
        this.fixMenu = fixMenu;
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
    
}
