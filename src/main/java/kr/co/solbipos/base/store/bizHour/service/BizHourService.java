package kr.co.solbipos.base.store.bizHour.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BizHourService.java
 * @Description : 기초관리 > 매장관리 > 매장영업시간관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.01  김유승      최초생성
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BizHourService {

    /** 요일별 리스트 조회 */
    List<DefaultMap<String>> getDaysList(BizHourVO bizHourVO);

    /** 요일별 신규 등록 */
    int saveNewDays(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 요일별 수정 */
    int saveDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);

    /** 요일별 삭제 */
    int deleteDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);

    /** 특정일 리스트 조회 */
    List<DefaultMap<String>> getSpecificList(BizHourVO bizHourVO);

    /** 특정일 신규 등록 */
    int saveNewSpecific(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 특정일 수정 */
    int saveSpecific(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);

    /** 특정일 삭제 */
    int deleteSpecific(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);
}
