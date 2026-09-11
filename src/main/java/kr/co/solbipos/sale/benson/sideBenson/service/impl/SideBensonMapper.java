package kr.co.solbipos.sale.benson.sideBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.sideBenson.service.SideBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SideBensonMapper.java
 * @Description : 벤슨 > 매출현황2 > 상품별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승        최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface SideBensonMapper {
    /** 상품분류별 조회 */
    List<DefaultMap<String>> sideProdClass(SideBensonVO sideBensonVO);

    /** 상품분류별 조회 */
    List<DefaultMap<String>> sideProdClassExcel(SideBensonVO sideBensonVO);

}
