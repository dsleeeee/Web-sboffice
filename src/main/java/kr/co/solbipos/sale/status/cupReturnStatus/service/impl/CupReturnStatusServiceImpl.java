package kr.co.solbipos.sale.status.cupReturnStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.cupReturnStatus.service.CupReturnStatusService;
import kr.co.solbipos.sale.status.cupReturnStatus.service.CupReturnStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : CupReturnStatusServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 컵보증금회수현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cupReturnStatusService")
@Transactional
public class CupReturnStatusServiceImpl implements CupReturnStatusService {

    private final CupReturnStatusMapper cupReturnStatusMapper;

    @Autowired
    public CupReturnStatusServiceImpl(CupReturnStatusMapper cupReturnStatusMapper) {
        this.cupReturnStatusMapper = cupReturnStatusMapper;
    }

    /** 컵보증금회수현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getCupReturnStatusList(CupReturnStatusVO cupReturnStatusVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            cupReturnStatusVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return cupReturnStatusMapper.getCupReturnStatusList(cupReturnStatusVO);
    }
}
