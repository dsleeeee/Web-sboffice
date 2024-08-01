package kr.co.solbipos.mobile.stock.status.storeMonth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storeMonth.service.MobileStoreMonthService;
import kr.co.solbipos.mobile.stock.status.storeMonth.service.MobileStoreMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Class Name : MobileStoreMonthServiceImpl.java
 * @Description : (모바일)재고현황 > 매장월수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobileStoreMonthService")
public class MobileStoreMonthServiceImpl implements MobileStoreMonthService {

    private final MobileStoreMonthMapper mobileStoreMonthMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public MobileStoreMonthServiceImpl(MobileStoreMonthMapper mobileStoreMonthMapper, PopupMapper popupMapper) {
        this.mobileStoreMonthMapper = mobileStoreMonthMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장월수불 리스트 조회 */
    @Override
    public List<DefaultMap<String>> stockStoreMonthList(MobileStoreMonthVO mobileStoreMonthVO, SessionInfoVO sessionInfoVO) {
        mobileStoreMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileStoreMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(mobileStoreMonthVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStoreMonthVO.getStoreCd(), 3900));
            mobileStoreMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (mobileStoreMonthVO.getVendrCd() != null && !"".equals(mobileStoreMonthVO.getVendrCd())) {
            String[] arrVendrCd = mobileStoreMonthVO.getVendrCd().split(",");
            if (arrVendrCd.length > 0) {
                if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
                    mobileStoreMonthVO.setArrVendrCd(arrVendrCd);
                }
            }
        }

        return mobileStoreMonthMapper.stockStoreMonthList(mobileStoreMonthVO);
    }

    @Override
    public List<DefaultMap<String>> stockStoreMonthExcelList(MobileStoreMonthVO mobileStoreMonthVO, SessionInfoVO sessionInfoVO) {
        mobileStoreMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileStoreMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(mobileStoreMonthVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStoreMonthVO.getStoreCd(), 3900));
            mobileStoreMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (mobileStoreMonthVO.getVendrCd() != null && !"".equals(mobileStoreMonthVO.getVendrCd())) {
            String[] arrVendrCd = mobileStoreMonthVO.getVendrCd().split(",");
            if (arrVendrCd.length > 0) {
                if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
                    mobileStoreMonthVO.setArrVendrCd(arrVendrCd);
                }
            }
        }

        return mobileStoreMonthMapper.stockStoreMonthExcelList(mobileStoreMonthVO);
    }
}
