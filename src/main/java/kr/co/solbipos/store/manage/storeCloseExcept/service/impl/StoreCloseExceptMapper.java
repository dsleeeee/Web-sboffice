package kr.co.solbipos.store.manage.storeCloseExcept.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreCloseExceptMapper.java
 * @Description : 기초관리 > 매장정보관리 > 폐점제외매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.04.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreCloseExceptMapper {

    /** 밴 콤보박스 조회 */
    List<DefaultMap<String>> getVanComboList();

    /** 매장 조회 */
    List<DefaultMap<String>> getStoreList(StoreCloseExceptVO storeCloseExceptVO);

    /** 폐점제외매장 조회 */
    List<DefaultMap<String>> getStoreCloseExceptList(StoreCloseExceptVO storeCloseExceptVO);

    /** 폐점제외매장 등록 */
    int saveStoreCloseExcept(StoreCloseExceptVO storeCloseExceptVO);

    /** 폐점제외매장 삭제 */
    int deleteStoreCloseExcept(StoreCloseExceptVO storeCloseExceptVO);
}
