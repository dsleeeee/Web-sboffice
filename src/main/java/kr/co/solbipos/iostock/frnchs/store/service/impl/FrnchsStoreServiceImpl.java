package kr.co.solbipos.iostock.frnchs.store.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreService;
import kr.co.solbipos.iostock.frnchs.store.service.FrnchsStoreVO;

@Service("FrnchsStoreService")
public class FrnchsStoreServiceImpl implements FrnchsStoreService {
    private final FrnchsStoreMapper frnchsStoreMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public FrnchsStoreServiceImpl(FrnchsStoreMapper frnchsStoreMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.frnchsStoreMapper = frnchsStoreMapper;
    	this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 매장별 입출고내역 - 매장별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFrnchsStoreList(FrnchsStoreVO frnchsStoreVO, SessionInfoVO sessionInfoVO) {

    	frnchsStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(frnchsStoreVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsStoreVO.getStoreCd(), 3900));
            frnchsStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsStoreMapper.getFrnchsStoreList(frnchsStoreVO);
    }

    /** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회 */
	@Override
	public List<DefaultMap<String>> getFrnchsStoreInfoList(FrnchsStoreVO frnchsStoreVO) {
		//frnchsStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(frnchsStoreVO.getStoreCd()).equals("")) {
        	frnchsStoreVO.setArrStoreCd(frnchsStoreVO.getStoreCd().split(","));
        }*/

        return frnchsStoreMapper.getFrnchsStoreInfoList(frnchsStoreVO);
	}

	/** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getFrnchsStoreDtlList(FrnchsStoreVO frnchsStoreVO) {
		//frnchsStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(frnchsStoreVO.getStoreCd()).equals("")) {
        	frnchsStoreVO.setArrStoreCd(frnchsStoreVO.getStoreCd().split(","));
        }*/

        return frnchsStoreMapper.getFrnchsStoreDtlList(frnchsStoreVO);
	}


	/** 매장별 입출고내역 - 매장별 입출고내역 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getFrnchsStoreExcelList(FrnchsStoreVO frnchsStoreVO, SessionInfoVO sessionInfoVO) {

    	frnchsStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(frnchsStoreVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsStoreVO.getStoreCd(), 3900));
            frnchsStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsStoreMapper.getFrnchsStoreExcelList(frnchsStoreVO);
	}

}
