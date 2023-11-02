package kr.co.solbipos.sale.appr.momsGift.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.momsGift.service.MomsGiftService;
import kr.co.solbipos.sale.appr.momsGift.service.MomsGiftVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MomsMomsGiftServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 상품권 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("momsGiftService")
@Transactional
public class MomsGiftServiceImpl implements MomsGiftService {
    private final MomsGiftMapper giftMapper;
    private final PopupMapper popupMapper;

    public MomsGiftServiceImpl(MomsGiftMapper giftMapper, PopupMapper popupMapper) {
        this.giftMapper = giftMapper;
        this.popupMapper = popupMapper;
    }

    /** 모바일쿠폰입금관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftList(MomsGiftVO giftVO, SessionInfoVO sessionInfoVO) {

        giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            giftVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(giftVO.getStoreCds()).equals("")) {
       		StoreVO storeVO = new StoreVO();
       		storeVO.setArrSplitStoreCd(CmmUtil.splitText(giftVO.getStoreCds(), 3900));
            giftVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
       	}
        
        return giftMapper.getGiftList(giftVO);
    }

    /** 모바일쿠폰입금관리 - 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftExcelList(MomsGiftVO giftVO, SessionInfoVO sessionInfoVO) {

        giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            giftVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(giftVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(giftVO.getStoreCds(), 3900));
            giftVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        
        return giftMapper.getGiftExcelList(giftVO);
    }

}