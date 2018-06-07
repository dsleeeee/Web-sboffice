package kr.co.solbipos.store.manage.storemanage.service.impl;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리
 * 
 * @author 김지은
 */
public interface StoreManageMapper {

    /**
     * 매장 목록 조회
     * @param storeManageVO
     * @return
     */
    List<DefaultMap<String>> list(StoreManageVO storeManageVO);
}
