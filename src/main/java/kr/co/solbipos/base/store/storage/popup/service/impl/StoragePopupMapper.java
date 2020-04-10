package kr.co.solbipos.base.store.storage.popup.service.impl;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.solbipos.base.store.storage.popup.service.StoragePopupVO;

@Mapper
@Repository
public interface StoragePopupMapper {

    /** 창고관리 - 본사 창고정보 저장 */
	int RegStoreStorageInfo(StoragePopupVO storagePopupVO);
	/** 창고관리 - 매장 창고정보 저장 */
	int RegHqStorageInfo(StoragePopupVO storagePopupVO);
    /** 창고관리 - 본사 창고정보 수정 */
    int ModHqStorageInfo(StoragePopupVO storagePopupVO);
    /** 창고관리 - 매장 창고정보 수정 */
    int ModStoreStorageInfo(StoragePopupVO storagePopupVO);
    /** 창고관리 - storageCd 카운트 조회 */
    int StorageCdCnt(StoragePopupVO storagePopupVO);
    /** 창고관리 - 창고 재고 조회 */
    int StockCnt(StoragePopupVO storagePopupVO);
}
