package kr.co.solbipos.store.manage.posHwInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.posHwInfo.service.PosHwInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosHwInfoMapper.java
 * @Description : 기초관리 > 매장정보관리 > 포스 H/W정보 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosHwInfoMapper {

    /** 가상로그인 목록 조회 */
    List<DefaultMap<String>> getPosHwInfo(PosHwInfoVO posHwInfoVO);

}
