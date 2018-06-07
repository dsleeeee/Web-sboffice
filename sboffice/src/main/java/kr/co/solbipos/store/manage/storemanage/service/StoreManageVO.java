package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreManageVO extends PageVO {

    /** 매장코드 */
    private String storeCd;
    
    /** 매장명 */
    private String storeNm;

    /** 대표자명 */
    private String ownerNm;

    /** 본사사업장코드 */
    private String hqOfficeCd;

    /** 본사사업장명 */
    private String hqOfficeNm;
    
    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 사업자번호 */
    private String bizNo;

    /** 사업자유형코드 */
    private String bizTypeCd;

    /** 사업자항목코드 */
    private String bizItemCd;

    /** 사업자매장명 */
    private String bizStoreNm;

    /** 전화번호 */
    private String telNo;

    /** 팩스번호 */
    private String faxNo;

    /** 이메일주소 */
    private String emailAddr;

    /** 홈페이지주소 */
    private String hmpgAddr;

    /** 우편번호 */
    private String postNo;

    /** 주소 */
    private String addr;

    /** 주소상세 */
    private String addrDtl;

    /** 지역코드 */
    private String areaCd;

    /** 용도구분  */
    private String clsFg;

    /** 시스템상태구분 */
    private String sysStatFg;

    /** 시스템오픈일자 */
    private String sysOpenDate;

    /** 시스템폐점일자 */
    private String sysClosureDate;

    /** 밴사코드 */
    private String vanCd;

    /** 대리점코드 */
    private String agencyCd;

    /** 비고 */
    private String remark;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;
}
