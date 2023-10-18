package kr.co.solbipos.iostock.frnchs.prod.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ProdFrnchsServiceImpl(ProdFrnchsMapper prodFrnchsMapper, PopupMapper popupMapper, MessageService messageService) {
        this.prodFrnchsMapper = prodFrnchsMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdFrnchsList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
        prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodFrnchsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodFrnchsVO.getStoreCd(), 3900));
            prodFrnchsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodFrnchsMapper.getProdFrnchsList(prodFrnchsVO);
    }

	@Override
	public List<DefaultMap<String>> getProdInOutstockInfoList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
		prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        
        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodFrnchsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodFrnchsVO.getStoreCd(), 3900));
            prodFrnchsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodFrnchsMapper.getProdInOutstockInfoList(prodFrnchsVO);
	}

	/** 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getProdFrnchsExcelList(ProdFrnchsVO prodFrnchsVO, SessionInfoVO sessionInfoVO) {
		prodFrnchsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodFrnchsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodFrnchsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodFrnchsVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodFrnchsVO.getStoreCd(), 3900));
            prodFrnchsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodFrnchsMapper.getProdFrnchsExcelList(prodFrnchsVO);
	}
}
