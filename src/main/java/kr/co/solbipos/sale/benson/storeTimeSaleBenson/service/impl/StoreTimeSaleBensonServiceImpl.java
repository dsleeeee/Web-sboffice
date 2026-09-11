package kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.StoreTimeSaleBensonService;
import kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.StoreTimeSaleBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreTimeSaleBensonServiceImpl.java
 * @Description : 벤슨 > 매출조회 > 지정가맹점_시간대별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeTimeSaleBensonService")
@Transactional
public class StoreTimeSaleBensonServiceImpl implements StoreTimeSaleBensonService {
    private final StoreTimeSaleBensonMapper storeTimeSaleBensonMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreTimeSaleBensonServiceImpl(StoreTimeSaleBensonMapper storeTimeSaleBensonMapper) {
        this.storeTimeSaleBensonMapper = storeTimeSaleBensonMapper;
    }

    /** 지정가맹점_시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreTimeSaleBensonList(StoreTimeSaleBensonVO storeTimeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        storeTimeSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 로그인시 세션 매장코드 세팅
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeTimeSaleBensonVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 조회시간 기본값 세팅
        if (StringUtil.getOrBlank(storeTimeSaleBensonVO.getStartTime()).equals("")) {
            storeTimeSaleBensonVO.setStartTime("00");
        }
        if (StringUtil.getOrBlank(storeTimeSaleBensonVO.getEndTime()).equals("")) {
            storeTimeSaleBensonVO.setEndTime("23");
        }

        return storeTimeSaleBensonMapper.getStoreTimeSaleBensonList(storeTimeSaleBensonVO);
    }
}
