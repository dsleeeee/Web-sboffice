package kr.co.solbipos.mobile.base.virtualLoginById.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.base.virtualLoginById.service.MobileVirtualLoginByIdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginByIdMapper.java
 * @Description : 기초관리_모바일 > 가상로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileVirtualLoginByIdMapper {

    /** 가상로그인(아이디별) - 조회 */
    List<DefaultMap<String>> getMobileVirtualLoginByIdList(MobileVirtualLoginByIdVO mobileVirtualLoginByIdVO);
}
