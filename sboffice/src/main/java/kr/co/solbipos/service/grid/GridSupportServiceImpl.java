package kr.co.solbipos.service.grid;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.domain.cmm.GridDispItem;
import kr.co.solbipos.application.persistance.cmm.CmmGridMapper;
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GridSupportServiceImpl implements GridSupportService {

    @Autowired
    MessageService messageService;

    @Autowired
    SampleService sampleService;
    
    @Autowired
    CmmGridMapper cmmGridMapper;

    final static String COLUMN_BINDING = "binding";
    final static String COLUMN_NAME = "header";
    
    /** 
     * 
     * 그리드 디스플레이 컬럼 관련 디비 접속 부분
     * 
     * */
    
    @Override
    public int insertGridItem(GridDispItem gridDispItem) {
        return cmmGridMapper.insertGridItem(gridDispItem);
    }

    @Override
    public GridDispItem selectGridItem(GridDispItem gridDispItem) {
        return cmmGridMapper.selectGridItem(gridDispItem);
    }

    @Override
    public int updateGridItem(GridDispItem gridDispItem) {
        return cmmGridMapper.updateGridItem(gridDispItem);
    }
    
    /** 
     *
     * 그리드 컬럼 관련
     * 
     * */

    @Override
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map,
            List<String> columnFilter) {
        if (ObjectUtils.isEmpty(map)) {
            return null;
        }
        /**
         * 컬럼을 필터 적용여부 true : 적용, false : 미적용
         */
        boolean isSelectColumn = !ObjectUtils.isEmpty(columnFilter);

        Set<String> keySet = map.keySet();
        List<HashMap<String, String>> rList = new ArrayList<HashMap<String, String>>();
        for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
            String keyName = (String) i.next();

            if (isSelectColumn) {
                if (columnFilter.indexOf(keyName) > -1) {
                    rList.add(makeHeader(keyName));
                }
            } else {
                rList.add(makeHeader(keyName));
            }

        }
        return rList;
    }

    @Override
    public String getGridColumsTable(String table, List<String> columnFilter) {
        // 테이블 컬럼 조회
        List<DefaultMap<Object>> columns = sampleService.selectColumns(table);

        if (ObjectUtils.isEmpty(columns)) {
            return null;
        }

        boolean isSelectColumn = !ObjectUtils.isEmpty(columnFilter);
        List<HashMap<String, String>> rList = new ArrayList<HashMap<String, String>>();

        rList.add(makeHeader("rnum")); // 로우 no 추가

        int size = columns.size();
        for (int i = 0; i < size; i++) {
            DefaultMap<Object> map = columns.get(i);
            String column = map.getStr("columnName");
            column = toCamelCaseName(column);

            if (isSelectColumn) {
                if (columnFilter.indexOf(column) > -1) {
                    rList.add(makeHeader(column));
                }
            } else {
                rList.add(makeHeader(column));
            }
        }

        return convertToJson(rList);
    }

    @Override
    public String getGridColumsTable(String table) {
        return getGridColumsTable(table, null);
    }

    @Override
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map) {
        return getGridColumns(map, null);
    }

    @Override
    public HashMap<String, String> makeHeader(String keyName) {

        String msg = Optional.ofNullable(messageService.get(keyName)) // 키 이름으로 다국어 메세지를 가져옴
                .filter(x -> x.trim().length() != 0) // 다국어 메세지가 없으면 키 이름을 돌려줌
                .orElse(keyName);

        HashMap<String, String> rMap = new HashMap<>();
        rMap.put(COLUMN_BINDING, keyName);
        rMap.put(COLUMN_NAME, msg);
        return rMap;
    }

}
