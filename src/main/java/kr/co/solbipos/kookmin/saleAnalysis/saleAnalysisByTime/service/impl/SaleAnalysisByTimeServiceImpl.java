package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service.SaleAnalysisByTimeService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service.SaleAnalysisByTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByTimeServiceImpl.java
 * @Description : 국민대 > 매출분석 > 시간대별 매출분석(요일별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("SaleAnalysisByTimeService")
@Transactional
public class SaleAnalysisByTimeServiceImpl implements SaleAnalysisByTimeService {

    private final SaleAnalysisByTimeMapper saleAnalysisByTimeMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SaleAnalysisByTimeServiceImpl(SaleAnalysisByTimeMapper saleAnalysisByTimeMapper, PopupMapper popupMapper) {
        this.saleAnalysisByTimeMapper = saleAnalysisByTimeMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대별 매출분석 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleAnalysisByTimeList(SaleAnalysisByTimeVO saleAnalysisByTimeVO, SessionInfoVO sessionInfoVO) {
        saleAnalysisByTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(saleAnalysisByTimeVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleAnalysisByTimeVO.getStoreCds(), 3900));
            saleAnalysisByTimeVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        saleAnalysisByTimeVO.setYoilList(saleAnalysisByTimeVO.getYoil().split(","));
        return saleAnalysisByTimeMapper.getSaleAnalysisByTimeList(saleAnalysisByTimeVO);
    }
}
