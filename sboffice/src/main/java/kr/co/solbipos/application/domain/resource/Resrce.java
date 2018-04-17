package kr.co.solbipos.application.domain.resource;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 리소스<br>
 * table : TB_WB_RESRCE_INFO
 * 
 * @author 조병준
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Resrce {

    /** 리소스 코드 */
    private String resrceCd;

    /** 상위 리소스 */
    private String pResrce;

    /** 리소스 명 */
    private String resrceNm;
    
    /** 권한 보유 여부 */
    private Long authFg;
    
    /** Child Items */
    private List<Resrce> items;


}
