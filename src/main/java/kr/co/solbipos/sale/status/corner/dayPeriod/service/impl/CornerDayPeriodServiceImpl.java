package kr.co.solbipos.sale.status.corner.dayPeriod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodService;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodVO;

@Service("cornerDayPeriodService")
public class CornerDayPeriodServiceImpl implements CornerDayPeriodService {
    private final CornerDayPeriodMapper cornerDayPeriodMapper;
    private final MessageService messageService;

    @Autowired
    public CornerDayPeriodServiceImpl(CornerDayPeriodMapper cornerDayPeriodMapper, MessageService messageService) {
        this.cornerDayPeriodMapper = cornerDayPeriodMapper;
        this.messageService = messageService;
    }


    /** 코너별매출 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCornerDayPeriodList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {
    	cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	
    	if (cornerDayPeriodVO.getCornrCd() != null && !"".equals(cornerDayPeriodVO.getCornrCd())) {
    		String[] arrCornrCd = cornerDayPeriodVO.getCornrCd().split(",");

    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerDayPeriodVO.setArrCornrCd(arrCornrCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = cornerDayPeriodVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				cornerDayPeriodVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
        return cornerDayPeriodMapper.getCornerDayPeriodList(cornerDayPeriodVO);
    }

    
    /** 코너별매출 - 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCornerDayPeriodDtlList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {
		cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		return cornerDayPeriodMapper.getCornerDayPeriodDtlList(cornerDayPeriodVO);
	}

}
