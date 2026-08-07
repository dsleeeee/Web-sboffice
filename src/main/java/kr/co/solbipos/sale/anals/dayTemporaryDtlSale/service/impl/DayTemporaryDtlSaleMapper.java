package kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.DayTemporaryDtlSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : DayTemporaryDtlSaleMapper.java
 * @Description : 맘스터치 > 매출분석2 > 일별 가승인 상세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayTemporaryDtlSaleMapper {

    /** 맘스터치 전용 - 매장별가승인매출 코드 조회 */
    List<DefaultMap<Object>> getTemporaryPayColList();

    /** 일별 가승인 상세매출 - 조회 */
    List<DefaultMap<Object>> getDayTemporaryDtlSaleList(DayTemporaryDtlSaleVO dayTemporaryDtlSaleVO);

}
