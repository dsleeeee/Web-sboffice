package kr.co.solbipos.structure;

import kr.co.solbipos.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Javascript 유형의 전달 결과
 * 
 * @author 이호원
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class JavaScriptResult extends Result {

    /** 스크립트 문자열 */
    String script;

    public JavaScriptResult(Status status) {
        super(status);
    }

    public JavaScriptResult(String script) {
        this.script = script;
    }

}
