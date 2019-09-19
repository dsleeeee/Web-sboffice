package kr.co.solbipos.membr.anals.membrPossesn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.membrPossesn.service.MembrPossesnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MembrPossesnMapper.java
 * @Description : 회원관리 > 회원분석 > 회원매출점유
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MembrPossesnMapper {

    /** 회원매출점유*/
    List<DefaultMap<Object>> getMembrPossesnList(MembrPossesnVO membrPossesnVO);
}
