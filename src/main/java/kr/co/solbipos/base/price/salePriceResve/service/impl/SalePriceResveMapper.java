package kr.co.solbipos.base.price.salePriceResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SalePriceResveMapper.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SalePriceResveMapper {

    /** 가격예약(본사판매가) 리스트 조회 */
    List<DefaultMap<String>> getHqSalePriceResveList(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 기존 예약 판매가 삭제 */
    int deleteHqSalePriceResve(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해) */
    int getHqSalePriceCnt(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 기존 판매가 히스토리에 저장 */
    int insertHqSalePriceHistory(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 새 예약 판매가 등록 */
    int insertHqSalePrice(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 본사 예약 판매가 매장적용 */
    int insertHqSalePriceToStore(SalePriceResveVO salePriceResveVO);

    /** 가격예약(본사판매가) 상품가격정보 조회 */
    List<DefaultMap<String>> getHqSalePriceInfo(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) [상품별 판매가관리] 리스트 조회 */
    List<DefaultMap<String>> getStoreProdSalePriceResveList(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) [매장별 판매가관리] 리스트 조회 */
    List<DefaultMap<String>> getStoreStoreSalePriceResveList(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) 기존 예약 판매가 삭제 */
    int deleteStoreSalePriceResve(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해) */
    int getStoreSalePriceCnt(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) 기존 판매가 히스토리에 저장 */
    int insertStoreSalePriceHistory(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) 새 예약 판매가 등록 */
    int insertStoreSalePrice(SalePriceResveVO salePriceResveVO);

    /** 가격예약(매장판매가) 상품가격정보 조회 */
    List<DefaultMap<String>> getStoreSalePriceInfo(SalePriceResveVO salePriceResveVO);

}
