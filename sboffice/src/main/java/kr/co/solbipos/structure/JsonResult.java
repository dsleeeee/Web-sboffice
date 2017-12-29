package kr.co.solbipos.structure;

/**
 * JSON 유형의 전달 결과
 * 
 * @author 이호원
 */
public class JsonResult extends Result {
    /** 응답 내용 */
    String message;
    /** 응답 URL */
    String url;

    public JsonResult(Result result) {
        super(result.getStatus(), result.getData());
    }

    public JsonResult(Status status) {
        super(status);
    }

    public JsonResult(Status status, Object data) {
        super(status, data);
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return this.message;
    }

    public JsonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUrl() {
        return this.url;
    }
}
