package kr.co.solbipos.mobile.sale.status.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;

@Service("MobileProdSaleService")
public class MobileProdSaleServiceImpl implements MobileProdSaleService {
    private final MobileProdSaleMapper mobileProdSaleMapper;

    @Autowired
    public MobileProdSaleServiceImpl(MobileProdSaleMapper mobileProdSaleMapper) {
        this.mobileProdSaleMapper = mobileProdSaleMapper;
    }

    /** 모바일 매출현황 - 상품별매출현황 */
    @Override
    public List<DefaultMap<String>> getProdSaleList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO) {

        mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(mobileProdSaleVO.getStoreCd()).equals("")) {
            mobileProdSaleVO.setArrStoreCd(mobileProdSaleVO.getStoreCd().split(","));
        }

        return mobileProdSaleMapper.getProdSaleList(mobileProdSaleVO);
    }

    /** 모바일 매출현황 - 다중매장조회 */
    @Override
    public List<DefaultMap<String>> getMultiStoreList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO) {

        mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileProdSaleVO.setStoreCd(sessionInfoVO.getStoreCd());

        return mobileProdSaleMapper.getMultiStoreList(mobileProdSaleVO);
    }

}
