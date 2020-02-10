package kr.co.solbipos.sale.status.prod.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosService;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("prodPosService")
public class ProdPosServiceImpl implements ProdPosService {
    private final ProdPosMapper prodPosMapper;
    private final MessageService messageService;

    @Autowired
    public ProdPosServiceImpl(ProdPosMapper prodPosMapper, MessageService messageService) {
        this.prodPosMapper = prodPosMapper;
        this.messageService = messageService;
    }

    /** 포스별매출상품별 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {
		prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return prodPosMapper.getStorePosList(prodPosVO);
	}

    /** 포스별매출상품별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdPosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {
    	prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posProdVO.getStoreCd()).equals("")) {
//    		posProdVO.setArrStoreCd(posProdVO.getStoreCd().split(","));
//		}
        return prodPosMapper.getProdPosList(prodPosVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {
		prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return prodPosMapper.getPosNmList(prodPosVO);
	}

}
