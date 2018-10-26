package kr.co.common.service.popup.impl;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.structure.DefaultMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PopupMapper.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PopupMapper {

    /** 벤사장 목록 조회 */
    List<DefaultMap<String>> getVanList(VanVO vanVO);

    /** 대리점 목록 조회 */
    List<DefaultMap<String>> getAgencyList(AgencyVO agencyVO);
}

