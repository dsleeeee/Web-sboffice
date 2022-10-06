package kr.co.solbipos.sale.prod.periodProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.periodProd.service.PeriodProdService;
import kr.co.solbipos.sale.prod.periodProd.service.PeriodProdVO;
import kr.co.solbipos.sale.prod.periodProd.service.impl.PeriodProdMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PeriodProdServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 대비기간별 상품 매출
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
@Service("periodProdService")
@Transactional
public class PeriodProdServiceImpl implements PeriodProdService {
    private final PeriodProdMapper periodProdMapper;

    public PeriodProdServiceImpl(PeriodProdMapper periodProdMapper) {
        this.periodProdMapper = periodProdMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodProdList(PeriodProdVO periodProdVO, SessionInfoVO sessionInfoVO) {

        periodProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = periodProdVO.getStoreCds().split(",");
        periodProdVO.setStoreCdList(storeCds);

        return periodProdMapper.getPeriodProdList(periodProdVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodProdExcelList(PeriodProdVO periodProdVO, SessionInfoVO sessionInfoVO) {

        periodProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = periodProdVO.getStoreCds().split(",");
        periodProdVO.setStoreCdList(storeCds);

        return periodProdMapper.getPeriodProdExcelList(periodProdVO);
    }
}