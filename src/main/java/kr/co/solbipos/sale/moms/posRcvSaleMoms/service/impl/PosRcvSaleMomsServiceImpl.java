package kr.co.solbipos.sale.moms.posRcvSaleMoms.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.BizException;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsService;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PosRcvSaleMomsServiceImpl.java
 * @Description : 맘스터치 > 정산 > POS내역수신(매출)
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
@Service("posRcvSaleMomsService")
public class PosRcvSaleMomsServiceImpl implements PosRcvSaleMomsService {

    private final PosRcvSaleMomsMapper posRcvSaleMomsMapper;

    @Autowired
    public PosRcvSaleMomsServiceImpl(PosRcvSaleMomsMapper posRcvSaleMomsMapper) {
        this.posRcvSaleMomsMapper = posRcvSaleMomsMapper;
    }

    /** POS내역수신(매출) - 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getPosRcvSaleMomsList(PosRcvSaleMomsVO posRcvSaleMomsVO, SessionInfoVO sessionInfoVO) {

        // 필수값 확인
        if (posRcvSaleMomsVO.getSaleDate() == null || posRcvSaleMomsVO.getSaleDate().trim().isEmpty()
                || posRcvSaleMomsVO.getStoreCd() == null || posRcvSaleMomsVO.getStoreCd().trim().isEmpty()) {
            throw new BizException(Status.FAIL, "조회일자와 매장코드는 필수입니다.");
        }

        posRcvSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return posRcvSaleMomsMapper.getPosRcvSaleMomsList(posRcvSaleMomsVO);
    }
}
