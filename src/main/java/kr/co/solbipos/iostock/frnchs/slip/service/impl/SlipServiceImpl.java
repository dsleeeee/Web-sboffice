package kr.co.solbipos.iostock.frnchs.slip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.slip.service.SlipService;
import kr.co.solbipos.iostock.frnchs.slip.service.SlipVO;

@Service("slipServiceImpl")
public class SlipServiceImpl implements SlipService {
    private final SlipMapper slipMapper;
    private final MessageService messageService;

    @Autowired
    public SlipServiceImpl(SlipMapper slipMapper, MessageService messageService) {
        this.slipMapper = slipMapper;
        this.messageService = messageService;
    }

    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSlipList(SlipVO slipVO, SessionInfoVO sessionInfoVO) {
        slipVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        slipVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(slipVO.getStoreCd()).equals("")) {
            slipVO.setArrStoreCd(slipVO.getStoreCd().split(","));
        }

        return slipMapper.getSlipList(slipVO);
    }


    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSlipDtlList(SlipVO slipVO, SessionInfoVO sessionInfoVO) {
        slipVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        slipVO.setStoreCd(sessionInfoVO.getStoreCd());
        slipVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return slipMapper.getSlipDtlList(slipVO);
    }

    /** 전표별 입출고내역 - 조회조건 전표구분 콤보 리스트 조회  */
	@Override
	public List<DefaultMap<String>> getSrchSlipFgList(SlipVO slipVO, SessionInfoVO sessionInfoVO) {
		return slipMapper.getSrchSlipFgList(slipVO);
	}

	/** 전표별 입출고내역 - 조회조건 전표종류 콤보 리스트 조회  */
	@Override
	public List<DefaultMap<String>> getSrchSlipKindList(SlipVO slipVO, SessionInfoVO sessionInfoVO) {
		return slipMapper.getSrchSlipKindList(slipVO);
	}

	/** 전표별 입출고내역 - 조회조건 진행상태 콤보 리스트 조회  */
	@Override
	public List<DefaultMap<String>> getSrchProcFgList(SlipVO slipVO, SessionInfoVO sessionInfoVO) {
		return slipMapper.getSrchProcFgList(slipVO);
	}
}
