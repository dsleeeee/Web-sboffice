package kr.co.solbipos.sys.admin.posUtilLog.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PosUtilLogVO.java
 * @Description : 시스템관리 > 관리자기능 > 포스유틸사용로그
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.21  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2023.12.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosUtilLogVO extends PageVO {

    private static final long serialVersionUID = 2097268746718714031L;

    /** 일자,로그전송일시 */
    private String srchDate;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 처리유저명 */
    private String userNm;
    /** 로그내용 */
    private String logMsg;

    public String getSaleDate() { return srchDate; }

    public void setSaleDate(String saleDate) { this.srchDate = saleDate; }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getHqOfficeNm() { return hqOfficeNm; }

    public void setHqOfficeNm(String hqOfficeNm) { this.hqOfficeNm = hqOfficeNm; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getUserNm() { return userNm; }

    public void setUserNm(String userNm) { this.userNm = userNm; }

    public String getLogMsg() { return logMsg; }

    public void setLogMsg(String logMsg) { this.logMsg = logMsg; }
}
