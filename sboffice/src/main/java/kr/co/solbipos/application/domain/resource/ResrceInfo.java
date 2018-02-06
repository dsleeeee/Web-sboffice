package kr.co.solbipos.application.domain.resource;

import kr.co.solbipos.application.domain.cmm.Cmm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 리소스<br>
 * table : TB_WB_RESRCE_INFO
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResrceInfo extends Cmm {

    private static final long serialVersionUID = 1L;
    
    /** 리소스 코드 */
    private String resrceCd;

    /** 상위 리소스 */
    private String pResrce;

    /** 리소스 구분 */
    private String resrceFg;
//    private ResrceFg resrceFg;
    
    /** 기능 구분 */
    private String funcFg;

    /** 리소스 명 */
    private String resrceNm;

    /** URL */
    private String url;

    /** 특수 권한 */
    private String spclAuthor;

    /** 표기 레벨 */
    private Long dispLevel;

    /** 표기 인덱스 */
    private Long dispIdx;

    /** 사용 여부 */
    private String useYn;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아이디 */
    private String modId;


}
