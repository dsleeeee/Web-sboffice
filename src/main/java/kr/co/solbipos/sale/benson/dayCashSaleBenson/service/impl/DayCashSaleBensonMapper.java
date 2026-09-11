package kr.co.solbipos.sale.benson.dayCashSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.dayCashSaleBenson.service.DayCashSaleBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayCashSaleBensonMapper.java
 * @Description : 벤슨 > 매출현황2 > 일별(현금)현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayCashSaleBensonMapper {

    /** 일별(현금)현황 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(DayCashSaleBensonVO dayCashSaleBensonVO);

    /** 일별(현금)현황 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(DayCashSaleBensonVO dayCashSaleBensonVO);

    /** 일별(현금)현황 - 객수 컬럼 리스트 조회 */
    List<DefaultMap<String>> getGuestColList(DayCashSaleBensonVO dayCashSaleBensonVO);

    /** 일별(현금)현황 - 일별종합(현금) 리스트 조회 */
    List<DefaultMap<String>> getDayCashTotalBensonList(DayCashSaleBensonVO dayCashSaleBensonVO);
}
