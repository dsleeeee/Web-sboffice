package kr.co.solbipos.pos.confg.vermanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : VerInfoVO.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class VerInfoVO extends PageVO {

    private static final long serialVersionUID = -1179266936609857210L;
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

    /** 사용여부 */
    private UseYn useYn;

}
