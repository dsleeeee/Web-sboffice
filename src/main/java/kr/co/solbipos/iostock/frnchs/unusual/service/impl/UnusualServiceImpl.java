package kr.co.solbipos.iostock.frnchs.unusual.service.impl;

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
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualService;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualVO;

@Service("unusualService")
public class UnusualServiceImpl implements UnusualService {
    private final UnusualMapper unusualMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public UnusualServiceImpl(UnusualMapper unusualMapper, PopupMapper popupMapper, MessageService messageService) {
        this.unusualMapper = unusualMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getUnusualList(UnusualVO unusualVO, SessionInfoVO sessionInfoVO) {
        unusualVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        unusualVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(unusualVO.getVendrCd()).equals("")) {
            unusualVO.setArrVendrCd(unusualVO.getVendrCd().split(","));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            unusualVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(unusualVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(unusualVO.getStoreCd(), 3900));
            unusualVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return unusualMapper.getUnusualList(unusualVO);
    }

	@Override
	public List<DefaultMap<String>> getUnusualExcelList(UnusualVO unusualVO, SessionInfoVO sessionInfoVO) {
		unusualVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        unusualVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(unusualVO.getVendrCd()).equals("")) {
            unusualVO.setArrVendrCd(unusualVO.getVendrCd().split(","));
        }
         
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            unusualVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(unusualVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(unusualVO.getStoreCd(), 3900));
            unusualVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return unusualMapper.getUnusualExcelList(unusualVO);
	}
}
