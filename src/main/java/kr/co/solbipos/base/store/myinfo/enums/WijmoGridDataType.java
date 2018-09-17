package kr.co.solbipos.base.store.myinfo.enums;

/**
 * @Class Name : WijmoGridDataType.java
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
public enum WijmoGridDataType{
    Object( 0 ),

    String( 1 ),

    Number( 2 ),

    Boolean( 3 ),

    Date( 4 ),

    Array( 5 );

    private Integer dataType;

    WijmoGridDataType( Integer dataType ){
        this.dataType = dataType;
    }

    public Integer getDataType(){
        return this.dataType;
    }
}
