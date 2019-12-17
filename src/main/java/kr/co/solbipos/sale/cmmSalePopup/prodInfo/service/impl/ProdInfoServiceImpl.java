package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoService;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("prodInfoService")
public class ProdInfoServiceImpl implements ProdInfoService {
    private final ProdInfoMapper prodInfoMapper;

    public ProdInfoServiceImpl(ProdInfoMapper prodInfoMapper) {
        this.prodInfoMapper = prodInfoMapper;
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

        prodInfoVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodInfoVO.getStoreCds().split(",");
        prodInfoVO.setStoreCdList(storeCds);

        return prodInfoMapper.getProdSaleDtlList(prodInfoVO);
    }
}
