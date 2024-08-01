package kr.co.solbipos.mobile.stock.curr.storeCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.curr.storeCurr.service.MobileStoreCurrService;
import kr.co.solbipos.mobile.stock.curr.storeCurr.service.MobileStoreCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Class Name : MobileStoreCurrServiceImpl.java
 * @Description : (모바일)재고현황 > 매장현재고
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
@Service("mobileStoreCurrService")
public class MobileStoreCurrServiceImpl implements MobileStoreCurrService {

    private final MobileStoreCurrMapper mobileStoreCurrMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public MobileStoreCurrServiceImpl(MobileStoreCurrMapper mobileStoreCurrMapper, PopupMapper popupMapper) {
        this.mobileStoreCurrMapper = mobileStoreCurrMapper;
        this.popupMapper = popupMapper;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCurrList(MobileStoreCurrVO mobileStoreCurrVO, SessionInfoVO sessionInfoVO) {

        mobileStoreCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileStoreCurrVO.getVendrCd()).equals("")) {
            mobileStoreCurrVO.setArrVendrCd(mobileStoreCurrVO.getVendrCd().split(","));
        }

        System.out.println("매장코드 출력: " + mobileStoreCurrVO.getStoreCd());
        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(mobileStoreCurrVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileStoreCurrVO.getStoreCd(), 3900));
            mobileStoreCurrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        return mobileStoreCurrMapper.getStoreCurrList(mobileStoreCurrVO);
    }
}
