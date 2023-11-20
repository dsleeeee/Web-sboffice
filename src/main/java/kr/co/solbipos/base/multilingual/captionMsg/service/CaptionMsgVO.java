package kr.co.solbipos.base.multilingual.captionMsg.service;

import kr.co.solbipos.application.common.service.PageVO;

public class CaptionMsgVO extends PageVO {

    private static final long serialVersionUID = -6827958776667358322L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 기능키, 메시지 화면구분코드 */
    private String captionImgCd;
    /** 기능키, 메시지 화면구분명 */
    private String captionImgNm;
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
    /** 확장자 체크 결과*/
    private String result;
    /** 파일타입 */
    private String fileType;
    /** 기능키 or 메시지 코드(고유 ID) array */
    private String arrCaptionMsgId[];
    /** 기능키 or 메시지 코드(고유 ID) */
    private String captionMsgId;
    /** 기능키, 메시지 명 */
    private String captionMsgNm;
    /** 기능키, 메시지 구분 */
    private String captionMsgGb;
    /** 기능키, 메시지명[영어] */
    private String captionMsgEnNm;
    /** 기능키, 메시지명[중국어] */
    private String captionMsgCnNm;
    /** 기능키, 메시지명[일어] */
    private String captionMsgJpNm;
    /** 화면이미지 고유번호 */
    private String captionNo;
    /** 비고 */
    private String remark;

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

    public String getCaptionImgCd() {
        return captionImgCd;
    }

    public void setCaptionImgCd(String captionImgCd) {
        this.captionImgCd = captionImgCd;
    }

    public String getCaptionImgNm() {
        return captionImgNm;
    }

    public void setCaptionImgNm(String captionImgNm) {
        this.captionImgNm = captionImgNm;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public String getFileOrgNm() {
        return fileOrgNm;
    }

    public void setFileOrgNm(String fileOrgNm) {
        this.fileOrgNm = fileOrgNm;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String[] getArrCaptionMsgId() {
        return arrCaptionMsgId;
    }

    public void setArrCaptionMsgId(String[] arrCaptionMsgId) {
        this.arrCaptionMsgId = arrCaptionMsgId;
    }

    public String getCaptionMsgId() {
        return captionMsgId;
    }

    public void setCaptionMsgId(String captionMsgId) {
        this.captionMsgId = captionMsgId;
    }

    public String getCaptionMsgNm() {
        return captionMsgNm;
    }

    public void setCaptionMsgNm(String captionMsgNm) {
        this.captionMsgNm = captionMsgNm;
    }

    public String getCaptionMsgGb() {
        return captionMsgGb;
    }

    public void setCaptionMsgGb(String captionMsgGb) {
        this.captionMsgGb = captionMsgGb;
    }

    public String getCaptionMsgEnNm() {
        return captionMsgEnNm;
    }

    public void setCaptionMsgEnNm(String captionMsgEnNm) {
        this.captionMsgEnNm = captionMsgEnNm;
    }

    public String getCaptionMsgCnNm() {
        return captionMsgCnNm;
    }

    public void setCaptionMsgCnNm(String captionMsgCnNm) {
        this.captionMsgCnNm = captionMsgCnNm;
    }

    public String getCaptionMsgJpNm() {
        return captionMsgJpNm;
    }

    public void setCaptionMsgJpNm(String captionMsgJpNm) {
        this.captionMsgJpNm = captionMsgJpNm;
    }

    public String getCaptionNo() {
        return captionNo;
    }

    public void setCaptionNo(String captionNo) {
        this.captionNo = captionNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
