package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
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
    private SysStatFg sysStatFg;

    /** 시스템오픈일자 */
    private String sysOpenDate;

    /** 시스템폐점일자 */
    private String sysClosureDate;

    /** 밴사코드 */
    private String vanCd;

    /** 대리점코드 */
    private String agencyCd;

    /** 시스템 비고 */
    private String sysRemark;
    
    /** 본사 비고 */
    private String hqRemark;
    
    /** 비고 */
    private String remark;
    
    /** 설치포스수  */
    private String installPosCnt;
    
    /** 매장환경 복사 대상이 되는 본사  */
    private String copyHqOfficeCd;

    /** 매장환경 복사 대상이 되는 매장 */
    private String copyStoreCd;
    
    /** 터치키 입력 구분 (H:본사, S:매장) - 매장 등록시 사용 */
    private String inFg;
    
    /** 복사할 환경값 */
    private String copyChkVal;

    /** 포스번호 */
    private int posNo;
    
    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

}
