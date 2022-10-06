package kr.co.solbipos.sale.prod.monthSalesVolume.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthSalesVolume.service.MonthSalesVolumeService;
import kr.co.solbipos.sale.prod.monthSalesVolume.service.MonthSalesVolumeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthSalesVolumeServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 월별 상품 판매량
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthSalesVolumeService")
@Transactional
public class MonthSalesVolumeServiceImpl implements MonthSalesVolumeService {
    private final MonthSalesVolumeMapper monthSalesVolumeMapper;

    public MonthSalesVolumeServiceImpl(MonthSalesVolumeMapper monthSalesVolumeMapper) {
        this.monthSalesVolumeMapper = monthSalesVolumeMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSalesVolumeList(MonthSalesVolumeVO monthSalesVolumeVO, SessionInfoVO sessionInfoVO) {

        monthSalesVolumeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthSalesVolumeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthSalesVolumeVO.getStoreCds().split(",");
        monthSalesVolumeVO.setStoreCdList(storeCds);

        return monthSalesVolumeMapper.getMonthSalesVolumeList(monthSalesVolumeVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSalesVolumeExcelList(MonthSalesVolumeVO monthSalesVolumeVO, SessionInfoVO sessionInfoVO) {

        monthSalesVolumeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthSalesVolumeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthSalesVolumeVO.getStoreCds().split(",");
        monthSalesVolumeVO.setStoreCdList(storeCds);

        return monthSalesVolumeMapper.getMonthSalesVolumeExcelList(monthSalesVolumeVO);
    }
}