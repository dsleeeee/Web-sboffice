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
public class EnvstVO extends CmmVO {

    private static final long serialVersionUID = 8257152089649317467L;
    
    /** 환경설정코드 */
    private String envstCd;
    
    /** 환경설정명 */
    private String envstNm;
    
    /** 환경설정구분 */
    private String envstFg;
    
    /** 환경설정구분명 */
    private String envstFgNm;
    
    /** 환경설정그룹코드 */
    private String envstGrpCd;
    
    /** 환경설정그룹코드명 */
    private String envstGrpCdNm;
    
    /** 직접입력여부 */
    private String dirctInYn;
    
    /** 대상구분 */
    private String targtFg;
    
    /** 대상구분명 */
    private String targtFgNm;
    
    /** 사용여부 */
    private String useYn;
    
    /** 비고 */
    private String remark;
    
    /**  */
    private String requiredYn;
    
    
   
}
