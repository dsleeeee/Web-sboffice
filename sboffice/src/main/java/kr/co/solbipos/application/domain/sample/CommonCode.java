package kr.co.solbipos.application.domain.sample;

import java.util.List;
import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.structure.DefaultMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonCode extends Cmm {

    private static final long serialVersionUID = 1L;

    private String comCdFg;
    private String comCd;
    private String comCdNm;

    List<DefaultMap<CommonCode>> codeList;
}
