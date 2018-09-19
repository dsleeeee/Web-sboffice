package kr.co.solbipos.application.common.service;

import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Class Name : CmmVO.java
 * @Description : 테이블 공통 도메인 최상위 클래스
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CmmVO implements Serializable {

    private static final long serialVersionUID = -389255049761030824L;
    /** 등록 일시 */
    private String regDt;
    /** 등록 아이디 */
    private String regId;
    /** 수정 일시 */
    private String modDt;
    /** 수정 아이디 */
    private String modId;
    /** 상태 (IUD) */
    private GridDataFg status;
    /** 그리드 공통 체크박스 */
    private Boolean gChk = false;
    
    /** VO내의 모든 값 출력 */
    public String getProperties() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    
    /**
     * @return the regDt
     */
    public String getRegDt() {
        return regDt;
    }
    /**
     * @param regDt the regDt to set
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }
    /**
     * @return the regId
     */
    public String getRegId() {
        return regId;
    }
    /**
     * @param regId the regId to set
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }
    /**
     * @return the modDt
     */
    public String getModDt() {
        return modDt;
    }
    /**
     * @param modDt the modDt to set
     */
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }
    /**
     * @return the modId
     */
    public String getModId() {
        return modId;
    }
    /**
     * @param modId the modId to set
     */
    public void setModId(String modId) {
        this.modId = modId;
    }
    /**
     * @return the status
     */
    public GridDataFg getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(GridDataFg status) {
        this.status = status;
    }
    /**
     * @return the gChk
     */
    public Boolean getgChk() {
        return gChk;
    }
    /**
     * @param gChk the gChk to set
     */
    public void setgChk(Boolean gChk) {
        this.gChk = gChk;
    }

}
