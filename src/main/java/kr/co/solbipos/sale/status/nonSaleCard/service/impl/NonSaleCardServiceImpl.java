package kr.co.solbipos.sale.status.nonSaleCard.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.nonSaleCard.service.NonSaleCardService;
import kr.co.solbipos.sale.status.nonSaleCard.service.NonSaleCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : NonSaleCardServiceImpl.java
 * @Description : 매출관리 > 승인현황 > 비매출카드상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("nonSaleCardService")
@Transactional
public class NonSaleCardServiceImpl implements NonSaleCardService {
    private final NonSaleCardMapper nonSaleCardMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public NonSaleCardServiceImpl(NonSaleCardMapper nonSaleCardMapper, PopupMapper popupMapper) { this.nonSaleCardMapper = nonSaleCardMapper; this.popupMapper = popupMapper;}

    /** 비매출카드상세 - 조회 */
    @Override
    public List<DefaultMap<Object>> getNonSaleCardList(NonSaleCardVO nonSaleCardVO, SessionInfoVO sessionInfoVO) {

        nonSaleCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (nonSaleCardVO.getPosNo() != null && !"".equals(nonSaleCardVO.getPosNo())) {
            String[] arrPosNo = nonSaleCardVO.getPosNo().split(",");
            if (arrPosNo.length > 0) {
                if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
                    nonSaleCardVO.setArrPosNo(arrPosNo);
                }
            }
        } else {
            if(!StringUtil.getOrBlank(nonSaleCardVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(nonSaleCardVO.getStoreCd(), 3900));
                nonSaleCardVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        }

        return nonSaleCardMapper.getNonSaleCardList(nonSaleCardVO);
    }

}