package kr.co.solbipos.adi.resve.resveInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.resve.resveInfo.service.ResveInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ResveInfoMapper.java
 * @Description : 부가서비스 > 예약관리 > 예약현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface ResveInfoMapper {

    /** 예약현황 조회 */
    List<DefaultMap<String>> getResveList(ResveInfoVO resveInfoVO);

}