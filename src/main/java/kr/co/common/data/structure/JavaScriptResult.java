package kr.co.common.data.structure;

import kr.co.common.data.enums.Status;

/**
 * @Class Name : JavaScriptResult.java
 * @Description : Javascript 유형의 전달 결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  이호원      최초생성
 *
 * @author NHN한국사이버결제 KCP 이호원
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
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
    
    
    /**
     * @return the script
     */
    public String getScript() {
        return script;
    }
    /**
     * @param script the script to set
     */
    public void setScript(String script) {
        this.script = script;
    }
    
}
