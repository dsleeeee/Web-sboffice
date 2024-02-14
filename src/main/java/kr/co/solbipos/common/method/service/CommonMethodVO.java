package kr.co.solbipos.common.method.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : CommonMethodVO.java
 * @Description : (공통) 화면 공통 사용 메소드 모음
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.13  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CommonMethodVO extends PageVO {

    private static final long serialVersionUID = -1770588950551961184L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사용자아이디 */
    private String userId;
    /** 리소스 코드 */
    private String resrceCd;
    /** 리소스 경로명 */
    private String pathNm;
    /** 행위 내용 */
    private String contents;
    /** 로그인 IP */
    private String loginIp;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public String getPathNm() {
        return pathNm;
    }

    public void setPathNm(String pathNm) {
        this.pathNm = pathNm;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
