package kr.co.solbipos.dlvr.info.regist.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DlvrRegistVO extends PageVO {
    /** 매장코드 */
    private String storeCd;
    /** 배달대분류구역코드 */
    private String dlvrLzoneCd;
    /** 배달대분류구역명 */
    private String dlvrLzoneNm;
    /** 페이지번호 */
    private String pageNo;
    /** 사원번호 */
    private String empNo;
    /** 입력구분 */
    private String inFg;
    /** 사용여부*/
    private String useYn;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getDlvrLzoneCd() {
        return dlvrLzoneCd;
    }

    public void setDlvrLzoneCd(String dlvrLzoneCd) {
        this.dlvrLzoneCd = dlvrLzoneCd;
    }

    public String getDlvrLzoneNm() {
        return dlvrLzoneNm;
    }

    public void setDlvrLzoneNm(String dlvrLzoneNm) {
        this.dlvrLzoneNm = dlvrLzoneNm;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getInFg() {
        return inFg;
    }

    public void setInFg(String inFg) {
        this.inFg = inFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
