package kr.co.solbipos.sale.status.pos.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.day.service.PosDayService;
import kr.co.solbipos.sale.status.pos.day.service.PosDayVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posDayService")
public class PosDayServiceImpl implements PosDayService {
    private final PosDayMapper posDayMapper;
    private final MessageService messageService;

    @Autowired
    public PosDayServiceImpl(PosDayMapper posDayMapper, MessageService messageService) {
        this.posDayMapper = posDayMapper;
        this.messageService = messageService;
    }

    /** 포스별매출일자별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO) {
		posDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posDayMapper.getStorePosList(posDayVO);
	}

    /** 포스별매출일자별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO) {
    	posDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayVO.getStoreCd()).equals("")) {
//    		posDayVO.setArrStoreCd(posDayVO.getStoreCd().split(","));
//		}
        return posDayMapper.getPosDayList(posDayVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO) {
		posDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posDayMapper.getPosNmList(posDayVO);
	}

}
