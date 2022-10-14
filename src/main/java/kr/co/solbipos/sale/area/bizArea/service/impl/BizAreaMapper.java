package kr.co.solbipos.sale.area.bizArea.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.area.bizArea.service.BizAreaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BizAreaMapper.java
 * @Description : 맘스터치 > 매출분석 > 상권별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BizAreaMapper {

    /** 조회 */
    List<DefaultMap<Object>> getBizAreaList(BizAreaVO bizAreaVO);
    List<DefaultMap<Object>> getBizAreaExcelList(BizAreaVO bizAreaVO);
}