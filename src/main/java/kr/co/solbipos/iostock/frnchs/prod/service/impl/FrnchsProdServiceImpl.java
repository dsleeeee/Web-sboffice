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
import kr.co.solbipos.iostock.frnchs.prod.service.FrnchsProdService;
import kr.co.solbipos.iostock.frnchs.prod.service.FrnchsProdVO;

@Service("frnchsProdService")
public class FrnchsProdServiceImpl implements FrnchsProdService {
    private final FrnchsProdMapper frnchsProdMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public FrnchsProdServiceImpl(FrnchsProdMapper frnchsProdMapper, PopupMapper popupMapper, MessageService messageService) {
        this.frnchsProdMapper = frnchsProdMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdFrnchsList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO) {
        frnchsProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        frnchsProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            frnchsProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(frnchsProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsProdVO.getStoreCd(), 3900));
            frnchsProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsProdMapper.getProdFrnchsList(frnchsProdVO);
    }

    @Override
    public List<DefaultMap<String>> getProdInOutstockInfoList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO) {
        frnchsProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        frnchsProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            frnchsProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(frnchsProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsProdVO.getStoreCd(), 3900));
            frnchsProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsProdMapper.getProdInOutstockInfoList(frnchsProdVO);
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdFrnchsExcelList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO) {
        frnchsProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        frnchsProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            frnchsProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(frnchsProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsProdVO.getStoreCd(), 3900));
            frnchsProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsProdMapper.getProdFrnchsExcelList(frnchsProdVO);
    }
}
