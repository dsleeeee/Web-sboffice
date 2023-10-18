package kr.co.solbipos.iostock.frnchs.storeProd.service.impl;

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
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdService;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdVO;

@Service("FrnchsStoreProdService")
public class FrnchsStoreProdServiceImpl implements FrnchsStoreProdService {
    private final FrnchsStoreProdMapper frnchsStoreProdMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public FrnchsStoreProdServiceImpl(FrnchsStoreProdMapper frnchsStoreProdMapper, PopupMapper popupMapper, MessageService messageService) {
    	this.frnchsStoreProdMapper = frnchsStoreProdMapper;
    	this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFrnchsStoreProdList(FrnchsStoreProdVO frnchsStoreProdVO, SessionInfoVO sessionInfoVO) {

    	frnchsStoreProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(frnchsStoreProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsStoreProdVO.getStoreCd(), 3900));
            frnchsStoreProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsStoreProdMapper.getFrnchsStoreProdList(frnchsStoreProdVO);
    }

    /** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFrnchsStoreProdDtlList(FrnchsStoreProdVO frnchsStoreProdVO, SessionInfoVO sessionInfoVO) {

    	frnchsStoreProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return frnchsStoreProdMapper.getFrnchsStoreProdDtlList(frnchsStoreProdVO);
    }


    /** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getFrnchsStoreProdExcelList(FrnchsStoreProdVO frnchsStoreProdVO, SessionInfoVO sessionInfoVO) {

    	frnchsStoreProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(frnchsStoreProdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(frnchsStoreProdVO.getStoreCd(), 3900));
            frnchsStoreProdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return frnchsStoreProdMapper.getFrnchsStoreProdExcelList(frnchsStoreProdVO);
	}
}
