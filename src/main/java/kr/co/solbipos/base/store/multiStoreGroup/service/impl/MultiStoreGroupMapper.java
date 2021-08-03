package kr.co.solbipos.base.store.multiStoreGroup.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.multiStoreGroup.service.MultiStoreGroupVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MultiStoreGroupMapper.java
 * @Description : 기초관리 - 매장관리 - 다중매장그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.07.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MultiStoreGroupMapper {

    /** 그룹조회 */
    List<DefaultMap<Object>> getMultiStoreGroup(MultiStoreGroupVO multiStoreGroupVO);

    /** 그룹코드 생성 */
    String getMultiStoreGroupCode(MultiStoreGroupVO multiStoreGroupVO);

    /** 그룹생성 */
    int insertMultiStoreGroup(MultiStoreGroupVO multiStoreGroupVO);

    /** 그룹수정 */
    int updateMultiStoreGroup(MultiStoreGroupVO multiStoreGroupVO);







    /** 매장조회 */
    List<DefaultMap<Object>> getStoreList(MultiStoreGroupVO multiStoreGroupVO);
}
