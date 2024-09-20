package kr.co.solbipos.sys.etc.vanManage.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : VanManageVO.java
 * @Description : 시스템관리 > Van/Card사 관리 > 밴사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.13  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VanManageVO extends PageVO {

    private static final long serialVersionUID = 7640082124137455351L;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 밴사구분 */
    private String vanFg;

    /** 밴사코드 */
    private String vanCd;

    /** 밴사명 */
    private String vanNm;

    /** 전화번호 */
    private String telNo;

    /** 팩스번호 */
    private String faxNo;

    /** 메인IP */
    private String mainIp;

    /** 메인PORT */
    private String mainPort;

    /** 서브IP */
    private String subIp;

    /** 서브PORT */
    private String subPort;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getVanFg() {
        return vanFg;
    }

    public void setVanFg(String vanFg) {
        this.vanFg = vanFg;
    }

    public String getVanCd() {
        return vanCd;
    }

    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }

    public String getVanNm() {
        return vanNm;
    }

    public void setVanNm(String vanNm) {
        this.vanNm = vanNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getMainIp() {
        return mainIp;
    }

    public void setMainIp(String mainIp) {
        this.mainIp = mainIp;
    }

    public String getMainPort() {
        return mainPort;
    }

    public void setMainPort(String mainPort) {
        this.mainPort = mainPort;
    }

    public String getSubIp() {
        return subIp;
    }

    public void setSubIp(String subIp) {
        this.subIp = subIp;
    }

    public String getSubPort() {
        return subPort;
    }

    public void setSubPort(String subPort) {
        this.subPort = subPort;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }
}
