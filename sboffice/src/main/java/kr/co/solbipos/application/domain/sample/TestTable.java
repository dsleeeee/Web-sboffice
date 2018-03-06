package kr.co.solbipos.application.domain.sample;

import kr.co.solbipos.application.domain.cmm.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TestTable extends Page {
    private Long keyNo;
    private String keyValue;
}
