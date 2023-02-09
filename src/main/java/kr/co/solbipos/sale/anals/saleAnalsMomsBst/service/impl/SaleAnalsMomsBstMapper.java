package kr.co.solbipos.sale.anals.saleAnalsMomsBst.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.saleAnalsMomsBst.service.SaleAnalsMomsBstVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleAnalsMomsBstMapper.java
 * @Description : 맘스터치 > 매출분석 > 매출분석(사업전략팀)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.27   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleAnalsMomsBstMapper {

    /** 상품별시간대매출(채널별) 조회 */
    List<DefaultMap<String>> getSaleAnalsMomsBstList(SaleAnalsMomsBstVO saleAnalsMomsBstVO);

    /** 해당 날짜의 총매출액 조회 */
    int getRealSaleAmt(SaleAnalsMomsBstVO saleAnalsMomsBstVO);
}
