package kr.co.solbipos.sale.status.offAdd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.offAdd.service.OffAddVO;
import kr.co.solbipos.sale.status.side.service.SideVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OffAddMapper.java
 * @Description : 매출관리 > 매출현황2 > 오프라인추가매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.14  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface OffAddMapper {
    /** 일별 조회 */
    List<DefaultMap<String>> getOffAddDayList(OffAddVO offAddVO);

    /** 일별상세 조회 */
    List<DefaultMap<String>> getOffAddDayDetailList(OffAddVO offAddVO);

    /** 월별 조회 */
    List<DefaultMap<String>> getOffAddMonthList(OffAddVO offAddVO);

    /** 월별상세 조회 */
    List<DefaultMap<String>> getOffAddMonthDetailList(OffAddVO offAddVO);

    /** 상품별 조회 */
    List<DefaultMap<String>> getOffAddProdList(OffAddVO offAddVO);

    /** 상품별 엑셀 조회 */
    List<DefaultMap<String>> getOffAddProdExcelList(OffAddVO offAddVO);

}
