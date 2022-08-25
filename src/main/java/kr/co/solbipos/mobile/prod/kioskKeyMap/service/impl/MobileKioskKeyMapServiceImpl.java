package kr.co.solbipos.mobile.prod.kioskKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.prod.kioskKeyMap.service.MobileKioskKeyMapService;
import kr.co.solbipos.mobile.prod.kioskKeyMap.service.MobileKioskKeyMapVO;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutVO;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.impl.MobileProdSoldOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileKioskKeyMapServiceImpl.java
 * @Description : 상품관리 > 키오스크키맵
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.22  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("MobileKioskKeyMapService")
public class MobileKioskKeyMapServiceImpl implements MobileKioskKeyMapService {
    private final MobileKioskKeyMapMapper mobileKioskKeyMapMapper;
    private final MessageService messageService;

    @Autowired
    public MobileKioskKeyMapServiceImpl(MobileKioskKeyMapMapper mobileKioskKeyMapMapper, MessageService messageService) {
        this.mobileKioskKeyMapMapper = mobileKioskKeyMapMapper;
        this.messageService = messageService;
    }

    // 매장 키맵 조회
    @Override
    public List<DefaultMap<String>> getMobileKioskKeyMapStoreList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO) {
        mobileKioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        return mobileKioskKeyMapMapper.getMobileKioskKeyMapStoreList(mobileKioskKeyMapVO);
    }

    // 포장 키맵 조회
    @Override
    public List<DefaultMap<String>> getMobileKioskKeyMapPackList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO) {
        mobileKioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        return mobileKioskKeyMapMapper.getMobileKioskKeyMapPackList(mobileKioskKeyMapVO);
    }

    // 키맵 저장
    @Override
    public int getMobileKioskKeyMapGrpSave(MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int cnt = 0;
        String currentDt = currentDateTimeString();

        for(MobileKioskKeyMapVO mobileKioskKeyMapVO : mobileKioskKeyMapVOs) {
            mobileKioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            mobileKioskKeyMapVO.setModDt(currentDt);
            mobileKioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            cnt = mobileKioskKeyMapMapper.getMobileKioskKeyMapGrpSave(mobileKioskKeyMapVO);
        }
        return cnt;
    }

    // 키맵 상품 조회
    @Override
    public List<DefaultMap<String>> getMobileKioskKeyMapProdList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO) {
        mobileKioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        return mobileKioskKeyMapMapper.getMobileKioskKeyMapProdList(mobileKioskKeyMapVO);
    }

    // 키맵 상품 저장
    @Override
    public int getMobileKioskKeyMapProdSave(MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int cnt = 0;
        String currentDt = currentDateTimeString();

        for(MobileKioskKeyMapVO mobileKioskKeyMapVO : mobileKioskKeyMapVOs) {
            mobileKioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            mobileKioskKeyMapVO.setModDt(currentDt);
            mobileKioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            cnt = mobileKioskKeyMapMapper.getMobileKioskKeyMapProdSave(mobileKioskKeyMapVO);
        }
        return cnt;
    }
}
