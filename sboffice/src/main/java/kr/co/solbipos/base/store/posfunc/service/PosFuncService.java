package kr.co.solbipos.base.store.posfunc.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : PosFuncService.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PosFuncService {

    /** 포스목록 조회 */
    List<DefaultMap<String>> getPosList(PosFuncVO posFuncVO);

    /** 포스기능목록 조회 */
    List<DefaultMap<String>> getPosFuncList(PosFuncVO posFuncVO);
    
    /** 포스기능 상세조회 */
    List<DefaultMap<String>> getPosConfDetail(PosFuncVO posFuncVO);

    /** 포스기능 상세저장 */
    int savePosConf(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO);

}
