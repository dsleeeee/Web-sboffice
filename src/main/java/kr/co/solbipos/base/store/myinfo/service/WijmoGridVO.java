package kr.co.solbipos.base.store.myinfo.service;

import java.util.List;

/**
 * @Class Name : WijmoGridVO.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.27  이호원      최초생성
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.08.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class WijmoGridVO< T >{
    /** Grid 식별 */
    private String id;
    /** Grid 명 */
    private String name;
    /** 매핑 컬럼 목록 */
    private List< WijmoGridColumnVO > columns;
    /** 저장 index */
    private Long saveIndex;
    /** 저장 매핑 컬럼 목록 */
    private String saveColumn;
    /** 나열할 데이터 */
    private List< T > rows;

    public WijmoGridVO( String id, String name, List<WijmoGridColumnVO> columns, Long saveIndex,
                        String saveColumn, List<T> rows ){
        this.id = id;
        this.name = name;
        this.columns = columns;
        this.saveIndex = saveIndex;
        this.saveColumn = saveColumn;
        this.rows = rows;
    }

    public String getId(){
        return id;
    }

    public void setId( String id ){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public List< WijmoGridColumnVO > getColumns(){
        return columns;
    }

    public void setColumns( List< WijmoGridColumnVO > columns ){
        this.columns = columns;
    }

    public Long getSaveIndex(){
        return saveIndex;
    }

    public void setSaveIndex( Long saveIndex ){
        this.saveIndex = saveIndex;
    }

    public String getSaveColumn(){
        return saveColumn;
    }

    public void setSaveColumn( String saveColumn ){
        this.saveColumn = saveColumn;
    }

    public List< T > getRows(){
        return rows;
    }

    public void setRows( List<T> rows ){
        this.rows = rows;
    }
}