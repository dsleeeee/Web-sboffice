package kr.co.solbipos.sys.stats.webLogin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : WebLoginMapper.java
 * @Description : 시스템관리 > 통계 > 웹로그인 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.06.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface WebLoginMapper {

    /** 웹로그인 현황 조회 */
    List<DefaultMap<Object>> getWebLoginList(WebLoginVO webLoginVO);

    /** 일자별 현황 조회 */
    List<DefaultMap<Object>> getWebLoginDayDetailList(WebLoginVO webLoginVO);

    /** 로그인 현황 조회 */
    List<DefaultMap<Object>> getWebLoginLoginDetailList(WebLoginVO webLoginVO);
}