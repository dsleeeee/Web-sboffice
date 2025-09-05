package kr.co.solbipos.sale.marketing.salePerfCompare.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.marketing.salePerfCompare.service.SalePerfCompareVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : SalePerfCompareMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 매출실적비교
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.08  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SalePerfCompareMapper {
    List<DefaultMap<Object>> getSalePerfCompareList(SalePerfCompareVO salePerfCompareVO);

    List<DefaultMap<Object>> getSalePerfCompareDtlList(SalePerfCompareVO salePerfCompareVO);

    List<DefaultMap<Object>> getSalePerfCompareStoreList(SalePerfCompareVO salePerfCompareVO);
}
