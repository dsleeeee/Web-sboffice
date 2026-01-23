package kr.co.solbipos.sys.link.orderkitStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderkitStatusMapper.java
 * @Description : 시스템관리 > 연동 > 오더킷현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.21  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface OrderkitStatusMapper {

    /** 사용자현황 조회 */
    List<DefaultMap<Object>> getUserStatusList(OrderkitStatusVO orderkitStatusVO);

    /** 접속현황 조회 */
    List<DefaultMap<Object>> getConnectStatusList(OrderkitStatusVO orderkitStatusVO);

    /** 메뉴 리소스코드 조회*/
    String getMenuResrceCd(OrderkitStatusVO orderkitStatusVO);

    /** 연동구분(OMS, QR, NAVER)별 연동 상태 저장 */
    int insertAgencyStatus(OrderkitStatusVO orderkitStatusVO);
}
