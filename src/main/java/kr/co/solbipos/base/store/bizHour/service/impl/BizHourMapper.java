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
}
