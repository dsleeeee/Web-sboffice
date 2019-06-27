package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoService;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("prodInfoService")
public class ProdInfoServiceImpl implements ProdInfoService {
    private final ProdInfoMapper prodInfoMapper;

    public ProdInfoServiceImpl(ProdInfoMapper prodInfoMapper) {
        this.prodInfoMapper = prodInfoMapper;
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<String>> getProdSaleDtlList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCd()).equals("")) {
            prodInfoVO.setArrStoreCd(prodInfoVO.getStoreCd().split(","));
        }

        return prodInfoMapper.getProdSaleDtlList(prodInfoVO);
    }
}
