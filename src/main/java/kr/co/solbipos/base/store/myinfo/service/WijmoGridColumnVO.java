package kr.co.solbipos.base.store.myinfo.service;

import kr.co.solbipos.base.store.myinfo.enums.WijmoGridDataType;

/**
 * @Class Name : WijmoGridColumnVO.java
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
public class WijmoGridColumnVO{
    /** 매핑할 컬럼 명 */
    private String binding;

    /** 컬럼 표시 명 */
    private String header;
    /**
     * Wijmo Data Type<br>
     * 0 : Object<br>
     * 1 : String<br>
     * 2 : Number<br>
     * 3 : Boolean<br>
     * 4 : Date<br>
     * 5 : Array
     */
    private WijmoGridDataType dataType;

    /**
     * 컬럼 길이<br>
     * 숫자 : 고정길이<br>
     * * : 가변길이
     */
    private Object width;

    public WijmoGridColumnVO( String binding, String header, WijmoGridDataType dataType, Object width ){
        this.binding = binding;
        this.header = header;
        this.dataType = dataType;
        this.width = width;
    }

    public String getBinding(){
        return binding;
    }

    public void setBinding( String binding ){
        this.binding = binding;
    }

    public String getHeader(){
        return header;
    }

    public void setHeader( String header ){
        this.header = header;
    }

    public Integer getDataType(){
        return dataType.getDataType();
    }

    public void setDataType( WijmoGridDataType dataType ){
        this.dataType = dataType;
    }

    public Object getWidth(){
        return width;
    }

    public void setWidth( String width ){
        this.width = width;
    }
}
