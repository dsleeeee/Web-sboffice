package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service;

/**
 * @Class Name : DlvrAgencyLinkResVO.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.14  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

public class DlvrAgencyLinkResVO {

    private static final long serialVersionUID = -3506061792250059277L;

    private String code;
    private DlvrAgencyLinkDataVO data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DlvrAgencyLinkDataVO getData() {
        return data;
    }

    public void setData(DlvrAgencyLinkDataVO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
