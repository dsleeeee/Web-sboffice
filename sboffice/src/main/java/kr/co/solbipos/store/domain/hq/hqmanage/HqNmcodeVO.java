package kr.co.solbipos.store.domain.hq.hqmanage;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqNmcodeVO extends CmmVO {
    
    /** 브랜드코드 */
    private String hqBrandCd;
    
    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
    
    /** 명칭코드코드 */
    private String nmcodeCd;
    
    /** 명칭코드명 */
    private String nmcodeNm;
    
    /** 명칭코드항목_1 */
    private String nmcodeItem1;
    
    /** 명칭코드항목_2 */
    private String nmcodeItem2;
    
    /** 사용여부 (사용:Y 사용안함:N) */
    private UseYn useYn;
}
