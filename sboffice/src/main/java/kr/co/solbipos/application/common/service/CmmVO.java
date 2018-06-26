package kr.co.solbipos.application.common.service;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
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
    
    /** VO내의 모든 값 출력 */
    public String getProperties() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
