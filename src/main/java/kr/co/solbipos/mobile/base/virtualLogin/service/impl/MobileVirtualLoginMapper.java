package kr.co.solbipos.mobile.base.virtualLogin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.base.virtualLogin.service.MobileVirtualLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginMapper.java
 * @Description : 모바일 > 기초관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileVirtualLoginMapper {

    /** (모바일)가상로그인 - 조회 */
    List<DefaultMap<String>> getMobileVirtualLoginList(MobileVirtualLoginVO mobileVirtualLoginVO);

    /** 가상로그인 권한 조회 */
    Integer checkMobileVirtualLoginAuth(String userId);

    /** 가상로그인 권한 조회 */
    Integer checkMobileVirtualLoginAuthCheck(MobileVirtualLoginVO mobileVirtualLoginVO);
}
