package kr.co.solbipos.store.manage.virtuallogin.service;

import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : VirtualLoginVO.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VirtualLoginVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 용도 */
    private String clsFg;
    
    /** 용도 */
    private String clsFgNm;
    
    /** 상태 */
    private String sysStatFg;
    
    /** 상태 */
    private String sysStatFgNm;

    /** 대표자명 */
    private String ownerNm;

    /** 전화번호 */
    private String telNo;

    /** 휴대전화 */
    private String mpNo;

    /** 관리업체 */
    private String agencyCd;
    
    /** 관리업체 */
    private String agencyNm;

    /** 시스템오픈일 */
    private String sysOpenDate;

    /** 시스템폐점일 */
    private String sysClosureDate;
    
    /** 본사로그인 ID */
    private String hqUserId;
    
    /** 매장로그인 ID */
    private String msUserId;
    
    /** 관리업체로그인 ID */
    private String cmUserId;
    
    /** 가상로그인ID */
    private String vUserId;

}
