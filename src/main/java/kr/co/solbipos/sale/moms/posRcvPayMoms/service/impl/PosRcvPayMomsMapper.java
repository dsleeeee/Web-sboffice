package kr.co.solbipos.sale.moms.posRcvPayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.moms.posRcvPayMoms.service.PosRcvPayMomsVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosRcvPayMomsMapper.java
 * @Description : 맘스터치 > 정산 > POS내역수신(결제)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosRcvPayMomsMapper {

    /** POS내역수신(결제) - 리스트 조회 */
    List<DefaultMap<Object>> getPosRcvPayMomsList(PosRcvPayMomsVO posRcvPayMomsVO);
}
