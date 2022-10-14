package kr.co.solbipos.sale.pay.payDayDevice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.pay.payDayDevice.service.PayDayDeviceService;
import kr.co.solbipos.sale.pay.payDayDevice.service.PayDayDeviceVO;
import kr.co.solbipos.sale.pay.payDayDevice.service.impl.PayDayDeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayDayDeviceServiceImpl.java
 * @Description : 맘스터치 > 결제수단별 매출 > 일별 결제수단 매출(기기별)
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
@Service("payDayDeviceService")
@Transactional
public class PayDayDeviceServiceImpl implements PayDayDeviceService {
    private final PayDayDeviceMapper payDayDeviceMapper;

    public PayDayDeviceServiceImpl(PayDayDeviceMapper payDayDeviceMapper) {
        this.payDayDeviceMapper = payDayDeviceMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayDayDeviceList(PayDayDeviceVO payDayDeviceVO, SessionInfoVO sessionInfoVO) {
        payDayDeviceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payDayDeviceVO.setEmpNo(sessionInfoVO.getEmpNo());
        payDayDeviceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(payDayDeviceVO.getStoreCd()).equals("")) {
            payDayDeviceVO.setArrStoreCd(payDayDeviceVO.getStoreCd().split(","));
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payDayDeviceVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        payDayDeviceVO.setPivotPayCol(pivotPayCol);
        payDayDeviceVO.setArrPayCol(payCol.split(","));

        return payDayDeviceMapper.getPayDayDeviceList(payDayDeviceVO);
    }

}