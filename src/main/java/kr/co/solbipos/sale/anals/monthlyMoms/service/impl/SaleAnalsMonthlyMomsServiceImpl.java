package kr.co.solbipos.sale.anals.monthlyMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.monthlyMoms.service.SaleAnalsMonthlyMomsService;
import kr.co.solbipos.sale.anals.monthlyMoms.service.SaleAnalsMonthlyMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SaleAnalsMonthlyMomsService")
public class SaleAnalsMonthlyMomsServiceImpl implements SaleAnalsMonthlyMomsService {

    private final SaleAnalsMonthlyMomsMapper saleAnalsMonthlyMomsMapper;
    private final MessageService messageService;

    @Autowired
    public SaleAnalsMonthlyMomsServiceImpl(SaleAnalsMonthlyMomsMapper saleAnalsMonthlyMomsMapper, MessageService messageService) {
        this.saleAnalsMonthlyMomsMapper = saleAnalsMonthlyMomsMapper;
        this.messageService = messageService;
    }

    /** 월력판매분석 - 월력판매분석 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleAnalsMonthlyMomsList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO) {

        // 매장브랜드 '전체' 일때
        if (saleAnalsMonthlyMomsVO.getStoreHqBrandCd() == "" || saleAnalsMonthlyMomsVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = saleAnalsMonthlyMomsVO.getUserBrands().split(",");
            saleAnalsMonthlyMomsVO.setUserBrandList(userBrandList);
        }

        return saleAnalsMonthlyMomsMapper.getSaleAnalsMonthlyMomsList(saleAnalsMonthlyMomsVO);
    }

    /** 월력판매분석 - 결제수단별 팝업 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO) {
        return saleAnalsMonthlyMomsMapper.getSaleAnalsMonthlyMomsStoreList(saleAnalsMonthlyMomsVO);
	}

    @Override
    public List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreDtlList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO) {
        return saleAnalsMonthlyMomsMapper.getSaleAnalsMonthlyMomsStoreDtlList(saleAnalsMonthlyMomsVO);
    }
}
