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
    /** 동영상출력순서 */
    private String dispSeq;
    /** 이미지출력시간 */
    private String dispTime;
    /** 저장된파일명 */
    private String fileNmExt;
    /** 적용시간 사용여부 */
    private String timeYn;
    /** 적용시간 시작시간 */
    private String startTime;
    /** 적용시간 종료시간 */
    private String endTime;
    /** 요일 사용여부 */
    private String weekYn;
    /** 월요일 */
    private String monYn;
    /** 화요일 */
    private String tueYn;
    /** 수요일 */
    private String wedYn;
    /** 목요일 */
    private String thuYn;
    /** 금요일 */
    private String friYn;
    /** 토요일 */
    private String satYn;
    /** 일요일 */
    private String sunYn;
    /** 프로모션코드 */
    private String promotionCd;
    /** 소속코드 */
    private String orgnCd;
    /** 원본 파일 명 */
    private String orginlFileNm;
    /** 언어구분 */
    private String langFg;
    /** 매장등록구분 */
    private String storeSelectExceptFg;

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

    public String getDispSeq() { return dispSeq; }

    public void setDispSeq(String dispSeq) { this.dispSeq = dispSeq; }

    public String getDispTime() { return dispTime; }

    public void setDispTime(String dispTime) { this.dispTime = dispTime; }

    public String getFileNmExt() { return fileNmExt; }

    public void setFileNmExt(String fileNmExt) { this.fileNmExt = fileNmExt; }

    public String getTimeYn() {
        return timeYn;
    }

    public void setTimeYn(String timeYn) {
        this.timeYn = timeYn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeekYn() {
        return weekYn;
    }

    public void setWeekYn(String weekYn) {
        this.weekYn = weekYn;
    }

    public String getMonYn() {
        return monYn;
    }

    public void setMonYn(String monYn) {
        this.monYn = monYn;
    }

    public String getTueYn() {
        return tueYn;
    }

    public void setTueYn(String tueYn) {
        this.tueYn = tueYn;
    }

    public String getWedYn() {
        return wedYn;
    }

    public void setWedYn(String wedYn) {
        this.wedYn = wedYn;
    }

    public String getThuYn() {
        return thuYn;
    }

    public void setThuYn(String thuYn) {
        this.thuYn = thuYn;
    }

    public String getFriYn() {
        return friYn;
    }

    public void setFriYn(String friYn) {
        this.friYn = friYn;
    }

    public String getSatYn() {
        return satYn;
    }

    public void setSatYn(String satYn) {
        this.satYn = satYn;
    }

    public String getSunYn() {
        return sunYn;
    }

    public void setSunYn(String sunYn) {
        this.sunYn = sunYn;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    public String getOrginlFileNm() {
        return orginlFileNm;
    }

    public void setOrginlFileNm(String orginlFileNm) {
        this.orginlFileNm = orginlFileNm;
    }

    public String getLangFg() {
        return langFg;
    }

    public void setLangFg(String langFg) {
        this.langFg = langFg;
    }

    public String getStoreSelectExceptFg() {
        return storeSelectExceptFg;
    }

    public void setStoreSelectExceptFg(String storeSelectExceptFg) {
        this.storeSelectExceptFg = storeSelectExceptFg;
    }
}
