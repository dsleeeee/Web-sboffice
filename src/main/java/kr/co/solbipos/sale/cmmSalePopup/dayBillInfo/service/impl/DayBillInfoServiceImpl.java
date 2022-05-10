package kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.DayBillInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service.DayBillInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayBillInfoServiceImpl.java
 * @Description : 매출 공통팝업 > 매장별 영수건수 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dayBillInfoService")
@Transactional
public class DayBillInfoServiceImpl implements DayBillInfoService {
    private final DayBillInfoMapper dayBillInfoMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayBillInfoServiceImpl(DayBillInfoMapper dayBillInfoMapper) {
        this.dayBillInfoMapper = dayBillInfoMapper;
    }

    /** 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDayStoreBillList(DayBillInfoVO dayBillInfoVO, SessionInfoVO sessionInfoVO) {

        dayBillInfoVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        dayBillInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayBillInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayBillInfoVO.setEmpNo(sessionInfoVO.getEmpNo());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayBillInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayBillInfoVO.getStoreCds().split(",");
        dayBillInfoVO.setStoreCdList(storeCds);

        return dayBillInfoMapper.getDayStoreBillList(dayBillInfoVO);
    }

    /** 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDayStoreBillList2(DayBillInfoVO dayBillInfoVO, SessionInfoVO sessionInfoVO) {

        dayBillInfoVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayBillInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayBillInfoVO.getStoreCds().split(",");
        dayBillInfoVO.setStoreCdList(storeCds);

        return dayBillInfoMapper.getDayStoreBillList2(dayBillInfoVO);
    }
}