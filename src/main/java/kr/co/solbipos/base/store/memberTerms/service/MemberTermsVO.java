package kr.co.solbipos.base.store.memberTerms.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MemberTermsVO.java
 * @Description : 기초관리 > 매장관리 > 회원약관관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MemberTermsVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 파일타입 */
    private String fileType;

    /** 버전일련번호 */
    private String verSerNo;

    /** 버전일련번호 */
    private String verSerNm;

    /** 원본파일명 */
    private String fileOrgNm;

    /** 사용여부 */
    private UseYn useYn;

    /** 사용자ID */
    private String userId;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVerSerNo() {
        return verSerNo;
    }

    public void setVerSerNo(String verSerNo) { this.verSerNo = verSerNo; }

    public String getVerSerNm() {
        return verSerNm;
    }

    public void setVerSerNm(String verSerNm) {
        this.verSerNm = verSerNm;
    }

    public String getFileOrgNm() {
        return fileOrgNm;
    }

    public void setFileOrgNm(String fileOrgNm) {
        this.fileOrgNm = fileOrgNm;
    }

    public UseYn getUseYn() {
        return useYn;
    }

    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}