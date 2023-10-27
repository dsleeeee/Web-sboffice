package kr.co.solbipos.sale.anals.versusPeriod.week.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.versusPeriod.week.service.VersusPeriodWeekService;
import kr.co.solbipos.sale.anals.versusPeriod.week.service.VersusPeriodWeekVO;

@Service("VersusPeriodWeekService")
public class VersusPeriodWeekServiceImpl implements VersusPeriodWeekService {
    private final VersusPeriodWeekMapper versusPeriodWeekMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    public VersusPeriodWeekServiceImpl(VersusPeriodWeekMapper versusPeriodWeekMapper, PopupMapper popupMapper, MessageService messageService) {
		super();
		this.versusPeriodWeekMapper = versusPeriodWeekMapper;
		this.popupMapper = popupMapper;
		this.messageService = messageService;
	}

	/** 상품별 매출 -별 상품 일자 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodWeekList(VersusPeriodWeekVO versusPeriodWeekVO, SessionInfoVO sessionInfoVO) {

        versusPeriodWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        versusPeriodWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        versusPeriodWeekVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(versusPeriodWeekVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(versusPeriodWeekVO.getStoreCd(), 3900));
            versusPeriodWeekVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return versusPeriodWeekMapper.getVersusPeriodWeekList(versusPeriodWeekVO);
    }
    
	/** 상품별 매출 - 차트 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodWeekChartList(VersusPeriodWeekVO versusPeriodWeekVO, SessionInfoVO sessionInfoVO) {

        versusPeriodWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        versusPeriodWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        versusPeriodWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
        if(!StringUtil.getOrBlank(versusPeriodWeekVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(versusPeriodWeekVO.getStoreCd(), 3900));
            versusPeriodWeekVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

    	
        return versusPeriodWeekMapper.getVersusPeriodWeekChartList(versusPeriodWeekVO);
    }
}
