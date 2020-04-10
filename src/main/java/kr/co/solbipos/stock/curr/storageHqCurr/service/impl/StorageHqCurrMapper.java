package kr.co.solbipos.stock.curr.storageHqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StorageHqCurrMapper {
    /** 창고별현재고현황 - 본사 창고별현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStorageHqCurrList(StorageHqCurrVO storageHqCurrVO);
    /** 창고별현재고현황 - 매장 창고별현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStorageStorageCurrList(StorageHqCurrVO storageHqCurrVO);
    /** 창고별현재고현황 - 매장 창고별현재고현황 본사창고 리스트 조회 */
	List<DefaultMap<String>> getStorageHqList(StorageHqCurrVO storageHqCurrVO);
	/** 창고별현재고현황 - 매장 창고별현재고현황 매장창고 리스트 조회 */
	List<DefaultMap<String>> getStorageStorageList(StorageHqCurrVO storageHqCurrVO);
}
