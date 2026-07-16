package kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.ProdSaleDayStoreBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSaleDayStoreBensonMapper.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별(매장)
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
public interface ProdSaleDayStoreBensonMapper {

    /** 상품매출일별(매장) - 조회 */
    List<DefaultMap<Object>> getProdSaleDayStoreBensonList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayStoreBensonExcelList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayStoreBensonExcelDivisionList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    int getDivisionExcelDownloadUserIdChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 insert */
    int getDivisionExcelDownloadSaveInsert(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 순번(자동채번) */
    String getDownloadSeq(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 화면별 건당 다운로드 예상시간(초) */
    String getExpectedTimeSecond(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    String getDivisionExcelDownloadCntChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    int getDivisionExcelDownloadSaveUpdate(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO);
}
