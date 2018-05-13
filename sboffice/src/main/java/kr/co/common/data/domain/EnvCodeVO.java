package kr.co.common.data.domain;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.cmm.CmmVO;
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

    private String envstCd;
    private String envstValCd;
    private String envstValNm;
    private String defltYn;
    private String useYn;

    List<DefaultMap<String>> codeList;
}
