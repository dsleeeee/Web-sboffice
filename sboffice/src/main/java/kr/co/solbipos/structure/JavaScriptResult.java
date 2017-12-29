package kr.co.solbipos.structure;

/**
 * Javascript 유형의 전달 결과
 * 
 * @author 이호원
 */
public class JavaScriptResult extends Result {
    /** 스크립트 문자열 */
    String script;

    public JavaScriptResult(Status status) {
        super(status);
    }

    public JavaScriptResult(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public JavaScriptResult setScript(String script) {
        this.script = script;
        return this;
    }
}
