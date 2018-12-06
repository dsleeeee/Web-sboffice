package kr.co.solbipos.iostock.vendr.prodStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.prodStockInfo.service.ProdStockInfoService;
import kr.co.solbipos.iostock.vendr.prodStockInfo.service.ProdStockInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("prodStockInfoService")
public class ProdStockInfoServiceImpl implements ProdStockInfoService {
    private final ProdStockInfoMapper prodStockInfoMapper;
    private final MessageService messageService;

    @Autowired
    public ProdStockInfoServiceImpl(ProdStockInfoMapper prodStockInfoMapper, MessageService messageService) {
        this.prodStockInfoMapper = prodStockInfoMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdStockInfoList(ProdStockInfoVO prodStockInfoVO, SessionInfoVO sessionInfoVO) {
        prodStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        prodStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(prodStockInfoVO.getVendrCd()).equals("")) {
            prodStockInfoVO.setArrVendrCd(prodStockInfoVO.getVendrCd().split(","));
        }

        return prodStockInfoMapper.getProdStockInfoList(prodStockInfoVO);
    }
}
