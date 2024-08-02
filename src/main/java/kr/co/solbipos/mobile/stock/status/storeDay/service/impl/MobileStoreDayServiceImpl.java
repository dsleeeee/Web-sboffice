package kr.co.solbipos.mobile.stock.status.storeDay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storeDay.service.MobileStoreDayService;
import kr.co.solbipos.mobile.stock.status.storeDay.service.MobileStoreDayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : MobileStoreDayServiceImpl.java
 * @Description : (모바일)재고현황 > 매장일수불
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
@Service("MobileStoreDayService")
public class MobileStoreDayServiceImpl implements MobileStoreDayService {

    private final MobileStoreDayMapper mobileStoreDayMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public MobileStoreDayServiceImpl(MobileStoreDayMapper mobileStoreDayMapper, PopupMapper popupMapper) {
        this.mobileStoreDayMapper = mobileStoreDayMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장일수불 리스트 조회 */
    @Override
    public List<DefaultMap<String>> storeDayList(MobileStoreDayVO mobileStoreDayVO, SessionInfoVO sessionInfoVO) {
        mobileStoreDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileStoreDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(mobileStoreDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStoreDayVO.getStoreCd(), 3900));
            mobileStoreDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (mobileStoreDayVO.getVendrCd() != null && !"".equals(mobileStoreDayVO.getVendrCd())) {
            String[] arrVendrCd = mobileStoreDayVO.getVendrCd().split(",");
            if (arrVendrCd.length > 0) {
                if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
                    mobileStoreDayVO.setArrVendrCd(arrVendrCd);
                }
            }
        }

        return mobileStoreDayMapper.storeDayList(mobileStoreDayVO);
    }

    /** 매장일수불 엑셀 전체 리스트 조회 */
    @Override
    public List<DefaultMap<String>> storeDayExcelList(MobileStoreDayVO mobileStoreDayVO, SessionInfoVO sessionInfoVO) {
        mobileStoreDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileStoreDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(mobileStoreDayVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStoreDayVO.getStoreCd(), 3900));
            mobileStoreDayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (mobileStoreDayVO.getVendrCd() != null && !"".equals(mobileStoreDayVO.getVendrCd())) {
            String[] arrVendrCd = mobileStoreDayVO.getVendrCd().split(",");
            if (arrVendrCd.length > 0) {
                if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
                    mobileStoreDayVO.setArrVendrCd(arrVendrCd);
                }
            }
        }

        return mobileStoreDayMapper.storeDayExcelList(mobileStoreDayVO);
    }
    
}
