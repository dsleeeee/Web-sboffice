package kr.co.solbipos.base.prod.purchaser.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.purchaser.service.PurchaserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PurchaserMapper.java
 * @Description : 국민대 > 매입처관리 > 매입처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PurchaserMapper {

    /** 매입처조회 - 조회 */
    List<DefaultMap<Object>> getPurchaserList(PurchaserVO purchaserVO);
}