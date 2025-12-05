package kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.InStockReportDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : InStockReportDtlMapper.java
 * @Description : 국민대 > 매입관리 > 매입처별 상세매입내역(상품별)
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
public interface InStockReportDtlMapper {

    /** 매입처별 상세내역서(상품별) - 조회 */
    List<DefaultMap<String>> getInStockReportDtlList(InStockReportDtlVO inStockReportDtlVO);
}
