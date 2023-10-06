package kr.co.solbipos.sale.status.dc.dcfg.service.impl;

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
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgService;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgVO;

@Service("dcDcfgService")
public class DcDcfgServiceImpl implements DcDcfgService {
    private final DcDcfgMapper dcDcfgMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public DcDcfgServiceImpl(DcDcfgMapper dcDcfgMapper, PopupMapper popupMapper, MessageService messageService) {
        this.dcDcfgMapper = dcDcfgMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 할일구분별매출 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcDcfgList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dcDcfgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcDcfgVO.getStoreCd(), 3900));
            dcDcfgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

    	if(!StringUtil.getOrBlank(dcDcfgVO.getDcCd()).equals("")) {
        	dcDcfgVO.setArrDcCd(dcDcfgVO.getDcCd().split(","));
        }
        return dcDcfgMapper.getDcDcfgList(dcDcfgVO);
    }

    /** 할일구분별매출 - 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcDcfgExcelList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dcDcfgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcDcfgVO.getStoreCd(), 3900));
            dcDcfgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

    	if(!StringUtil.getOrBlank(dcDcfgVO.getDcCd()).equals("")) {
        	dcDcfgVO.setArrDcCd(dcDcfgVO.getDcCd().split(","));
        }
        return dcDcfgMapper.getDcDcfgExcelList(dcDcfgVO);
    }


    /** 할일구분별매출 - 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getDcDcfgDtlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		return dcDcfgMapper.getDcDcfgDtlList(dcDcfgVO);
	}

	/** 할인구분별 탭 - 할인유형 콤보박스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getDcNmlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcDcfgVO.getStoreCd(), 3900));
            dcDcfgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

		if(!StringUtil.getOrBlank(dcDcfgVO.getDcCd()).equals("")) {
        	dcDcfgVO.setArrDcCd(dcDcfgVO.getDcCd().split(","));
        }
		return dcDcfgMapper.getDcNmList(dcDcfgVO);
	}

}
