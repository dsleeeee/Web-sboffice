package kr.co.solbipos.store.hq.brand.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.store.hq.brand.enums.TargtFg;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 가맹점관리 > 본사정보 > 브랜드정보관리 > 환경설정
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqEnvstVO extends CmmVO{
    
    private static final long serialVersionUID = 713050528057597639L;

    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 브랜드코드 */
    private String hqBrandCd;
    
    /** 환경설정코드 */
    private String envstCd;
    
    /** 환경설정명 */
    private String envstNm;
    
    /** 환경설정그룹코드 */
    private String envstGrpCd;
    
    /** 환경설정 기본값 */
    private String envDefault;
    
    /** 환경설정값 */
    private String envstVal;
    
    /** 대상구분 */
    private TargtFg targtFg;
    
    /** 직접입력여부 */
    private String dirctInYn;
    
    /** 사용여부 */
    private UseYn useYn;

}
