package kr.co.solbipos.sale.period.comparePeriodMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.period.comparePeriodMoms.service.ComparePeriodMomsService;
import kr.co.solbipos.sale.period.comparePeriodMoms.service.ComparePeriodMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ComparePeriodMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.06   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("comparePeriodMomsService")
@Transactional
public class ComparePeriodMomsServiceImpl implements ComparePeriodMomsService {

    private final ComparePeriodMomsMapper comparePeriodMomsMapper;

    public ComparePeriodMomsServiceImpl(ComparePeriodMomsMapper comparePeriodMomsMapper) {
        this.comparePeriodMomsMapper = comparePeriodMomsMapper;
    }

    /** 대비기간별 매출 조회 */
    @Override
    public List<DefaultMap<String>> getComparePeriodList(ComparePeriodMomsVO comparePeriodMomsVO, SessionInfoVO sessionInfoVO){
        comparePeriodMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        comparePeriodMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            comparePeriodMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = comparePeriodMomsVO.getStoreCds().split(",");
        comparePeriodMomsVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (comparePeriodMomsVO.getStoreHqBrandCd() == "" || comparePeriodMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = comparePeriodMomsVO.getUserBrands().split(",");
                comparePeriodMomsVO.setUserBrandList(userBrandList);
            }
        }

        return comparePeriodMomsMapper.getComparePeriodList(comparePeriodMomsVO);
    }
}
