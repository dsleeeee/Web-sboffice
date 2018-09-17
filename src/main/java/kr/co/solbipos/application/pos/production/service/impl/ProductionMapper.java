package kr.co.solbipos.application.pos.production.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.pos.exceptForward.service.ExcpForwardProductVO;
import kr.co.solbipos.application.pos.production.service.ProductionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProductionMapper.java
 * @Description : POS 화면에서 생산량 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.16  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProductionMapper {

    /** 생산량 등록 대상상품 목록 조회 */
    List<DefaultMap<String>> getProductList(ProductionVO productionVO);
}
