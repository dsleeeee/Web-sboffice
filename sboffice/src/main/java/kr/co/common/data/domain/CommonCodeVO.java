package kr.co.common.data.domain;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 공통 코드 리스트
 *
 * @author 정용길
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonCodeVO extends CmmVO {

    private static final long serialVersionUID = -1829122321337723874L;

    private String comCdFg;
    private String comCd;
    private String comCdNm;

    List<DefaultMap<String>> codeList;
}
