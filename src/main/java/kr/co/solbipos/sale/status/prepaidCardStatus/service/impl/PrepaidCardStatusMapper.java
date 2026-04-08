package kr.co.solbipos.sale.status.prepaidCardStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prepaidCardStatus.service.PrepaidCardStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PrepaidCardStatusMapper.java
 * @Description :  맘스터치 > 매출분석2 > 선불카드현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.02  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.04.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface PrepaidCardStatusMapper {

    /** 선불카드 충전 현황 - 조회 */
    List<DefaultMap<Object>> getPrepaidCardChargeStatus(PrepaidCardStatusVO prepaidCardStatusVO);

    /** 선불카드 사용 현황 - 조회 */
    List<DefaultMap<Object>> getPrepaidCardUseStatus(PrepaidCardStatusVO prepaidCardStatusVO);

    /** 선불카드 충전 현황 - 상세 조회 */
    List<DefaultMap<Object>> getPrepaidCardChargeStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO);

    /** 선불카드 사용 현황 - 상세 조회 */
    List<DefaultMap<Object>> getPrepaidCardUseStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO);

}
