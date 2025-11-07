package kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : WorkScheduleStoreMapper.java
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
@Mapper
@Repository
public interface WorkScheduleStoreMapper {

    /** 근무테이블 조회 */
    List<DefaultMap<String>> getWorkScheduleStoreList(WorkScheduleStoreVO workScheduleStoreVO);

    /** 로우 추가 매장별 근무코드 조회  */
    List<DefaultMap<Object>> getWorkSchCodeList(WorkScheduleStoreVO workScheduleStoreVO);

    /** 근무코드 MAX값 추가 */
    int getMaxWowkSchCode(WorkScheduleStoreVO workScheduleStoreVO);

    /** 근무테이블 등록 */
    int insertWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO);
    /** 근무테이블 수정 */
    int updateWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO);
    /** 근무테이블 삭제 */
    int deleteWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO);

    /** 매장별 근무코드 조회 */
    String getWorkSchCodeStore(WorkScheduleStoreVO workScheduleStoreVO);

    /** 로우 추가 매장코드, 매장명 조회 */
    List<DefaultMap<Object>> getWorkScheduleStoreCdNmList(WorkScheduleStoreVO workScheduleStoreVO);

    List<DefaultMap<Object>> getTermInfoChk(WorkScheduleStoreVO workScheduleStoreVO);

    int getWorkSchCodeDupChk(WorkScheduleStoreVO workScheduleStoreVO);
}
