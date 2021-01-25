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

    /** 상품별탭 - 매장 및 포스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {

		posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		return posProdMapper.getStorePosList(posProdVO);
	}

    /** 상품별탭 - 매장 코너 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
            posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        return posProdMapper.getPosNmList(posProdVO);
    }

    /** 상품별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {

        posProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posProdMapper.getPosProdList(posProdVO);
    }

    /** 상품별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getPosProdExcelList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO) {

        posProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	posProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posProdMapper.getPosProdExcelList(posProdVO);
    }

 }