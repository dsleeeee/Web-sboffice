package kr.co.solbipos.sale.status.weight.weight.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayVO;
import kr.co.solbipos.sale.status.weight.weight.service.WeightVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : WeightMapper.java
 * @Description : 매출관리 > 매출현황2 > 중량별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.08  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface WeightMapper {
    /** 중량별 리스트 조회 */
    List<DefaultMap<String>> getWeightList(WeightVO weightVO);

    /** 일자별 리스트 조회 */
    List<DefaultMap<String>> getWeightDayList(WeightVO weightVO);

    /** 상품별 리스트 조회 */
    List<DefaultMap<String>> getWeightProdList(WeightVO weightVO);

    /** 상품별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getWeightProdExcelList(WeightVO weightVO);

}
