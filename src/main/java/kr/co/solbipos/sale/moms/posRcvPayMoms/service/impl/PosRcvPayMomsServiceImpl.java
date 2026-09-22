package kr.co.solbipos.sale.moms.posRcvPayMoms.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.BizException;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.moms.posRcvPayMoms.service.PosRcvPayMomsService;
import kr.co.solbipos.sale.moms.posRcvPayMoms.service.PosRcvPayMomsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PosRcvPayMomsServiceImpl.java
 * @Description : 맘스터치 > 정산 > POS내역수신(결제)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("posRcvPayMomsService")
public class PosRcvPayMomsServiceImpl implements PosRcvPayMomsService {

    private final PosRcvPayMomsMapper posRcvPayMomsMapper;

    @Autowired
    public PosRcvPayMomsServiceImpl(PosRcvPayMomsMapper posRcvPayMomsMapper) {
        this.posRcvPayMomsMapper = posRcvPayMomsMapper;
    }

    /** POS내역수신(결제) - 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getPosRcvPayMomsList(PosRcvPayMomsVO posRcvPayMomsVO, SessionInfoVO sessionInfoVO) {

        // 필수값 확인
        if (posRcvPayMomsVO.getSaleDate() == null || posRcvPayMomsVO.getSaleDate().trim().isEmpty()
                || posRcvPayMomsVO.getStoreCd() == null || posRcvPayMomsVO.getStoreCd().trim().isEmpty()) {
            throw new BizException(Status.FAIL, "조회일자와 매장코드는 필수입니다.");
        }

        posRcvPayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posRcvPayMomsMapper.getPosRcvPayMomsList(posRcvPayMomsVO);
    }
}
