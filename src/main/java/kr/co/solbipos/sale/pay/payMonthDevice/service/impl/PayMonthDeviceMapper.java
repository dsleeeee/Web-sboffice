package kr.co.solbipos.sale.pay.payMonthDevice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.pay.payMonthDevice.service.PayMonthDeviceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PayMonthDeviceMapper.java
 * @Description : 맘스터치 > 결제수단별 매출 > 월별 결제수단 매출(기기별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PayMonthDeviceMapper {

    /** 조회 */
    List<DefaultMap<Object>> getPayMonthDeviceList(PayMonthDeviceVO payMonthDeviceVO);
}