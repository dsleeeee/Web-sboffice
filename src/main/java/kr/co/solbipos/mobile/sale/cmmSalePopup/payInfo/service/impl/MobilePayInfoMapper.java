package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobilePayInfoMapper.java
 * @Description : (모바일) 공통 결제수단 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobilePayInfoMapper {

    /** 가승인 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO);
}