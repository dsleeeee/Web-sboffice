package kr.co.sample.application.domain;

import kr.co.solbipos.application.domain.cmm.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TestTableVO extends PageVO {
    private Long keyNo;
    private String keyValue;
}
