package kr.co.common.service.grid.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.grid.GridSupportService;
import kr.co.common.service.message.MessageService;
import kr.co.sample.application.service.SampleService;
import kr.co.solbipos.application.common.service.GridDispItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static kr.co.common.utils.spring.StringUtil.toCamelCaseName;

@Service("gridSupportService")
public class GridSupportServiceImpl implements GridSupportService {
    
    final static String COLUMN_BINDING = "binding";
    final static String COLUMN_NAME = "header";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CmmGridMapper cmmGridMapper;
    private final MessageService messageService;
    private final SampleService sampleService;

    /** Constructor Injection */
    @Autowired
    public GridSupportServiceImpl(CmmGridMapper cmmGridMapper, MessageService messageService,
        SampleService sampleService) {
        this.cmmGridMapper = cmmGridMapper;
        this.messageService = messageService;
        this.sampleService = sampleService;
    }

    /**
     *
     * 그리드 디스플레이 컬럼 관련 디비 접속 부분
     *
     * */

    @Override
    public int insertGridItem(GridDispItemVO gridDispItemVO) {
        return cmmGridMapper.insertGridItem(gridDispItemVO);
    }

    @Override
    public GridDispItemVO selectGridItem(GridDispItemVO gridDispItemVO) {
        return cmmGridMapper.selectGridItem(gridDispItemVO);
    }

    @Override
    public int updateGridItem(GridDispItemVO gridDispItemVO) {
        return cmmGridMapper.updateGridItem(gridDispItemVO);
    }

    /**
     *
     * 그리드 컬럼 관련
     *
     * */

    @Override
    public List<HashMap<String, String>> getGridColumns(DefaultMap<Object> map) {
        return getGridColumns(map, null);
    }

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
    public String getGridColumsTable(String table) {
        return getGridColumsTable(table, null);
    }

    @Override
    public String getGridColumsTable(String table, List<String> columnFilter) {
        // 테이블 컬럼 조회
        List<DefaultMap<Object>> columns = sampleService.selectColumns(table);

        if (ObjectUtils.isEmpty(columns)) {
            return null;
        }

        boolean isSelectColumn = !ObjectUtils.isEmpty(columnFilter);

        LOGGER.debug("■■■■■■■■ columnFilter : " + columnFilter + " , isSelectColumn : "+ isSelectColumn);

        List<HashMap<String, String>> gridHeaderNames = new ArrayList<HashMap<String, String>>();

        gridHeaderNames.add(makeHeader("rnum")); // 로우 no 추가

        int size = columns.size();
        for (int i = 0; i < size; i++) {
            DefaultMap<Object> map = columns.get(i);
            String column = map.getStr("columnName");
            column = toCamelCaseName(column);

            if (isSelectColumn) {
                if (columnFilter.indexOf(column) > -1) {
                    gridHeaderNames.add(makeHeader(column));
                }
            } else {
                gridHeaderNames.add(makeHeader(column));
            }
        }
        // json 포맷의 string 타입으로 변경
        return convertToJson(gridHeaderNames);
    }

    @Override
    public HashMap<String, String> makeHeader(String keyName) {
        String msg = Optional.ofNullable(messageService.get(keyName)) // 키 이름으로 다국어 메세지를 가져옴
                .filter(x -> x.trim().length() != 0) // 다국어 메세지가 없으면 키 이름을 돌려줌
                .orElse(keyName);

        LOGGER.debug("■■■■■■■■■■■■■■■■■■■■■■■■  makeHeader keyName: " + keyName + ", msg : "+ msg);

        HashMap<String, String> rMap = new HashMap<>();
        rMap.put(COLUMN_BINDING, keyName);
        rMap.put(COLUMN_NAME, msg);
        return rMap;
    }

}
