package kr.co.solbipos.iostock.frnchs.prod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsService;
import kr.co.solbipos.iostock.frnchs.prod.service.ProdFrnchsVO;

@Service("prodFrnchsService")
public class ProdFrnchsServiceImpl implements ProdFrnchsService {
    private final ProdFrnchsMapper prodFrnchsMapper;
    private final MessageService messageService;

    @Autowired
    public ProdFrnchsServiceImpl(ProdFrnchsMapper prodFrnchsMapper, MessageService messageService) {
        this.prodFrnchsMapper = prodFrnchsMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdFrnchsList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
        prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
        	prodFrnchsVO.setArrStoreCd(prodFrnchsVO.getStoreCd().split(","));
        }

        return prodFrnchsMapper.getProdFrnchsList(prodFrnchsVO);
    }

	@Override
	public List<DefaultMap<String>> getProdInOutstockInfoList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
		prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        
		// 매장 멀티 선택
        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
        	prodFrnchsVO.setArrStoreCd(prodFrnchsVO.getStoreCd().split(","));
        }

        return prodFrnchsMapper.getProdInOutstockInfoList(prodFrnchsVO);
	}

	/** 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getProdFrnchsExcelList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
		prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
        	prodFrnchsVO.setArrStoreCd(prodFrnchsVO.getStoreCd().split(","));
        }

        return prodFrnchsMapper.getProdFrnchsExcelList(prodFrnchsVO);
	}
}
