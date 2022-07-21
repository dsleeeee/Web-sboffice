package kr.co.solbipos.base.store.specificDayMemo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.specificDayMemo.service.SpecificDayMemoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SpecificDayMemoMapper.java
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
@Mapper
@Repository
public interface SpecificDayMemoMapper {

    /** 회원약관관리 - 조회 */
    List<DefaultMap<Object>> getSpecificDayMemoList(SpecificDayMemoVO specificDayMemoVO);

    /** 신규 등록 채번 */
    String getSpecificNo(SpecificDayMemoVO specificDayMemoVO);

    /** 신규 등록 */
    int getSpecificDayMemoRegist(SpecificDayMemoVO specificDayMemoVO);

    /** 저장 */
    int getSpecificDayMemoSave(SpecificDayMemoVO specificDayMemoVO);

    /** 삭제 */
    int getSpecificDayMemoDelete(SpecificDayMemoVO specificDayMemoVO);
}