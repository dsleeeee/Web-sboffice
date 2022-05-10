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

        mobileProdSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileProdSaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileProdSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileProdSaleVO.setArrStoreCd(mobileProdSaleVO.getSrchStoreCd().split(","));
        }

        return mobileProdSaleMapper.getProdSaleList(mobileProdSaleVO);
    }

    /** 모바일 매출현황 - 다중매장조회 */
    @Override
    public List<DefaultMap<String>> getMultiStoreList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO) {

        mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileProdSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
        mobileProdSaleVO.setUserId(sessionInfoVO.getUserId());

        return mobileProdSaleMapper.getMultiStoreList(mobileProdSaleVO);
    }

    /** 모바일 매출현황 - 매장조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO) {

        mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileProdSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        return mobileProdSaleMapper.getStoreList(mobileProdSaleVO);
    }

}
