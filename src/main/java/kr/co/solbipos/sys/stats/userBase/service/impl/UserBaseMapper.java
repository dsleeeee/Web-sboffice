package kr.co.solbipos.sys.stats.userBase.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.stats.userBase.service.UserBaseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : UserBaseMapper.java
 * @Description : 시스템관리 > 통계 > 사용자기준 사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface UserBaseMapper {

    /** 사용자기준 사용현황 조회 */
    List<DefaultMap<Object>> getUserBaseList(UserBaseVO userBaseVO);

    /** 사용자기준 사용현황 상세조회(사용자 탭) */
    List<DefaultMap<Object>> getUserList(UserBaseVO userBaseVO);

    /** 사용자기준 사용현황 상세조회(사용메뉴 탭) */
    List<DefaultMap<Object>> getUseMenuList(UserBaseVO userBaseVO);
}