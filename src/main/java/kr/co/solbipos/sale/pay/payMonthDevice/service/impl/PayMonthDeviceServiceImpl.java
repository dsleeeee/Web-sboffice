package kr.co.solbipos.sale.pay.payMonthDevice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.payMonthDevice.service.PayMonthDeviceService;
import kr.co.solbipos.sale.pay.payMonthDevice.service.PayMonthDeviceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayMonthDeviceServiceImpl.java
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
@Service("payMonthDeviceService")
@Transactional
public class PayMonthDeviceServiceImpl implements PayMonthDeviceService {
    private final PayMonthDeviceMapper payMonthDeviceMapper;

    public PayMonthDeviceServiceImpl(PayMonthDeviceMapper payMonthDeviceMapper) {
        this.payMonthDeviceMapper = payMonthDeviceMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayMonthDeviceList(PayMonthDeviceVO payMonthDeviceVO, SessionInfoVO sessionInfoVO) {

        payMonthDeviceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payMonthDeviceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payMonthDeviceVO.setEmpNo(sessionInfoVO.getEmpNo());

        payMonthDeviceVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payMonthDeviceVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = payMonthDeviceVO.getStoreCds().split(",");
        payMonthDeviceVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        payMonthDeviceVO.setArrPayCol(payMonthDeviceVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payMonthDeviceVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        payMonthDeviceVO.setPivotPayCol(pivotPayCol);

        return payMonthDeviceMapper.getPayMonthDeviceList(payMonthDeviceVO);
    }

}