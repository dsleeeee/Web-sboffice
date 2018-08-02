package kr.co.common.utils.jsp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kr.co.common.service.grid.GridSupportService;

/**
 * {@code JSP} 에서 호출 하는 그리드 컬럼 관련 Util
 * 
 * @author 정용길
 *
 */
@Component("columnList")
public class ColumnList {

    @Autowired
    GridSupportService gsService;

    /**
      * 그리드의 header 정보를 다국어 처리한 string 형태로 돌려줌 <br>
      * {@code JSP} 에서 호출 예) ${{@code cl.getColumnList('SSL_TRDTL_T')}}; 
      * 
      * @param table {@code String} 타입의 테이블 명
      * @return {@code String} 형태의 그리드에 바로 사용 가능한 컬럼 데이터
      */
    public String getColumnList(String table) {
        return gsService.getGridColumsTable(table);
    }
}


