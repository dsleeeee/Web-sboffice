package kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.InStockReportByAcquireService;
import kr.co.solbipos.kookmin.acquire.inStockReportByAcquire.service.InStockReportByAcquireVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : InStockReportByAcquireServiceImpl.java
 * @Description : 국민대 > 매입관리 > 매입처별 입고내역서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("InStockReportByAcquireService")
@Transactional
public class InStockReportByAcquireServiceImpl implements InStockReportByAcquireService {

    private final InStockReportByAcquireMapper inStockReportByAcquireMapper;

    public InStockReportByAcquireServiceImpl(InStockReportByAcquireMapper inStockReportByAcquireMapper) {
        this.inStockReportByAcquireMapper = inStockReportByAcquireMapper;
    }

    /** 매입처별 입고 내역서 - 조회*/
    @Override
    public List<DefaultMap<String>> getInStockReportByAcquireList(InStockReportByAcquireVO inStockReportByAcquireVO, SessionInfoVO sessionInfoVO) {

        inStockReportByAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return inStockReportByAcquireMapper.getInStockReportByAcquireList(inStockReportByAcquireVO);
    }
}
