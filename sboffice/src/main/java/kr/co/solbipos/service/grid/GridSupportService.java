package kr.co.solbipos.service.grid;

import java.util.HashMap;
import java.util.List;
import kr.co.solbipos.application.domain.cmm.GridDispItem;
import kr.co.solbipos.structure.DefaultMap;

/**
 * @author 정용길
 *
 */

public interface GridSupportService {
    
    /** 
     * 
     * 그리드 디스플레이 컬럼 관련 디비 접속 부분
     * 
     * */
    
    /**
      * 
      * @param gridDispItem
      * @return
      */
    public int insertGridItem(GridDispItem gridDispItem);
    
    /**
      * 
      * @param gridDispItem
      * @return
      */
    public int updateGridItem(GridDispItem gridDispItem);
    
    /**
      * 
      * @param gridDispItem
      * @return
      */
    GridDispItem selectGridItem(GridDispItem gridDispItem);
    
    /** 
     * 
     * 그리드 컬럼 관련
     * 
     * */
    
    /**
     * grid 헤더 이름을 다국어 버전으로 가져옴
     * 
     * @param map 조회한 결과의 컬럼이 key=value 타입의 {@code DefaultMap}. key 가 다국어 처리됨
     * @param columnFilter {@code List<String>} map 의 colum filter
     * @return {@code List<Object>} 타입으로 {@code jsp} 에서 grid 생성 columns 옵션에 바로 사용 가능
     */
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map,
            List<String> columnFilter);

    /**
     * call {@code getGridColumns(DefaultMap<Object> map, null) }
     * 
     * @param map
     * @return
     */
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map);

    /**
     * 컬럼 이름을 다국어 처리해서 HashMap 으로 리턴
     * 
     * @param keyName
     * @return
     */
    public HashMap<String, String> makeHeader(String keyName);

    /**
     * table 의 컬럼을 조회해서 다국어를 적용 json 타입으로 리턴
     * 
     * @param table 조회 대상 테이블명
     * @param columnFilter 사용을 원하는 컬럼 필터
     * @return
     */
    public String getGridColumsTable(String table, List<String> columnFilter);

    /**
     * call {@code getGridColumsTable(String table, null) }
     * 
     * @param table
     * @return
     */
    public String getGridColumsTable(String table);
}


