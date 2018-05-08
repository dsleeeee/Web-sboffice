package kr.co.solbipos.application.domain.cmm;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreVO extends CmmVO {
    
    private static final long serialVersionUID = 1L;

    /**  */
    private String storeCd;

    /**  */
    private String storeNm;

    /**  */
    private String ownerNm;

    /**  */
    private String hqOfficeCd;
}
