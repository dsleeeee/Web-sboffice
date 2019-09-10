package kr.co.solbipos.membr.anals.periodMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.periodMembr.service.PeriodMembrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PeriodMembrMapper.java
 * @Description : 회원관리 > 회원분석 > 기간회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PeriodMembrMapper {

    /** 기간회원 구매내역 구매내역*/
    List<DefaultMap<Object>> getPeriodMembrList(PeriodMembrVO periodMembrVO);
}