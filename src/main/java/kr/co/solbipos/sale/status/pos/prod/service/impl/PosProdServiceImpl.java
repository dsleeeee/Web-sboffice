package kr.co.solbipos.sale.status.pos.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdService;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posProdService")
public class PosProdServiceImpl implements PosProdService {
    private final PosProdMapper posProdMapper;
    private final MessageService messageService;

    @Autowired
    public PosProdServiceImpl(PosProdMapper posProdMapper, MessageService messageService) {
        this.posProdMapper = posProdMapper;
        this.messageService = messageService;
    }

    /** 포스별매출상품별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {
		posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return posProdMapper.getStorePosList(posProdVO);
	}

    /** 포스별매출상품별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {
    	posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posProdVO.getStoreCd()).equals("")) {
//    		posProdVO.setArrStoreCd(posProdVO.getStoreCd().split(","));
//		}
        return posProdMapper.getPosProdList(posProdVO);
    }

    /** 포스별매출상품별 - 리스트 조회 (엑셀) */
    @Override
    public List<DefaultMap<String>> getPosProdExcelList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {
    	posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posProdVO.getStoreCd()).equals("")) {
//    		posProdVO.setArrStoreCd(posProdVO.getStoreCd().split(","));
//		}
        return posProdMapper.getPosProdExcelList(posProdVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {
		if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
			posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		}
		return posProdMapper.getPosNmList(posProdVO);
	}

}
