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
    /** 파일설명 (버전적용명) */
    private String fileNm;
    /** 파일사이즈 */
    private String fileSize;
    /** 파일 저장 디렉토리 */
    private String fileDir;
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
    /** 사용여부 */
    private UseYn useYn;
    
    
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
     * @return the fileSize
     */
    public String getFileSize() {
        return fileSize;
    }
    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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
    
}
