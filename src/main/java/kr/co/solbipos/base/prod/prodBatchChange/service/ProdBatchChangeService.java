package kr.co.solbipos.base.prod.prodBatchChange.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdBatchChangeService.java
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
public interface ProdBatchChangeService {

    /** 상품정보일괄변경 조회 */
    List<DefaultMap<Object>> getProdBatchChangeList(ProdBatchChangeVO prodBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 상품정보일괄변경 저장(판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분) */
    int getProdBatchChangeSave(ProdBatchChangeVO[] prodBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 상품정보일괄변경 저장(브랜드, 상품분류) */
    int getProdBatchChange2Save(ProdBatchChangeVO[] prodBatchChangeVOs, SessionInfoVO sessionInfoVO);
}