package kr.co.solbipos.membr.info.grade.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

public class MembrClassPointVO extends CmmVO {

    /**
     * [회원소속구분]
     */
    private OrgnFg membrOrgnFg;
    /**
     * [회원소속코드]
     */
    private String membrOrgnCd;
    /**
     * [회원등급코드]
     */
    private String membrClassCd;
    /**
     * [결제코드]
     */
    private String payCd;
    /**
     * [결제코드]
     */
    private String initPayCd;
    /**
     * [적립율]
     */
    private Integer accRate;
    /**
     * [비고]
     */
    private String remark;

    /**
     * @return the membrOrgnFg
     */
    public OrgnFg getMembrOrgnFg() {
        return membrOrgnFg;
    }

    /**
     * @param membrOrgnFg the useYn to set
     */
    public void setMembrOrgnFg(OrgnFg membrOrgnFg) {
        this.membrOrgnFg = membrOrgnFg;
    }

    /**
     * @return the membrOrgnCd
     */
    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }
    /**
     * @param membrOrgnCd the useYn to set
     */
    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    /**
     * @return the membrClassCd
     */

    public String getMembrClassCd() {
        return membrClassCd;
    }

    /**
     * @param membrClassCd the useYn to set
     */
    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }
    /**
     * @return the payCd
     */
    public String getPayCd() {
        return payCd;
    }
    /**
     * @param payCd the useYn to set
     */
    public void setPayCd(String payCd) {
        this.payCd = payCd;
    }
    /**
     * @return the accRate
     */
    public Integer getAccRate() {
        return accRate;
    }
    /**
     * @param accRate the useYn to set
     */
    public void setAccRate(Integer accRate) {
        this.accRate = accRate;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the useYn to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return the initPayCd
     */
    public String getInitPayCd() {
        return initPayCd;
    }
    /**
     * @param initPayCd the useYn to set
     */
    public void setInitPayCd(String initPayCd) {
        this.initPayCd = initPayCd;
    }
}
