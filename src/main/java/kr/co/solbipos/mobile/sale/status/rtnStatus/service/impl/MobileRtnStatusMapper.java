package kr.co.solbipos.mobile.sale.status.rtnStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.rtnStatus.service.MobileRtnStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileRtnStatusMapper.java
 * @Description : (모바일) 매출현황 > 시간대별(일자별)매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileRtnStatusMapper {

    /** 반품현황 - 조회 */
    List<DefaultMap<Object>> getMobileRtnStatusList(MobileRtnStatusVO mobileRtnSaleVO);

    /** 반품현황 상세 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileRtnStatusDtlList(MobileRtnStatusVO mobileRtnSaleVO);
}