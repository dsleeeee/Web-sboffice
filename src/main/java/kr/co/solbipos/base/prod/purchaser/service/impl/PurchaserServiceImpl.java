package kr.co.solbipos.base.prod.purchaser.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.purchaser.service.PurchaserService;
import kr.co.solbipos.base.prod.purchaser.service.PurchaserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : PurchaserServiceImpl.java
 * @Description : 국민대 > 매입처관리 > 매입처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("purchaserService")
@Transactional
public class PurchaserServiceImpl implements PurchaserService {
    private final PurchaserMapper purchaserMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PurchaserServiceImpl(PurchaserMapper purchaserMapper, PopupMapper popupMapper) {
        this.purchaserMapper = purchaserMapper;
        this.popupMapper = popupMapper;
    }

    /** 매입처조회 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPurchaserList(PurchaserVO purchaserVO, SessionInfoVO sessionInfoVO) {

        purchaserVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return purchaserMapper.getPurchaserList(purchaserVO);
    }
}