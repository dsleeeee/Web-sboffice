package kr.co.solbipos.application.common.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 메뉴 사용 기록 저장<br>
 * table : TB_WB_MENU_USE_HIST
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuUseHistVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    
    /** 순서 */
    private String seq;

    /** 사용자 아이디 */
    private String userId;

    /** 리소스 코드 */
    private String resrceCd;

    /** 사용 일자 */
    private String useDate;

    /** 사용 일시 */
    private String useDt;
}
