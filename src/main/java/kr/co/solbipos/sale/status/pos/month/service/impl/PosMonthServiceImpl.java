package kr.co.solbipos.sale.status.pos.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.month.service.PosMonthService;
import kr.co.solbipos.sale.status.pos.month.service.PosMonthVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posMonthService")
public class PosMonthServiceImpl implements PosMonthService {
    private final PosMonthMapper posMonthMapper;
    private final MessageService messageService;

    @Autowired
    public PosMonthServiceImpl(PosMonthMapper posMonthMapper, MessageService messageService) {
        this.posMonthMapper = posMonthMapper;
        this.messageService = messageService;
    }

    /** 포스별매출월별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO) {
		posMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posMonthMapper.getStorePosList(posMonthVO);
	}

    /** 포스별매출월별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosMonthList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO) {
    	posMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posMonthVO.getStoreCd()).equals("")) {
//    		posMonthVO.setArrStoreCd(posMonthVO.getStoreCd().split(","));
//		}
        return posMonthMapper.getPosMonthList(posMonthVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO) {
		if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
			posMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		}
		return posMonthMapper.getPosNmList(posMonthVO);
	}

	@Override
	public List<DefaultMap<String>> getPosMonthExcelList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO) {
		posMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posMonthVO.getStoreCd()).equals("")) {
//    		posMonthVO.setArrStoreCd(posMonthVO.getStoreCd().split(","));
//		}
        return posMonthMapper.getPosMonthExcelList(posMonthVO);
	}

}
