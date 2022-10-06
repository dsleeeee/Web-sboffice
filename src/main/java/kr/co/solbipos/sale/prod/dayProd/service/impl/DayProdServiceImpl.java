package kr.co.solbipos.sale.prod.dayProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayProdServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 일별 상품 매출 현황
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
@Service("dayProdService")
@Transactional
public class DayProdServiceImpl implements DayProdService {
    private final DayProdMapper dayProdMapper;

    public DayProdServiceImpl(DayProdMapper dayProdMapper) {
        this.dayProdMapper = dayProdMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdList(DayProdVO dayProdVO, SessionInfoVO sessionInfoVO) {

        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdVO.getStoreCds().split(",");
        dayProdVO.setStoreCdList(storeCds);

        return dayProdMapper.getDayProdList(dayProdVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdExcelList(DayProdVO dayProdVO, SessionInfoVO sessionInfoVO) {

        dayProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayProdVO.getStoreCds().split(",");
        dayProdVO.setStoreCdList(storeCds);

        return dayProdMapper.getDayProdExcelList(dayProdVO);
    }
}