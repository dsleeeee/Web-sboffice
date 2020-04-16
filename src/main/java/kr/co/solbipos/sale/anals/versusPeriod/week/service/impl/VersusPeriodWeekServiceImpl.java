package kr.co.solbipos.sale.anals.versusPeriod.week.service.impl;

import java.util.List;

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
    private final MessageService messageService;

    public VersusPeriodWeekServiceImpl(VersusPeriodWeekMapper versusPeriodWeekMapper, MessageService messageService) {
		super();
		this.versusPeriodWeekMapper = versusPeriodWeekMapper;
		this.messageService = messageService;
	}

	/** 상품별 매출 - 일자별 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodWeekList(VersusPeriodWeekVO versusPeriodWeekVO, SessionInfoVO sessionInfoVO) {
  
    	versusPeriodWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if(!StringUtil.getOrBlank(versusPeriodWeekVO.getStoreCd()).equals("")) {
        	versusPeriodWeekVO.setArrStoreCd(versusPeriodWeekVO.getStoreCd().split(","));
        }
    	
        return versusPeriodWeekMapper.getVersusPeriodWeekList(versusPeriodWeekVO);
    }
    
	/** 상품별 매출 - 차트 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodWeekChartList(VersusPeriodWeekVO versusPeriodWeekVO, SessionInfoVO sessionInfoVO) {
  
    	versusPeriodWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if(!StringUtil.getOrBlank(versusPeriodWeekVO.getStoreCd()).equals("")) {
        	versusPeriodWeekVO.setArrStoreCd(versusPeriodWeekVO.getStoreCd().split(","));
        }
    	
        return versusPeriodWeekMapper.getVersusPeriodWeekChartList(versusPeriodWeekVO);
    }
}
