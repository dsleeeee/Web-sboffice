package kr.co.solbipos.store.manage.pwdmanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : PwdManageVO.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PwdManageVO extends PageVO {
    
    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;
    
    /** 사용자ID */
    private String userId;
    
    /** 사용자명 */
    private String userNm;
    
    /** 재직구분 */
    private String serviceFg;
    
    /** 웹사용여부 */
    private String webUseYn;
    
    /** 휴대폰번호 */
    private String mpNo;
    
    /** 이메일주소 */
    private String emailAddr;
    
    /** 주소 */
    private String addr;
    
    /** 새비밀번호 */
    private String newPassword;
    
    /** 새비밀번호 확인 */
    private String confirmPassword;
    
    /** 기존비밀번호 */
    private String priorPwd;
    
    /** 등록IP */
    private String regIp;
    
    /** 등록일시 */
    private String regDt;
    
    /** 등록자ID */
    private String regId;
    
    /** 수정일시 */
    private String modDt;
    
    /** 수정자ID */
    private String modId;
    
}
