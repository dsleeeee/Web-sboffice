package kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.ProdSaleMonthStoreBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSaleMonthStoreBensonMapper.java
 * @Description : 벤슨 > 간소화화면 > 상품매출월별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdSaleMonthStoreBensonMapper {

    /** 상품매출월별(매장) - 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelDivisionList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    int getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 insert */
    int getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 순번(자동채번) */
    String getDownloadSeq(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 화면별 건당 다운로드 예상시간(초) */
    String getExpectedTimeSecond(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    String getDivisionExcelDownloadCntChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    int getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO);
}
