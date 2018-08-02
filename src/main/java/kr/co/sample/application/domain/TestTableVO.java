package kr.co.sample.application.domain;

import kr.co.solbipos.application.common.service.PageVO;

public class TestTableVO extends PageVO {
    
    private static final long serialVersionUID = -1161109160663780494L;
    private Long keyNo;
    private String keyValue;
    
    
    /**
     * @return the keyNo
     */
    public Long getKeyNo() {
        return keyNo;
    }
    /**
     * @param keyNo the keyNo to set
     */
    public void setKeyNo(Long keyNo) {
        this.keyNo = keyNo;
    }
    /**
     * @return the keyValue
     */
    public String getKeyValue() {
        return keyValue;
    }
    /**
     * @param keyValue the keyValue to set
     */
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
    
}
