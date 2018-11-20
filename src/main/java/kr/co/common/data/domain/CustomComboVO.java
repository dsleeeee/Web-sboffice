package kr.co.common.data.domain;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CustomComboVO.java
 * @Description : 공통 커스텀 콤보 데이터
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CustomComboVO extends CmmVO {

    private static final long serialVersionUID = 7940677414097109441L;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 조회할 쿼리 ID */
    private String queryId;
    /** 조회할 쿼리 Type */
    private String type;
    /** code */
    private String value;
    /** codeNm */
    private String name;
    /** 파라미터1 */
    private String param1;
    /** 파라미터2 */
    private String param2;
    /** 파라미터3 */
    private String param3;
    /** 파라미터4 */
    private String param4;


    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the queryId
     */
    public String getQueryId() {
        return queryId;
    }

    /**
     * @param queryId the queryId to set
     */
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the param1
     */
    public String getParam1() {
        return param1;
    }

    /**
     * @param param1 the param1 to set
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * @return the param2
     */
    public String getParam2() {
        return param2;
    }

    /**
     * @param param2 the param2 to set
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * @return the param3
     */
    public String getParam3() {
        return param3;
    }

    /**
     * @param param3 the param3 to set
     */
    public void setParam3(String param3) {
        this.param3 = param3;
    }

    /**
     * @return the param4
     */
    public String getParam4() {
        return param4;
    }

    /**
     * @param param4 the param4 to set
     */
    public void setParam4(String param4) {
        this.param4 = param4;
    }
}
