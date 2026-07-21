package kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service.DcfgPeriodSaleBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DcfgPeriodSaleBensonMapper.java
 * @Description : 벤슨 > 매출분석 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.20
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DcfgPeriodSaleBensonMapper {

    /**
     * 할인구분기간상세 리스트 조회
     * @param dcfgPeriodSaleBensonVO
     * @return
     */
    List<DefaultMap<String>> getDcfgPeriodSaleBensonList(DcfgPeriodSaleBensonVO dcfgPeriodSaleBensonVO);
}
