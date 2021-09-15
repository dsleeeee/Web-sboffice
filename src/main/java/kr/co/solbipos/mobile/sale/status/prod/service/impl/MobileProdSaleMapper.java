package kr.co.solbipos.mobile.sale.status.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MobileProdSaleMapper {

    /** 모바일 매출현황 - 상품별매출현황 */
    List<DefaultMap<String>> getProdSaleList(MobileProdSaleVO mobileProdSaleVO);

    /** 모바일 매출현황 - 다중매장조회 */
    List<DefaultMap<String>> getMultiStoreList(MobileProdSaleVO mobileProdSaleVO);

    /** 모바일 매출현황 - 매장조회 */
    List<DefaultMap<String>> getStoreList(MobileProdSaleVO mobileProdSaleVO);
}
