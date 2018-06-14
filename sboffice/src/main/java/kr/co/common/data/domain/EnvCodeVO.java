package kr.co.common.data.domain;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 환경변수  코드 리스트
 *
 * @author 정용길
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EnvCodeVO extends CmmVO {
    
    private static final long serialVersionUID = -1741117450552741289L;
    
    private String envstCd;
    private String envstValCd;
    private String envstValNm;
    private String defltYn;
    private String useYn;

    List<DefaultMap<String>> codeList;
}
