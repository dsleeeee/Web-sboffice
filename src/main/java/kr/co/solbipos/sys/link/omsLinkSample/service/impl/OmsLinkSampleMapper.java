package kr.co.solbipos.sys.link.omsLinkSample.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OmsLinkSampleMapper.java
 * @Description : 시스템관리 > 연동 > OMS연동샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

@Mapper
@Repository
public interface OmsLinkSampleMapper {

    /** OMS연동샘플 API 호출 목록 조회 */
    List<DefaultMap<Object>> getOmsLinkSampleReqList(ApiLinkVO apiLinkVO);

    /** API 호출 로그 저장 */
    int saveApiLog(ApiLinkVO apiLinkVO);
}
