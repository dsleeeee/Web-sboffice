package kr.co.solbipos.store.manage.storeview.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storeview.service.StoreViewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @Class Name : StoreViewMapper.java
* @Description : 가맹점 관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.07  김영근      최초생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.07
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
public interface StoreViewMapper { 

    /** 매장정보 목록 조회 */
    List<DefaultMap<String>> getStoreViewList(StoreViewVO storeViewVO);
}
