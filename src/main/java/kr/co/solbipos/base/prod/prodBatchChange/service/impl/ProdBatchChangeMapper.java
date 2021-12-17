package kr.co.solbipos.base.prod.prodBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdBatchChangeMapper.java
 * @Description : 기초관리 > 상품관리 > 상품정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 20201.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdBatchChangeMapper {

    /** 상품정보일괄변경 조회 */
    List<DefaultMap<Object>> getProdBatchChangeList(ProdBatchChangeVO prodBatchChangeVO);

    /** 상품정보일괄변경 저장 update(판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분) */
    int getProdBatchChangeSaveUpdate(ProdBatchChangeVO prodBatchChangeVO);

    /** 상품정보일괄변경 저장 매장에 update(판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분) */
    int getProdBatchChangeSaveStoreUpdate(ProdBatchChangeVO prodBatchChangeVO);

    /** 상품정보일괄변경 저장 update(브랜드, 상품분류) */
    int getProdBatchChange2SaveUpdate(ProdBatchChangeVO prodBatchChangeVO);

    /** 상품정보일괄변경 저장 매장에 update(브랜드, 상품분류) */
    int getProdBatchChange2SaveStoreUpdate(ProdBatchChangeVO prodBatchChangeVO);
}