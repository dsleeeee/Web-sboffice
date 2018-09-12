package kr.co.solbipos.iostock.order.storeClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.storeClose.service.StoreCloseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreCloseMapper {
    /** 매장요청마감 마감월 리스트 조회 */
    List<DefaultMap<String>> getStoreCloseList(StoreCloseVO storeCloseVO);

    /** 매장요청마감 마감월 상세 리스트 조회 */
    List<DefaultMap<String>> getStoreCloseDtlList(StoreCloseVO storeCloseVO);

    /** 매장요청마감 마감일 마감여부 등록 */
    int insertStoreClose(StoreCloseVO storeCloseVO);

    /** 매장요청마감 마감일 마감여부 삭제 */
    int deleteStoreClose(StoreCloseVO storeCloseVO);
}
