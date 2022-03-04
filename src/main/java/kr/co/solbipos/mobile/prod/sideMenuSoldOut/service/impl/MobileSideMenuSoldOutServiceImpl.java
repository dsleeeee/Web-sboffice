package kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.MobileSideMenuSoldOutService;
import kr.co.solbipos.mobile.prod.sideMenuSoldOut.service.MobileSideMenuSoldOutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileProdServiceImpl.java
 * @Description : 상품관리 > 품절관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("MobileSideMenuSoldOutService")
public class MobileSideMenuSoldOutServiceImpl implements MobileSideMenuSoldOutService {
    private final MobileSideMenuSoldOutMapper mobileSideMenuSoldOutMapper;

    @Autowired
    public MobileSideMenuSoldOutServiceImpl(MobileSideMenuSoldOutMapper mobileSideMenuSoldOutMapper) {
        this.mobileSideMenuSoldOutMapper = mobileSideMenuSoldOutMapper;
    }

    // 선택그룹 조회
    @Override
    public List<DefaultMap<String>> getMobileSideMenuGrpList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            mobileSideMenuSoldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mobileSideMenuSoldOutMapper.getMobileSideMenuGrpList(mobileSideMenuSoldOutVO);
    }

    // 선택분류 조회
    @Override
    public List<DefaultMap<String>> getMobileSideMenuClassList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            mobileSideMenuSoldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mobileSideMenuSoldOutMapper.getMobileSideMenuClassList(mobileSideMenuSoldOutVO);

    }

    // 선택상품 조회
    @Override
    public List<DefaultMap<String>> getMobileSideMenuProdList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            mobileSideMenuSoldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        mobileSideMenuSoldOutVO.setUserId(sessionInfoVO.getUserId());

        return mobileSideMenuSoldOutMapper.getMobileSideMenuProdList(mobileSideMenuSoldOutVO);

    }

    // 품절여부 저장
    @Override
    public int getMobileSideMenuSoldOutSave(MobileSideMenuSoldOutVO[] mobileSideMenuSoldOutVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO : mobileSideMenuSoldOutVOs) {

            mobileSideMenuSoldOutVO.setModDt(currentDt);
            mobileSideMenuSoldOutVO.setModId(sessionInfoVO.getUserId());

            procCnt = mobileSideMenuSoldOutMapper.getMobileSideMenuSoldOutSave(mobileSideMenuSoldOutVO);

        }

        return procCnt;
    }
}
