package kr.co.solbipos.sale.marketing.saleByChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.marketing.saleByChannel.service.SaleByChannelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : SaleByChannelMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 채널별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleByChannelMapper {

    /** 채널별매출 - 조회 */
    List<DefaultMap<Object>> getSaleByChannelList(SaleByChannelVO saleByChannelVO);

    /** 그리드 컬럼 조회 */
    List<DefaultMap<String>> getDlvrColList(SaleByChannelVO saleByChannelVO);
}
