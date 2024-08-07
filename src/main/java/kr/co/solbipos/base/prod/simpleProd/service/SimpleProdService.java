package kr.co.solbipos.base.prod.simpleProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SimpleProdService.java
 * @Description : 기초관리 > 상품관리 > 간편상품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.26  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SimpleProdService {

    /** 거래처 콤보 조회 */
    List<DefaultMap<String>> vendrComboList(SessionInfoVO sessionInfoVO);

    /** 검증결과 전체 삭제 */
    int getSimpleProdCheckDeleteAll(SimpleProdVO simpleProdVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getSimpleProdCheckSave(SimpleProdVO[] simpleProdVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<Object>> getSimpleProdList(SimpleProdVO simpleProdVO, SessionInfoVO sessionInfoVO);

    /** 상품 저장 */
    int getSimpleProdSave(SimpleProdVO[] simpleProdVOs, SessionInfoVO sessionInfoVO);
}