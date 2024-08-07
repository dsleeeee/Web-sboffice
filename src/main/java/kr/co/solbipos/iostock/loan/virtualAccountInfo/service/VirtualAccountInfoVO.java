package kr.co.solbipos.iostock.loan.virtualAccountInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : VirtualAccountInfoVO.java
 * @Description : 수불관리 > 주문관리 > 가상계좌-기초정보등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VirtualAccountInfoVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 구분 */
    private String gubun;

    /** NHN KCP 발급 사이트코드 */
    private String siteCd;

    /** KCP PG-API 인증서정보(직렬화) */
    private String kcpCertInfo;

    /** KCP 발급 개인키 */
    private String kcpPrivateKey;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getSiteCd() { return siteCd; }

    public void setSiteCd(String siteCd) { this.siteCd = siteCd; }

    public String getKcpCertInfo() { return kcpCertInfo; }

    public void setKcpCertInfo(String kcpCertInfo) { this.kcpCertInfo = kcpCertInfo; }

    public String getKcpPrivateKey() { return kcpPrivateKey; }

    public void setKcpPrivateKey(String kcpPrivateKey) { this.kcpPrivateKey = kcpPrivateKey; }
}