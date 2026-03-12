package kr.co.solbipos.sys.link.naverPlaceStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.link.naverPlaceStatus.service.NaverPlaceStatusVO;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : NaverPlaceStatusMapper.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface NaverPlaceStatusMapper {

    /** 사용자현황 조회 */
    List<DefaultMap<Object>> getUserStatusList(NaverPlaceStatusVO naverPlaceStatusVO);

    /** 접속현황 조회 */
    List<DefaultMap<Object>> getConnectStatusList(NaverPlaceStatusVO naverPlaceStatusVO);

    /** 메뉴 리소스코드 조회*/
    String getMenuResrceCd(NaverPlaceStatusVO naverPlaceStatusVO);
}
