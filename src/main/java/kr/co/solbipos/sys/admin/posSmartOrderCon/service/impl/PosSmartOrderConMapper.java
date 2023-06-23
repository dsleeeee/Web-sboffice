package kr.co.solbipos.sys.admin.posSmartOrderCon.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.admin.posSmartOrderCon.service.PosSmartOrderConVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosSmartOrderConMapper.java
 * @Description : 시스템관리 > 관리자기능 > 스마트오더연결상태
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.20  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.06.20
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface PosSmartOrderConMapper {

    /** 매장코드 조회 */
    List<DefaultMap<String>> getPosSmartOrderConList(PosSmartOrderConVO posSmartOrderConVO);
}
