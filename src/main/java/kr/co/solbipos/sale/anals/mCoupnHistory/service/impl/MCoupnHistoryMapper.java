package kr.co.solbipos.sale.anals.mCoupnHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.mCoupnHistory.service.MCoupnHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MCoupnHistoryMapper.java
 * @Description :  매출관리 > 매출분석 > 모바일쿠폰이력조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.24  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.24
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface MCoupnHistoryMapper {

    /** 모바일쿠폰이력조회 */
    List<DefaultMap<Object>> getMCoupnHistory(MCoupnHistoryVO mCoupnHistoryVO);
}
