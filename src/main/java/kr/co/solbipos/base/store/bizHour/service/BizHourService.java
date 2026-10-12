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

    /** 요일별 리스트 조회 (피벗 : 매장×요일 매트릭스) */
    List<DefaultMap<String>> getDaysPivotList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 요일별 리스트 조회 (매장 : 요일별 한 줄씩, 일~토 기본 표시) */
    List<DefaultMap<String>> getDaysStoreList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 요일별 신규 등록 */
    int saveNewDays(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 요일별 수정 */
    int saveDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);

    /** 요일별 수정 (매장 : 행 단위 — 시간O+seqO=수정, 시간O+seqX=등록, 시간X+seqO=삭제) */
    int saveDaysStore(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);

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

    /** 영업시간 복사 (원본 매장 → 복사대상 매장들, 요일별+특정일, 타겟 삭제 후 교체) */
    int bizHourCopy(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 매장영업시간관리 사용여부 조회 (매장 자기 값) */
    String getStoreUseYn(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 매장영업시간관리 사용여부 매장목록 조회 (본사 팝업) */
    List<DefaultMap<String>> getStoreUseYnList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 매장영업시간관리 사용여부 저장 (매장 : 자기 매장) */
    int saveStoreUseYn(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO);

    /** 매장영업시간관리 사용여부 일괄 저장 (본사 팝업) */
    int saveStoreUseYnList(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO);
}
