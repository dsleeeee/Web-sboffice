package kr.co.solbipos.base.price.storeSalePrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreSalePriceMapper.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreSalePriceMapper {

    /** 상품 정보 조회 */
    List<DefaultMap<String>>  getSalePriceList(StoreSalePriceVO storeSalePriceVO);
    
    /** 엑셀다운로드 */
    List<DefaultMap<String>>  getSalePriceExcelList(StoreSalePriceVO storeSalePriceVO);

}
