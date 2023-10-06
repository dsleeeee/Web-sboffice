package kr.co.solbipos.sale.status.barcd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.barcd.service.BarcdService;
import kr.co.solbipos.sale.status.barcd.service.BarcdVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("barcdService")
public class BarcdServiceImpl implements BarcdService {
    private final BarcdMapper barcdMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public BarcdServiceImpl(BarcdMapper barcdMapper, PopupMapper popupMapper, MessageService messageService) {
        this.barcdMapper = barcdMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 바코드별매출 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getBarcdList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
    	barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		barcdVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(barcdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(barcdVO.getStoreCd(), 3900));
            barcdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return barcdMapper.getBarcdList(barcdVO);
    }


    /** 바코드별매출 - 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getBarcdDtlList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
    	barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		barcdVO.setEmpNo(sessionInfoVO.getEmpNo());
		
        if(!StringUtil.getOrBlank(barcdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(barcdVO.getStoreCd(), 3900));
            barcdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return barcdMapper.getBarcdDtlList(barcdVO);
	}

    /** 바코드별매출 - 엑셀 전체 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getBarcdExcelList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
		barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		barcdVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(barcdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(barcdVO.getStoreCd(), 3900));
            barcdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return barcdMapper.getBarcdExcelList(barcdVO);
	}

    /** 바코드별매출 - 엑셀 전체 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getBarcdDtlExcelList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO) {
		barcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	barcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		barcdVO.setEmpNo(sessionInfoVO.getEmpNo());
		
        if(!StringUtil.getOrBlank(barcdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(barcdVO.getStoreCd(), 3900));
            barcdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return barcdMapper.getBarcdDtlExcelList(barcdVO);
	}
}
