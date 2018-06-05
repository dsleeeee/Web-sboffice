package kr.co.solbipos.store.domain.hq.hqmanage;

import kr.co.solbipos.application.domain.cmm.PageVO;
import kr.co.solbipos.sys.enums.IncldExcldFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqMenuVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 복사할 본사코드 */
    private String copyHqOfficeCd;
    
    /** 메뉴 리소스 코드 */
    private String resrceCd;

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
