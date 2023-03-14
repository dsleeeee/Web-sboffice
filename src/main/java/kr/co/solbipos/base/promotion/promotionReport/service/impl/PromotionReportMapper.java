package kr.co.solbipos.base.promotion.promotionReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.promotion.promotionReport.service.PromotionReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PromotionReportMapper.java
 * @Description : 기초관리 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PromotionReportMapper {

    List<DefaultMap<Object>> getPromotionReportList(PromotionReportVO promotionReportVO);
    List<DefaultMap<Object>> getPromotionReportExcelList(PromotionReportVO promotionReportVO);

    /** 프로모션정산 상세 조회 */
    List<DefaultMap<Object>> getPromotionReportDtlList(PromotionReportVO promotionReportVO);
}