package kr.co.solbipos.store.manage.storemanage.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리
 * 
 * @author 김지은
 */
public interface StoreManageService {

    /**
     * 매장 목록 조회
     * @param storeManageVO
     * @return
     */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /**
     * 매장정보 상세조회
     * @param storeManageVO
     * @return
     */
    DefaultMap<String> getStoreDetail(StoreManageVO storeManageVO);
}
