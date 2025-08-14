package kr.co.solbipos.pos.confg.mainVerManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MainVerManageVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 메인버전관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MainVerManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회용 본사코드 */
    private String srchHqOfficeCd;

    /** 본사명 */
    private String srchHqOfficeNm;

    /** 등록여부 */
    private String registYn;

    /** 구분 */
    private String verGubun;

    /** 버전일련번호 */
    private String verSerNo;

    /** 버전명 */
    private String verSerNm;

    /** 프로그램구분상세 */
    private String progDetailFg;

    /** 파일설명 */
    private String fileDesc;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getSrchHqOfficeCd() { return srchHqOfficeCd; }

    public void setSrchHqOfficeCd(String srchHqOfficeCd) { this.srchHqOfficeCd = srchHqOfficeCd; }

    public String getSrchHqOfficeNm() { return srchHqOfficeNm; }

    public void setSrchHqOfficeNm(String srchHqOfficeNm) { this.srchHqOfficeNm = srchHqOfficeNm; }

    public String getRegistYn() { return registYn; }

    public void setRegistYn(String registYn) { this.registYn = registYn; }

    public String getVerGubun() { return verGubun; }

    public void setVerGubun(String verGubun) { this.verGubun = verGubun; }

    public String getVerSerNo() { return verSerNo; }

    public void setVerSerNo(String verSerNo) { this.verSerNo = verSerNo; }

    public String getVerSerNm() { return verSerNm; }

    public void setVerSerNm(String verSerNm) { this.verSerNm = verSerNm; }

    public String getProgDetailFg() { return progDetailFg; }

    public void setProgDetailFg(String progDetailFg) { this.progDetailFg = progDetailFg; }

    public String getFileDesc() { return fileDesc; }

    public void setFileDesc(String fileDesc) { this.fileDesc = fileDesc; }
}