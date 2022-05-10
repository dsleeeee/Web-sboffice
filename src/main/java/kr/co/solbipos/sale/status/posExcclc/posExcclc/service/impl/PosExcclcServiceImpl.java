package kr.co.solbipos.sale.status.posExcclc.posExcclc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
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
    private final MessageService messageService;

    @Autowired
    public PosExcclcServiceImpl(PosExcclcMapper posExcclcMapper, MessageService messageService) {
        this.posExcclcMapper = posExcclcMapper;
        this.messageService = messageService;
    }

    /** POS정산내역 - POS정산내역 리스트 조회  */
	@Override
	public List<DefaultMap<String>> getPosExcclcList(PosExcclcVO posExcclcVO, SessionInfoVO sessionInfoVO) {
		posExcclcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		posExcclcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		posExcclcVO.setEmpNo(sessionInfoVO.getEmpNo());
		if (posExcclcVO.getStoreCd() != null && !"".equals(posExcclcVO.getStoreCd())) {
    		String[] arrStoreCd = posExcclcVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				posExcclcVO.setArrStoreCd(arrStoreCd);
    			}
    		}
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
		if (posExcclcVO.getStoreCd() != null && !"".equals(posExcclcVO.getStoreCd())) {
    		String[] arrStoreCd = posExcclcVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				posExcclcVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return posExcclcMapper.getPosExcclcExcelList(posExcclcVO);
	}
}
