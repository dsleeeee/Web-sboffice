package kr.co.solbipos.mobile.prod.prodSoldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutVO;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutService;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutVO;
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

@Service("MobileProdSoldOutService")
public class MobileProdSoldOutServiceImpl implements MobileProdSoldOutService {
    private final MobileProdSoldOutMapper mobileProdSoldOutMapper;
    private final MessageService messageService;

    @Autowired
    public MobileProdSoldOutServiceImpl(MobileProdSoldOutMapper mobileProdSoldOutMapper, MessageService messageService) {
        this.mobileProdSoldOutMapper = mobileProdSoldOutMapper;
        this.messageService = messageService;
    }

    // 상품 조회
    @Override
    public List<DefaultMap<String>> getMobileProdSoldOutList(MobileProdSoldOutVO mobileProdSoldOutVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            mobileProdSoldOutVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        mobileProdSoldOutVO.setUserId(sessionInfoVO.getUserId());

        return mobileProdSoldOutMapper.getMobileProdSoldOutList(mobileProdSoldOutVO);
    }

    // 품절여부 저장
    @Override
    public int getMobileProdSoldOutSave(MobileProdSoldOutVO[] mobileProdSoldOutVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(MobileProdSoldOutVO mobileProdSoldOutVO : mobileProdSoldOutVOs) {

            mobileProdSoldOutVO.setModDt(currentDt);
            mobileProdSoldOutVO.setModId(sessionInfoVO.getUserId());

            procCnt = mobileProdSoldOutMapper.getMobileProdSoldOutSave(mobileProdSoldOutVO);

        }

        return procCnt;
    }
}
