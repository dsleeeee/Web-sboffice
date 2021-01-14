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

    /** 포스별탭 - 매장 및 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {

		prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		return prodPosMapper.getStorePosList(prodPosVO);
	}

    /** 포스별탭 - 매장 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPosNmList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {

		prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		return prodPosMapper.getPosNmList(prodPosVO);
	}

	/** 포스별탭 - 조회 */
	@Override
	public List<DefaultMap<String>> getProdPosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {

		prodPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

    	/*if (!StringUtil.getOrBlank(prodPosVO.getStoreCd()).equals("")) {
    		posProdVO.setArrStorePos(posProdVO.getStoreCd().split(","));
		}*/

		if (prodPosVO.getPosNo() != null && !"".equals(prodPosVO.getPosNo())) {
			String[] arrPosNo = prodPosVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
				if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
					prodPosVO.setArrPosNo(arrPosNo);
				}
			}
		}

		return prodPosMapper.getProdPosList(prodPosVO);
	}

	/** 포스별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdPosExcelList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO) {

		prodPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posProdVO.getStoreCd()).equals("")) {
//    		posProdVO.setArrStoreCd(posProdVO.getStoreCd().split(","));
//		}
    	
    	if (prodPosVO.getPosNo() != null && !"".equals(prodPosVO.getPosNo())) {
			String[] arrPosNo = prodPosVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				prodPosVO.setArrPosNo(arrPosNo);
    			}
    		}
		}

        return prodPosMapper.getProdPosExcelList(prodPosVO);
    }

}