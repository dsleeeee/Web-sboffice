package kr.co.solbipos.base.store.myinfo.enums;

import kr.co.common.data.enums.CodeEnum;

/**
 * @Class Name : NmcodeGrpCd.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.13  이호원      최초생성
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.08.13
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public enum NmcodeGrpCd implements CodeEnum{
    /** 날씨 */
    AREA( "061" ),
    /** 매장형태 */
    STORE_TYPE( "068" ),
    /** 매장그룹 */
    GRP( "101" ),
    /** 시간대분류 */
    HOUR( "096" ),
    /** 고객수 구분 */
    GUEST( "102" );

    /** 코드 */
    private String code;

    NmcodeGrpCd( String code ){
        this.code = code;
    }

    @Override
    public String getCode(){
        return this.code;
    }
}
