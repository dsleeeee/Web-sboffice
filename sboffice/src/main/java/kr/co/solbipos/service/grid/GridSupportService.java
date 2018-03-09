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
     */

    /**
     * 그리드 컬럼 레이아웃 저장
     * 
     * @param gridDispItem {@link GridDispItem}
     * @return
     */
    public int insertGridItem(GridDispItem gridDispItem);

    /**
     * 그리드 컬럼 레이아웃 업데이트
     * 
     * @param gridDispItem {@link GridDispItem}
     * @return
     */
    public int updateGridItem(GridDispItem gridDispItem);

    /**
     * 그리드 컬럼 레이아웃 저장
     * 
     * @param gridDispItem {@link GridDispItem}
     * @return {@link GridDispItem} 조회 결과
     */
    GridDispItem selectGridItem(GridDispItem gridDispItem);
    
    

    /**
     * 
     * 그리드 컬럼 관련
     * 
     */
    
    /**
     * 디비 조회 결과로 다국어 적용한 그리드 헤더 데이터를 리턴
     * 
     * @param map 조회한 결과의 컬럼이 key=value 타입의 {@code DefaultMap}. key 가 다국어 처리됨
     * @param columnFilter {@code List<String>} map 의 colum filter
     * @see GridSupportService#getGridColumns(DefaultMap, List)
     */
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map);

    /**
     * 디비 조회 결과로 다국어 적용한 그리드 헤더 데이터를 리턴
     * 
     * @param map 조회한 결과의 컬럼이 key=value 타입의 {@code DefaultMap}. key 가 다국어 처리됨
     * @param columnFilter {@code List<String>} map 의 colum filter
     * @return {@code List<Object>} 타입으로 {@code jsp} 에서 grid 생성 columns 옵션에 바로 사용 가능
     */
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map,
            List<String> columnFilter);

    /**
     * 테이블 명으로 그리드 헤더 이름을 가져옴
     * 
     * @param table {@code String} 타입의 테이블 명
     * @return {@code String} 타입의 그리드 헤더 이름(json 형태)
     * @see GridSupportService#getGridColumsTable(String, List)
     */
    public String getGridColumsTable(String table);
    
    /**
     * {@code table} 명으로 컬럼 이름을 조회해서 다국어를 적용 {@code json} 포맷의 {@code String} 타입으로 리턴
     * 
     * @param table {@code String} 타입의 테이블 명
     * @param columnFilter {@code List<String>} 타입의 사용을 원하는 컬럼 필터
     * @return {@code String} 타입의 그리드 헤더 이름(json 포맷)
     */
    public String getGridColumsTable(String table, List<String> columnFilter);

    /**
     * {@code table} 명으로 컬럼 이름을 조회해서 다국어를 적용 {@code json} 포맷의 {@code String} 타입으로 리턴
     * 
     * @param keyName {@code String} 타입의 다국어 메시지 코드 
     * @return {@code HashMap<String, String>} 타입의 <br>
     * 예)<br>
     * { "binding" : {@code param keyName}, "header" : 다국어 처리된 메시지 }
     */
    public HashMap<String, String> makeHeader(String keyName);
}


