package kr.co.solbipos.base.store.bizHour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.bizHour.service.BizHourVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BizHourMapper.java
 * @Description : 기초관리 > 매장관리 > 매장영업시간관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.01  김유승      최초생성
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BizHourMapper {

    /** 요일별 리스트 조회 */
    List<DefaultMap<String>> getDaysList(BizHourVO bizHourVO);

    /** 요일별 리스트 조회 (피벗 : 매장×요일 매트릭스) */
    List<DefaultMap<String>> getDaysPivotList(BizHourVO bizHourVO);

    /** 요일별 리스트 조회 (매장 : 요일별 한 줄씩, 일~토 기본 표시) */
    List<DefaultMap<String>> getDaysStoreList(BizHourVO bizHourVO);

    /** 요일별 시간중복 건수 조회 */
    int getDaysOverlapCnt(BizHourVO bizHourVO);

    /** 요일별 순번 채번 */
    int getDaysMaxSeq(BizHourVO bizHourVO);

    /** 요일별 등록 */
    int insertDays(BizHourVO bizHourVO);

    /** 요일별 수정 */
    int updateDays(BizHourVO bizHourVO);

    /** 요일별 삭제 */
    int deleteDays(BizHourVO bizHourVO);

    /** 요일별 매장단위 전체삭제 (매트릭스 저장용) */
    int deleteDaysByStore(BizHourVO bizHourVO);

    /** 특정일 리스트 조회 */
    List<DefaultMap<String>> getSpecificList(BizHourVO bizHourVO);

    /** 특정일 시간중복 건수 조회 */
    int getSpecificOverlapCnt(BizHourVO bizHourVO);

    /** 특정일 순번 채번 */
    int getSpecificMaxSeq(BizHourVO bizHourVO);

    /** 특정일 등록 */
    int insertSpecific(BizHourVO bizHourVO);

    /** 특정일 수정 */
    int updateSpecific(BizHourVO bizHourVO);

    /** 특정일 삭제 */
    int deleteSpecific(BizHourVO bizHourVO);

    /** [복사] 요일별 : 타겟 매장 전체삭제 */
    int deleteDaysByCopyStore(BizHourVO bizHourVO);

    /** [복사] 요일별 : 원본→타겟 복사 */
    int copyDaysToStore(BizHourVO bizHourVO);

    /** [복사] 특정일 : 타겟 매장 전체삭제 */
    int deleteSpecificByCopyStore(BizHourVO bizHourVO);

    /** [복사] 특정일 : 원본→타겟 복사 */
    int copySpecificToStore(BizHourVO bizHourVO);

    /** 매장영업시간관리 사용여부 조회 (매장 자기 값) */
    String getStoreUseYn(BizHourVO bizHourVO);

    /** 매장영업시간관리 사용여부 매장목록 조회 (본사 팝업) */
    List<DefaultMap<String>> getStoreUseYnList(BizHourVO bizHourVO);

    /** 매장영업시간관리 사용여부 저장 (MERGE) */
    int saveStoreUseYn(BizHourVO bizHourVO);
}
