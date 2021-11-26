package kr.co.solbipos.mobile.application.direct.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.application.direct.service.MobileDirectVO;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileDaySaleMapper.java
 * @Description : (모바일) QR > 원하는 페이지 오픈
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileDirectMapper {

    String getAccess(MobileDirectVO mobileDirectVO);

}