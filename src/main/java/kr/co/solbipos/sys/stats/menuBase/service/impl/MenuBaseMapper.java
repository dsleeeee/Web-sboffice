package kr.co.solbipos.sys.stats.menuBase.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.stats.menuBase.service.MenuBaseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MenuBaseMapper.java
 * @Description : 시스템관리 > 통계 > 메뉴기준 사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MenuBaseMapper {

    /** 메뉴기준 사용현황 조회 */
    List<DefaultMap<Object>> getMenuBaseList(MenuBaseVO menuBaseVO);

    /** 메뉴기준 사용현황 상세조회 */
    List<DefaultMap<Object>> getMenuBaseDetailList(MenuBaseVO menuBaseVO);
}