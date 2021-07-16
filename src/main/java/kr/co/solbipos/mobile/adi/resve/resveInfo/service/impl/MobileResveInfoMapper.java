package kr.co.solbipos.mobile.adi.resve.resveInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.adi.resve.resveInfo.service.MobileResveInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileResveInfoMapper.java
 * @Description : (모바일) 부가서비스 > 예약현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MobileResveInfoMapper {

    /** 예약현황 조회 */
    List<DefaultMap<String>> getResveList(MobileResveInfoVO resveInfoVO);

}