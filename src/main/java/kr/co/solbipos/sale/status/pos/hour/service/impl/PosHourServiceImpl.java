package kr.co.solbipos.sale.status.pos.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.hour.service.PosHourService;
import kr.co.solbipos.sale.status.pos.hour.service.PosHourVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posHourService")
public class PosHourServiceImpl implements PosHourService {
    private final PosHourMapper posHourMapper;
    private final MessageService messageService;

    @Autowired
    public PosHourServiceImpl(PosHourMapper posHourMapper, MessageService messageService) {
        this.posHourMapper = posHourMapper;
        this.messageService = messageService;
    }

    /** 포스별매출시간대별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO) {
		posHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posHourMapper.getStorePosList(posHourVO);
	}

    /** 포스별매출시간대별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO) {
    	posHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayVO.getStoreCd()).equals("")) {
//    		posDayVO.setArrStoreCd(posDayVO.getStoreCd().split(","));
//		}
        return posHourMapper.getPosDayList(posHourVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO) {
		if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
			posHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		}
		return posHourMapper.getPosNmList(posHourVO);
	}

}
