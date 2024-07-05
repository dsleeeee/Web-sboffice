package kr.co.solbipos.base.prod.torderQtyDisp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.torderQtyDisp.service.TorderQtyDispVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TorderQtyDispMapper.java
 * @Description : 기초관리 > 상품관리2 > T오더수량변경표시관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TorderQtyDispMapper {

    /** T오더수량변경표시관리 - 조회 */
    List<DefaultMap<Object>> getTorderQtyDispList(TorderQtyDispVO torderQtyDispVO);

    /** T오더수량변경표시관리 - 저장 merge */
    int getTorderQtyDispSaveMerge(TorderQtyDispVO torderQtyDispVO);
}