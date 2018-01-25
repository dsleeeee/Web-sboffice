package kr.co.solbipos.utils.grid;


import static org.springframework.util.ObjectUtils.*;
import java.util.HashMap;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.structure.Result.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길
 *
 */
@Slf4j
public class ReturnUtil {

    private static final String LIST = "list"; // 리스트 데이터의 key
    private static final String PAGE = "page"; // page 객체가 담겨있는 key

    /**
     * 
     * @param status
     * @return
     */
    public static JsonResult returnJson(Status status) {
        Result result = new Result(status);
        JsonResult json = new JsonResult(result);
        return json;
    }

    /**
     * 
     * @param status
     * @param data
     * @return
     */
    public static JsonResult returnJson(Status status, Object data) {
        return genJsonResult(status, data);
    }

    /**
     * 
     * @param status
     * @param key
     * @param value
     * @return
     */
    public static JsonResult returnJson(Status status, String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return genJsonResult(status, map);
    }

    /**
     * 
     * @param status
     * @param data
     * @param page
     * @return
     */
    public static JsonResult returnListJson(Status status, Object data, Object page) {
        return genListJsonResult(status, data, page);
    }

    /**
     * 
     * @param status
     * @param data
     * @return
     */
    public static JsonResult returnListJson(Status status, Object data) {
        return genListJsonResult(status, data, null);
    }

    /**
     * 
     * @param status
     * @param data
     * @param page
     * @return
     */
    private static JsonResult genListJsonResult(Status status, Object data, Object page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(LIST, data);
        if (!isEmpty(page)) {
            map.put(PAGE, page);
        }
        return genJsonResult(status, map);
    }

    /**
     * 
     * @param status
     * @param map
     * @return
     */
    private static JsonResult genJsonResult(Status status, Object obj) {
        Result result = new Result(status, obj);
        JsonResult json = new JsonResult(result);
        return json;
    }
}


