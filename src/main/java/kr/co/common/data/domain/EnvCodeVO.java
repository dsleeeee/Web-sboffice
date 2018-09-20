package kr.co.common.data.domain;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.CmmVO;

import java.util.List;

/**
 * @Class Name : EnvCodeVO.java
 * @Description : 환경변수  코드 리스트
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EnvCodeVO extends CmmVO {
    
    private static final long serialVersionUID = -1741117450552741289L;
    
    private String envstCd;
    private String envstValCd;
    private String envstValNm;
    private String defltYn;
    private String useYn;
    List<DefaultMap<String>> codeList;
    
    
    /**
     * @return the envstCd
     */
    public String getEnvstCd() {
        return envstCd;
    }
    /**
     * @param envstCd the envstCd to set
     */
    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }
    /**
     * @return the envstValCd
     */
    public String getEnvstValCd() {
        return envstValCd;
    }
    /**
     * @param envstValCd the envstValCd to set
     */
    public void setEnvstValCd(String envstValCd) {
        this.envstValCd = envstValCd;
    }
    /**
     * @return the envstValNm
     */
    public String getEnvstValNm() {
        return envstValNm;
    }
    /**
     * @param envstValNm the envstValNm to set
     */
    public void setEnvstValNm(String envstValNm) {
        this.envstValNm = envstValNm;
    }
    /**
     * @return the defltYn
     */
    public String getDefltYn() {
        return defltYn;
    }
    /**
     * @param defltYn the defltYn to set
     */
    public void setDefltYn(String defltYn) {
        this.defltYn = defltYn;
    }
    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
