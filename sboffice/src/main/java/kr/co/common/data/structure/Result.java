package kr.co.common.data.structure;

import kr.co.common.data.enums.Status;
import lombok.Data;

/**
 * @author 정용길
 *
 */
@Data
public class Result {

    /** 응답 유형 */
    Status status;

    /** 데이터 ( 필요시 전달 ) */
    Object data;

    /** 리턴 URL */
    String url;

    /** 리턴 메시지 */
    String message;

    protected Result() {}

    public Result(Status status) {
        this.status = status;
    }

    public Result(Status status, Object data) {
        this.status = status;
        this.data = data;
    }
}
