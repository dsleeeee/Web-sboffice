package kr.co.solbipos.base.promotion.promotionReportBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.promotion.promotionReportBenson.service.PromotionReportBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PromotionReportBensonMapper.java
 * @Description : 벤슨 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PromotionReportBensonMapper {

    List<DefaultMap<Object>> getPromotionReportBensonList(PromotionReportBensonVO promotionReportBensonVO);
    List<DefaultMap<Object>> getPromotionReportBensonExcelList(PromotionReportBensonVO promotionReportBensonVO);

    /** 프로모션정산 상세 조회 */
    List<DefaultMap<Object>> getPromotionReportBensonDtlList(PromotionReportBensonVO promotionReportBensonVO);
}
