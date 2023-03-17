package kr.co.solbipos.base.prod.kioskDisplay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayService;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskDisplayServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 비노출관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("KioskDisplayService")
public class KioskDisplayServiceImpl implements KioskDisplayService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KioskDisplayMapper kioskDisplayMapper;

    /** Constructor Injection */
    @Autowired
    public KioskDisplayServiceImpl(KioskDisplayMapper kioskDisplayMapper) {
        this.kioskDisplayMapper = kioskDisplayMapper;
    }

    @Override
    public List<DefaultMap<String>> getProdList(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        kioskDisplayVO.setUserId(sessionInfoVO.getUserId());

        return kioskDisplayMapper.getProdList(kioskDisplayVO);
    }

    @Override
    public DefaultMap<String> getProdDetail(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 상품상세정보 조회
        result = kioskDisplayMapper.getProdDetail(kioskDisplayVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = kioskDisplayMapper.getLinkedProdList(kioskDisplayVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    // 상품 품절관리 업데이트
    @Override
    public int getProdKioskDisplaySave(KioskDisplayVO[] kioskDisplayVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(KioskDisplayVO kioskDisplayVO : kioskDisplayVOs) {

            kioskDisplayVO.setModDt(currentDt);
            kioskDisplayVO.setModId(sessionInfoVO.getUserId());

            procCnt = kioskDisplayMapper.getProdKioskDisplaySave(kioskDisplayVO);
        }

        return procCnt;
    }
}