package kr.co.solbipos.utils.grid;


import static org.springframework.util.ObjectUtils.*;
import java.util.HashMap;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import kr.co.solbipos.application.domain.cmm.Page;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;

/**
 * @author 정용길
 *
 */
public class ReturnUtil {

    private static final String LIST = "list"; // 리스트 데이터의 key
    private static final String PAGE = "page"; // page 객체가 담겨있는 key

    /**
     * 
     * @param status
     * @return
     */
    public static Result returnJson(Status status) {
        return new Result(status);
    }

    /**
     * 
     * @param status
     * @param data
     * @return
     */
    public static Result returnJson(Status status, Object data) {
        return genJsonResult(status, data);
    }

    /**
     * 
     * @param status
     * @param key
     * @param value
     * @return
     */
    public static Result returnJson(Status status, String key, Object value) {
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
    public static Result returnListJson(Status status, Object data, Object page) {
        return genListJsonResult(status, data, page);
    }

    /**
     * 
     * @param status
     * @param data
     * @return
     */
    public static Result returnListJson(Status status, Object data) {
        return genListJsonResult(status, data, null);
    }

    /**
     * 
     * @param status
     * @param data
     * @param page
     * @return
     */
    private static Result genListJsonResult(Status status, Object data, Object page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(LIST, data);
        
        /** 
         * {@author 정용길}
         * 전체 갯수를 세팅해준다 컨트롤러에서 따로 set 할 필요가 없어짐<br>
         * page 객체가 kr.co.solbipos.application.domain.cmm.Page 객체를 상속 받고
         * 조회 쿼리에 전체 갯수 조회 컬럼을 TOT_CNT가 있어야됨
         * */
        if(!isEmpty(data) && !isEmpty(page)) {
            @SuppressWarnings("unchecked")
            List<DefaultMap<Object>> a = (List<DefaultMap<Object>>) data;
            
            if(Page.class.isAssignableFrom(page.getClass().getSuperclass())) {
                Page p = Page.class.cast(page);
                p.setTotalCount(a.get(0).getInt("totCnt"));
            }
        }
        
        if (!isEmpty(page)) {
            map.put(PAGE, page);
        }
        return genJsonResult(status, map);
    }

    /**
      * controller 에서 BindingResult 에러 처리시에 에러 내역을 json 형태로 리턴 
      * 
      * @param bindingResult
      * @return
      */
    public static Result returnJsonBindingFieldError(BindingResult bindingResult) {
        List<ObjectError> errros = bindingResult.getAllErrors();
        HashMap<String, String> map = new HashMap<>();
        for (Object object : errros) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return returnJson(Status.FAIL, map);
    }
    
    /**
     * 
     * @param status
     * @param map
     * @return
     */
    private static Result genJsonResult(Status status, Object obj) {
        return new Result(status, obj);
    }
    
    /**
      * 
      * @param status
      * @param message
      * @return
      */
    public static Result genJsonResultMsg(Status status, String message) {
        Result result = new Result(status);
        result.setMessage(message);
        return result;
    }
}


