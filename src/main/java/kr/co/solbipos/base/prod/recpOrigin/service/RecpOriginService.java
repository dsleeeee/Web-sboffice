package kr.co.solbipos.base.prod.recpOrigin.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : RecpOriginService.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface RecpOriginService {

    /** 원산지관리 조회 */
    List<DefaultMap<Object>> getRecpOriginList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 원산지관리 저장 */
    int getRecpOriginSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);

    /** 재료-상품 조회 */
    List<DefaultMap<Object>> getRecpOriginDetailList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료-상품 등록 팝업 - 상품조회 */
    List<DefaultMap<Object>> getRecpProdList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO);

    /** 재료-상품 저장 */
    int getRecpOriginDetailSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO);
}