package kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.InStockReportByAcquireVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : InStockReportByAcquireMapper.java
 * @Description : 국민대 > 매입관리 > 매입처별 입고내역서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface InStockReportByAcquireMapper {

    /** 매입처별 입고 내역서 - 조회 */
    List<DefaultMap<String>> getInStockReportByAcquireList(InStockReportByAcquireVO inStockReportByAcquireVO);
}
