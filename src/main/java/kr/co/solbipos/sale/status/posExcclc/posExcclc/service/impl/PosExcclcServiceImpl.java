package kr.co.solbipos.sale.status.posExcclc.posExcclc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.posExcclc.posExcclc.service.PosExcclcService;
import kr.co.solbipos.sale.status.posExcclc.posExcclc.service.PosExcclcVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posExcclcService")
public class PosExcclcServiceImpl implements PosExcclcService {
    private final PosExcclcMapper posExcclcMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public PosExcclcServiceImpl(PosExcclcMapper posExcclcMapper, PopupMapper popupMapper, MessageService messageService) {
        this.posExcclcMapper = posExcclcMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** POS정산내역 - POS정산내역 리스트 조회  */
	@Override
	public List<DefaultMap<String>> getPosExcclcList(PosExcclcVO posExcclcVO, SessionInfoVO sessionInfoVO) {
		posExcclcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		posExcclcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		posExcclcVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(posExcclcVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(posExcclcVO.getStoreCd(), 3900));
            posExcclcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
		return posExcclcMapper.getPosExcclcList(posExcclcVO);
	}

	/** POS정산내역 - POS정산내역 세부 리스트 조회  */
	@Override
    public DefaultMap<String> getPosExcclcDetailInfo(PosExcclcVO posExcclcVO) {
        return posExcclcMapper.getPosExcclcDetailInfo(posExcclcVO);
    }

	@Override
	public List<DefaultMap<String>> getPosExcclcExcelList(PosExcclcVO posExcclcVO, SessionInfoVO sessionInfoVO) {
		posExcclcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		posExcclcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		posExcclcVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(posExcclcVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(posExcclcVO.getStoreCd(), 3900));
            posExcclcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
		return posExcclcMapper.getPosExcclcExcelList(posExcclcVO);
	}
}
