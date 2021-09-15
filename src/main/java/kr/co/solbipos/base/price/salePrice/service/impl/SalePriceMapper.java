package kr.co.solbipos.base.price.salePrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SalePriceMapper.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SalePriceMapper {

    /** 상품 정보 조회 */
    DefaultMap<String> getProdInfo(SalePriceVO salePriceVO);

    /** 상품별 매장 판매가 조회 */
    List<DefaultMap<String>> getProdSalePriceList(SalePriceVO salePriceVO);

    /** 등록 판매가 조회 */
    int getRegistProdCount(SalePriceVO salePriceVO);

    /** 상품별 매장 판매가 저장 */
    int updateStoreProdSalePriceHistory(SalePriceVO salePriceVO);

    /** 상품별 매장 판매가 저장 */
    int modifyStoreProdSalePrice(SalePriceVO salePriceVO);

    /** 상품별 매장 판매가 변경 히스토리 저장 */
//    int insertStoreProdSalePriceHistory(SalePriceVO salePriceVO);

    /** 상품별 매장 판매가 조회 */
    List<DefaultMap<String>> getStoreSalePriceList(SalePriceVO salePriceVO);

    /** 본사 판매가 조회 */
    List<DefaultMap<String>> getHqSalePriceList(SalePriceVO salePriceVO);

    /** 등록 판매가 조회(본사) */
    int getRegistHqProdCount(SalePriceVO salePriceVO);

    /** 본사 판매가 저장 */
    int updateHqProdSalePriceHistory(SalePriceVO salePriceVO);

    /** 본사 판매가 저장 */
    int modifyHqProdSalePrice(SalePriceVO salePriceVO);

    /** 전매장 적용 판매가 저장 */
    int modifyMsProdSalePrice(SalePriceVO salePriceVO);

    /** 매장 판매가 등록 */
    String saveStoreSalePrice(SalePriceVO salePriceVO);
}
