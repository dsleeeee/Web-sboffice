package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : HqManageVO.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqManageVO extends PageVO {

    /** 본사매장 구분 (H:본사, S:매장) */
    private String storeFg;
    
    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 본사명 */
    private String hqOfficeNm;
    
    /** 매장명 */
    private String storeCd;
    
    /** 대표자명 */
    private String ownerNm;

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

    /** 시스템상태구분  */
    private SysStatFg sysStatFg;

    /** 시스템오픈일자 */
    private String sysOpenDate;

    /** 시스템폐점일자 */
    private String sysClosureDate;

    /** 대리점코드 */
    private String agencyCd;

    /** 용도구분  */
    private String clsFg;

    /** 비고 */
    private String remark;

    /** 복사 본사 */
    private String copyHqOfficeCd;
    
    /** 사용자ID */
    private String userId;

    /** 포함 제외 여부 */
    private IncldExcldFg incldExcldFg;
    
    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;
}
