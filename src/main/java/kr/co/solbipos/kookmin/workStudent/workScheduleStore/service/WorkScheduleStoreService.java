package kr.co.solbipos.kookmin.workStudent.workScheduleStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : WorkScheduleStoreService.java
 * @Description : 국민대 > 근로학생관리 > 매장별 근무테이블 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.09
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface WorkScheduleStoreService {

    /** 근무테이블 조회 */
    List<DefaultMap<String>> getWorkScheduleStoreList(WorkScheduleStoreVO workScheduleStoreVO, SessionInfoVO sessionInfoVO);

    /** 로우 추가 매장별 근무코드 조회 */
    List<DefaultMap<Object>> addRowWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO, SessionInfoVO sessionInfoVO);

    /** 근무테이블 저장 */
    int saveWorkScheduleStore(WorkScheduleStoreVO[] workScheduleStoreVOs, SessionInfoVO sessionInfoVO);

    /** 학기정보 조회 */
    List<DefaultMap<Object>> getTermInfoChk(WorkScheduleStoreVO workScheduleStoreVO, SessionInfoVO sessionInfoVO);
}
