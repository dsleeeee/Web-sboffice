package kr.co.solbipos.sale.status.pos.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekService;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posDayOfWeekService")
public class PosDayOfWeekServiceImpl implements PosDayOfWeekService {
    private final PosDayOfWeekMapper posDayOfWeekMapper;
    private final MessageService messageService;

    @Autowired
    public PosDayOfWeekServiceImpl(PosDayOfWeekMapper posDayOfWeekMapper, MessageService messageService) {
        this.posDayOfWeekMapper = posDayOfWeekMapper;
        this.messageService = messageService;
    }

    /** 포스별매출요일별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO) {
		posDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posDayOfWeekMapper.getStorePosList(posDayOfWeekVO);
	}

    /** 포스별매출요일별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayOfWeekList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO) {
    	posDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayOfWeekVO.getStoreCd()).equals("")) {
//    		posDayOfWeekVO.setArrStoreCd(posDayOfWeekVO.getStoreCd().split(","));
//		}
        return posDayOfWeekMapper.getPosDayOfWeekList(posDayOfWeekVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO) {
		if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
			posDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		}
		return posDayOfWeekMapper.getPosNmList(posDayOfWeekVO);
	}

}
