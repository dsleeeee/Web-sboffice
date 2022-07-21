package kr.co.solbipos.base.store.specificDayMemo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SpecificDayMemoService.java
 * @Description : 기초관리 > 매장관리 > 이벤트등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SpecificDayMemoService {

    /** 이벤트등록 - 조회 */
    List<DefaultMap<Object>> getSpecificDayMemoList(SpecificDayMemoVO specificDayMemoVO, SessionInfoVO sessionInfoVO);

    /** 신규 등록 */
    int getSpecificDayMemoRegist(SpecificDayMemoVO specificDayMemoVO, SessionInfoVO sessionInfoVO);
    
    /** 저장 */
    int getSpecificDayMemoSave(SpecificDayMemoVO[] specificDayMemoVOs, SessionInfoVO sessionInfoVO);

    /** 삭제 */
    int getSpecificDayMemoDelete(SpecificDayMemoVO[] specificDayMemoVOs, SessionInfoVO sessionInfoVO);
}