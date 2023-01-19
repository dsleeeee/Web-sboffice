package kr.co.solbipos.sale.prod.uptPmix.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.uptPmix.service.UptPmixVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : UptPmixMapper.java
 * @Description : 맘스터치 > 상품매출분석 > UPT & P.mix
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.17   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface UptPmixMapper {

    /** UPT & P.mix 리스트 조회 */
    List<DefaultMap<Object>> getUptPmixList(UptPmixVO uptPmixVO);

    /** UPT & P.mix 엑셀 다운로드 리스트 조회 */
    List<DefaultMap<Object>> getUptPmixExcelList(UptPmixVO uptPmixVO);
}
