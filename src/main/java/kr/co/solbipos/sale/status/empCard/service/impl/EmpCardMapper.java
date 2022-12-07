package kr.co.solbipos.sale.status.empCard.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.empCard.service.EmpCardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpCardMapper.java
 * @Description : 매출관리 > 매출현황2 > 사원카드매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface EmpCardMapper {

    /** 조회 */
    List<DefaultMap<Object>> getEmpCardList(EmpCardVO empCardVO);

}