package kr.co.solbipos.membr.anals.membrNonBilClct.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.membrNonBilClct.service.MembrNonBilClctVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MembrNonBilClctMapper.java
 * @Description : 회원관리 > 회원분석 > 회원 미수금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MembrNonBilClctMapper {

    /** 회원 미수금현황 */
    List<DefaultMap<Object>> getMembrNonBilClctList(MembrNonBilClctVO membrNonBilClctVO);

    /** 회원 미수금현황 상세조회 */
    List<DefaultMap<Object>> getMembrNonBilClctDetailList(MembrNonBilClctVO membrNonBilClctVO);
}