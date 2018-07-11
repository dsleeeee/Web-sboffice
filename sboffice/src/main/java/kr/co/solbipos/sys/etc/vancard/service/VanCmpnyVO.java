package kr.co.solbipos.sys.etc.vancard.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : VanCmpnyVO.java
 * @Description : 시스템관리 > VAN/CARD사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
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
public class VanCmpnyVO extends CmmVO {

    private static final long serialVersionUID = -2340647114412075524L;
    
    /** 밴사코드 */
    private String vanCd;
    
    /** 밴사명 */
    private String vanNm;
    
    /** 전화번호 */
    private String telNo;
    
    /** 팩스번호 */
    private String faxNo;
    
    /** 메인IP */
    private String mainIp;
    
    /** 메인PORT */
    private String mainPort;
    
    /** 서브IP */
    private String subIp;
    
    /** 서브PORT */
    private String subPort;
    

}
