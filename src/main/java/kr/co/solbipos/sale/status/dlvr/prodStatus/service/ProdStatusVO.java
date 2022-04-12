package kr.co.solbipos.sale.status.dlvr.prodStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProdStatusVO extends PageVO {

    private static final long serialVersionUID = -2441587264050745887L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** Cook Memo 사용여부 */
    private String cookMemoUseYn;

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

    public String getCookMemoUseYn() {
        return cookMemoUseYn;
    }

    public void setCookMemoUseYn(String cookMemoUseYn) {
        this.cookMemoUseYn = cookMemoUseYn;
    }
}
