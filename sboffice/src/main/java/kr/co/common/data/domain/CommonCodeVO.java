package kr.co.common.data.domain;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CommonCodeVO.java
 * @Description : 공통 코드 리스트
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CommonCodeVO extends CmmVO {

    private static final long serialVersionUID = -1829122321337723874L;
    private String comCdFg;
    private String comCd;
    private String comCdNm;
    
    List<DefaultMap<String>> codeList;
    
    
    /**
     * @return the comCdFg
     */
    public String getComCdFg() {
        return comCdFg;
    }
    /**
     * @param comCdFg the comCdFg to set
     */
    public void setComCdFg(String comCdFg) {
        this.comCdFg = comCdFg;
    }
    /**
     * @return the comCd
     */
    public String getComCd() {
        return comCd;
    }
    /**
     * @param comCd the comCd to set
     */
    public void setComCd(String comCd) {
        this.comCd = comCd;
    }
    /**
     * @return the comCdNm
     */
    public String getComCdNm() {
        return comCdNm;
    }
    /**
     * @param comCdNm the comCdNm to set
     */
    public void setComCdNm(String comCdNm) {
        this.comCdNm = comCdNm;
    }
    /**
     * @return the codeList
     */
    public List<DefaultMap<String>> getCodeList() {
        return codeList;
    }
    /**
     * @param codeList the codeList to set
     */
    public void setCodeList(List<DefaultMap<String>> codeList) {
        this.codeList = codeList;
    }
    
}
