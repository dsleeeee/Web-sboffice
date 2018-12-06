package kr.co.solbipos.iostock.vendr.slipStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.slipStockInfo.service.SlipStockInfoService;
import kr.co.solbipos.iostock.vendr.slipStockInfo.service.SlipStockInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("slipStockInfoServiceImpl")
public class SlipStockInfoServiceImpl implements SlipStockInfoService {
    private final SlipStockInfoMapper slipStockInfoMapper;
    private final MessageService messageService;

    @Autowired
    public SlipStockInfoServiceImpl(SlipStockInfoMapper slipStockInfoMapper, MessageService messageService) {
        this.slipStockInfoMapper = slipStockInfoMapper;
        this.messageService = messageService;
    }

    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSlipStockInfoList(SlipStockInfoVO slipStockInfoVO, SessionInfoVO sessionInfoVO) {
        slipStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        slipStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        slipStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(slipStockInfoVO.getVendrCd()).equals("")) {
            slipStockInfoVO.setArrVendrCd(slipStockInfoVO.getVendrCd().split(","));
        }

        return slipStockInfoMapper.getSlipStockInfoList(slipStockInfoVO);
    }


    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSlipStockInfoDtlList(SlipStockInfoVO slipStockInfoVO, SessionInfoVO sessionInfoVO) {
        slipStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        slipStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        slipStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return slipStockInfoMapper.getSlipStockInfoDtlList(slipStockInfoVO);
    }
}
