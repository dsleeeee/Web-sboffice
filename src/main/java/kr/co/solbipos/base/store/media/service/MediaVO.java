package kr.co.solbipos.base.store.media.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : MediaVO.java
* @Description : 기초관리 > 매장관리 > 미디어관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.10  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.05.10
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class MediaVO extends PageVO {

    private static final long serialVersionUID = -1179266936609857210L;

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
    /** 파일 저장 디렉토리 */
    private String fileDir;
    /** 저장파일명 */
    private String fileNm;
    /** 파일확장자 */
    private String fileExt;
    /** 파일사이즈 */
    private int fileSize;
    /** 파일MIME_TYPE */
    private String fileMimeType;
    /** 원본파일명 */
    private String fileOrgNm;
    /** 파일설명 */
    private String fileDesc;
    /** 포스 프로그램 구분 */
    private String progFg;
    /** PGM 포함 여부 */
    private String pgmYn;
    /** DB 포함 여부 */
    private String dbYn;
    /** IMG 포함 여부 */
    private String imgYn;
    /** 삭제여부 */
    private String delYn;
    /** 사용여부 */
    private UseYn useYn;
    /**  상태 */
    private String sysStatFg;
    /**  원본파일명 */
    private String verOrgNm;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 확장자 체크 결과 */
    private String result;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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

    /**
     * @return the verSerNo
     */
    public String getVerSerNo() {
        return verSerNo;
    }

    /**
     * @param verSerNo the verSerNo to set
     */
    public void setVerSerNo(String verSerNo) {
        this.verSerNo = verSerNo;
    }

    /**
     * @return the verSerNm
     */
    public String getVerSerNm() {
        return verSerNm;
    }

    /**
     * @param verSerNm the verSerNm to set
     */
    public void setVerSerNm(String verSerNm) {
        this.verSerNm = verSerNm;
    }

    /**
     * @return the fileDir
     */
    public String getFileDir() {
        return fileDir;
    }

    /**
     * @param fileDir the fileDir to set
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    /**
     * @return the fileNm
     */
    public String getFileNm() {
        return fileNm;
    }

    /**
     * @param fileNm the fileNm to set
     */
    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    /**
     * @return the fileExt
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * @param fileExt the fileExt to set
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    /**
     * @return the fileSize
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileMimeType
     */
    public String getFileMimeType() {
        return fileMimeType;
    }

    /**
     * @param fileMimeType the fileMimeType to set
     */
    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    /**
     * @return the fileOrgNm
     */
    public String getFileOrgNm() {
        return fileOrgNm;
    }

    /**
     * @param fileOrgNm the fileOrgNm to set
     */
    public void setFileOrgNm(String fileOrgNm) {
        this.fileOrgNm = fileOrgNm;
    }

    /**
     * @return the fileDesc
     */
    public String getFileDesc() {
        return fileDesc;
    }

    /**
     * @param fileDesc the fileDesc to set
     */
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * @return the progFg
     */
    public String getProgFg() {
        return progFg;
    }

    /**
     * @param progFg the progFg to set
     */
    public void setProgFg(String progFg) {
        this.progFg = progFg;
    }

    /**
     * @return the pgmYn
     */

    public String getPgmYn() {
        return pgmYn;
    }

    /**
     * @param pgmYn the pgmYn to set
     */
    public void setPgmYn(String pgmYn) {
        this.pgmYn = pgmYn;
    }

    /**
     * @return the dbYn
     */

    public String getDbYn() {
        return dbYn;
    }

    /**
     * @param dbYn the dbYn to set
     */
    public void setDbYn(String dbYn) {
        this.dbYn = dbYn;
    }

    /**
     * @return the imgYn
     */

    public String getImgYn() {
        return imgYn;
    }

    /**
     * @param imgYn the imgYn to set
     */
    public void setImgYn(String imgYn) {
        this.imgYn = imgYn;
    }

    /**
     * @return the delYn
     */
    public String getDelYn() {
        return delYn;
    }

    /**
     * @param delYn the delYn to set
     */
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    /**
     * @return the useYn
     */
    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVerOrgNm() {
        return verOrgNm;
    }

    public void setVerOrgNm(String verOrgNm) {
        this.verOrgNm = verOrgNm;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
