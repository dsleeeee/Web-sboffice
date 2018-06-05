package kr.co.solbipos.application.common.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 리소스 도메인<br>
 * {@link kr.co.solbipos.application.common.service.ResrceInfoVO}<br>
 * 의 경량화 버전
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResrceInfoBaseVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    
    /** 리소스 코드 */
    private String resrceCd;

    /** 상위 리소스 */
    private String pResrce;

    /** 리소스 명 */
    private String resrceNm;

    /** URL */
    private String url;

    /** 활성화 여부 */
    private boolean activation = false;
}
