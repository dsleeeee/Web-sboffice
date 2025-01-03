package kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.ProdSaleMonthStoreMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSaleMonthStoreMomsMapper.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출월별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.12.20  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdSaleMonthStoreMomsMapper {

    /** 상품매출월별(매장) - 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreMomsList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreMomsExcelList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreMomsExcelDivisionList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    int getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 insert */
    int getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 순번(자동채번) */
    String getDownloadSeq(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 화면별 건당 다운로드 예상시간(초) */
    String getExpectedTimeSecond(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    String getDivisionExcelDownloadCntChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    int getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO);
}
