package kr.co.solbipos.utils.jsp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kr.co.solbipos.service.grid.GridSupportService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * grid
 * 
 * @author 정용길
 *
 */
@Slf4j
@Component("columnList")
public class ColumnList {

    @Autowired
    GridSupportService gsService;

    /**
      * 테이블 명으로 그리드의 header 를 string 형태로 돌려줌
      * jsp 에서 호출 예) ${cl.getColumnList('SSL_TRDTL_T')};
      * 
      * @param table 해당하는 테이블 명
      * @return
      */
    public String getColumnList(String table) {
        return gsService.getGridColumsTable(table);
    }
}


