package kr.co.solbipos.application.domain.com;

import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 즐겨찾기 관리 <br>
 *
 * @author 노현수
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BkmkVO extends CmmVO {

    private static final long serialVersionUID = -8417487189063159637L;

    /** 사용자 아이디 */
    private String userId;

    /** 리소스 코드 */
    private String resrceCd;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 리소스코드 (Array) */
    private String[] resrceCds;

}
