package kr.co.solbipos.sale.status.giftCalc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.giftCalc.service.GiftCalcVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : GiftCalcMapper.java
 * @Description : 맘스터치 > 매출분석2 > 지류상품권 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.08.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.08.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface GiftCalcMapper {

    /** 지류상품권 정산 - 조회 */
    List<DefaultMap<Object>> getGiftCalcList(GiftCalcVO giftCalcVO);

    /** 지류상품권 정산황 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getGiftCalcExcelList(GiftCalcVO giftCalcVO);

    /** 지류상품권 정산 상세 팝업 - 조회 */
    List<DefaultMap<Object>> getGiftCalcDtlList(GiftCalcVO giftCalcVO);
}