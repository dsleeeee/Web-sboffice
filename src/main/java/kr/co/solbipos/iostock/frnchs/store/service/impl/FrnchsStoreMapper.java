package kr.co.solbipos.iostock.frnchs.store.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreVO;

@Mapper
@Repository
public interface FrnchsStoreMapper {
	/** 매장별 입출고내역 - 매장별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreList(FrnchsStoreVO frnchsStoreVO);
    /** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회 */
    List<DefaultMap<String>> getFrnchsStoreInfoList(FrnchsStoreVO frnchsStoreVO);
    /** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreDtlList(FrnchsStoreVO frnchsStoreVO);

}
