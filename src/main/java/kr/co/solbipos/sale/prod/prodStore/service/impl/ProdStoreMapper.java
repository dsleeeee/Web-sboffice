package kr.co.solbipos.sale.prod.prodStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdStoreMapper.java
 * @Description : 맘스터치 > 승인관리2 > 상품별 점포매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdStoreMapper {

    /** 조회 */
    List<DefaultMap<Object>> getProdStoreList(ProdStoreVO prodStoreVO);
    List<DefaultMap<Object>> getProdStoreExcelList(ProdStoreVO prodStoreVO);
}