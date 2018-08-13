package kr.co.solbipos.sys.bill.item.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : ItemVO.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력코드 구성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ItemVO extends CmmVO {
    
    private static final long serialVersionUID = 3145750355286359515L;
    /** 출력물코드 */
    private String prtCd;
    /** 출력물명 */
    private String prtNm;
    /** 예제사용여부 */
    private Boolean samplYn;
    /** 예제 */
    private String content;
    
    
    /**
     * @return the prtCd
     */
    public String getPrtCd() {
        return prtCd;
    }
    /**
     * @param prtCd the prtCd to set
     */
    public void setPrtCd(String prtCd) {
        this.prtCd = prtCd;
    }
    /**
     * @return the prtNm
     */
    public String getPrtNm() {
        return prtNm;
    }
    /**
     * @param prtNm the prtNm to set
     */
    public void setPrtNm(String prtNm) {
        this.prtNm = prtNm;
    }
    /**
     * @return the samplYn
     */
    public Boolean getSamplYn() {
        return samplYn;
    }
    /**
     * @param samplYn the samplYn to set
     */
    public void setSamplYn(Boolean samplYn) {
        this.samplYn = samplYn;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
}
