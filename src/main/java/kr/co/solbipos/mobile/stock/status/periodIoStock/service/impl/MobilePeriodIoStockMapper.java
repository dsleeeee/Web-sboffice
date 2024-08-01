package kr.co.solbipos.mobile.stock.status.periodIoStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobilePeriodIoStockMapper.java
 * @Description : (모바일)재고현황 > 기간수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobilePeriodIoStockMapper {

    /** 기간수불현황 - 기간수불현황 리스트 조회(본사) */
    List<DefaultMap<String>> getPeriodIoStockList(MobilePeriodIoStockVO mobilePeriodIostockVO);

    /** 기간수불현황 - 기간수불현황 리스트 조회(매장) */
    List<DefaultMap<String>> getPeriodIoStockStoreList(MobilePeriodIoStockVO mobilePeriodIostockVO);

    /** 기간수불현황 - 기간수불현황 상세 리스트 조회(본사) */
    List<DefaultMap<String>> getPeriodIoStockProdDtlList(MobilePeriodIoStockVO mobilePeriodIostockVO);

    /** 기간수불현황 - 기간수불현황 상세 리스트 조회(매장) */
    List<DefaultMap<String>> getPeriodIoStockProdStoreDtlList(MobilePeriodIoStockVO mobilePeriodIostockVO);

    /** 기간수불현황 - 기간수불현황 엑셀 전체다운로드 조회(본사) */
    List<DefaultMap<String>> getPeriodIoStockExcelList(MobilePeriodIoStockVO mobilePeriodIostockVO);

    /** 기간수불현황 - 기간수불현황 엑셀 전체다운로드 조회(매장) */
    List<DefaultMap<String>> getPeriodIoStockStoreExcelList(MobilePeriodIoStockVO mobilePeriodIostockVO);

}
