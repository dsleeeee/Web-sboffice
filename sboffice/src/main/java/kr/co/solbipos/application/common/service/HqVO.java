package kr.co.solbipos.application.common.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 본사 조회용
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HqVO extends CmmVO {
    
    private static final long serialVersionUID = 1L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 대표자명 */
    private String ownerNm;

}
