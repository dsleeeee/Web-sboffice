package kr.co.solbipos.sys.cd.envconfig.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : EnvConfigVO.java
 * @Description : 시스템관리 > 코드관리 > 환경설정관리
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
public class EnvstDtlVO extends CmmVO {

    private static final long serialVersionUID = 7029471376127078552L;

    /** 환경설정코드 */
    private String envstCd;
    
    /** 환경설정값코드 */
    private String envstValCd;
    
    /** 환경설정값명 */
    private String envstValNm;
    
    /** 기본여부 */
    private String defltYn;
    
    /** 사용여부 */
    private String useYn;
    
   
}
