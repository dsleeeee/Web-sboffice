package kr.co.solbipos.membr.anals.membrPoint.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MembrPointMapper.java
 * @Description : 회원관리 > 회원분석 > 회원 포인트실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MembrPointMapper {

    /** 회원 포인트실적 */
    List<DefaultMap<Object>> getMembrPointList(MembrPointVO membrPointVO);
}