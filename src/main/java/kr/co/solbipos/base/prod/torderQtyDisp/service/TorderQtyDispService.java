package kr.co.solbipos.base.prod.torderQtyDisp.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TorderQtyDispService.java
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
public interface TorderQtyDispService {

    /** T오더수량변경표시관리 - 조회 */
    List<DefaultMap<Object>> getTorderQtyDispList(TorderQtyDispVO torderQtyDispVO, SessionInfoVO sessionInfoVO);

    /** T오더수량변경표시관리 - 저장 */
    int getTorderQtyDispSave(TorderQtyDispVO[] torderQtyDispVOs, SessionInfoVO sessionInfoVO);
}