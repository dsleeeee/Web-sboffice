package kr.co.solbipos.sale.anals.monthly.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.monthly.service.SaleAnalsMonthlyService;
import kr.co.solbipos.sale.anals.monthly.service.SaleAnalsMonthlyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SaleAnalsMonthlyService")
public class SaleAnalsMonthlyServiceImpl implements SaleAnalsMonthlyService {
    private final SaleAnalsMonthlyMapper SaleAnalsMonthlyMapper;
    private final MessageService messageService;

    @Autowired
    public SaleAnalsMonthlyServiceImpl(SaleAnalsMonthlyMapper SaleAnalsMonthlyMapper, MessageService messageService) {
        this.SaleAnalsMonthlyMapper = SaleAnalsMonthlyMapper;
        this.messageService = messageService;
    }

    /** 월력판매분석 - 월력판매분석 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleAnalsMonthlyList(SaleAnalsMonthlyVO SaleAnalsMonthlyVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        result = SaleAnalsMonthlyMapper.getStSaleAnalsMonthlyList(SaleAnalsMonthlyVO);

        return result;
    }

    /** 월력판매분석 - 결제수단별 팝업 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getSaleAnalsMonthlyPopupList(SaleAnalsMonthlyVO SaleAnalsMonthlyVO, SessionInfoVO sessionInfoVO) {
        SaleAnalsMonthlyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        SaleAnalsMonthlyVO.setEmpNo(sessionInfoVO.getEmpNo());
		List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        result = SaleAnalsMonthlyMapper.getSaleAnalsMonthlyPopupList(SaleAnalsMonthlyVO);

        return result;
	}
}
