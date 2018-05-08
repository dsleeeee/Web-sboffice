package kr.co.solbipos.application.domain.cmm;

import java.io.Serializable;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import lombok.Data;

/**
 * 테이블 공통 도메인 클래스<br>
 * 등록, 수정 일시<br>
 * 등록, 수정 아이디
 *
 * @author 정용길
 */
@Data
public abstract class CmmVO implements Serializable {

    private static final long serialVersionUID = -389255049761030824L;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아이디 */
    private String modId;

    /** 상태 (IUD) */
    private GridDataFg status;
}
