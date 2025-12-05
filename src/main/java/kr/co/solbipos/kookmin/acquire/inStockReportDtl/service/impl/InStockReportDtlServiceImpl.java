package kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.InStockReportDtlService;
import kr.co.solbipos.kookmin.acquire.inStockReportDtl.service.InStockReportDtlVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
/**
 * @Class Name  : InStockReportDtlServiceImpl.java
 * @Description : 국민대 > 매입관리 > 매입처별 상세매입내역(상품별)
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
@Service("InStockReportDtlService")
@Transactional
public class InStockReportDtlServiceImpl implements InStockReportDtlService {

    private final InStockReportDtlMapper inStockReportDtlMapper;

    public InStockReportDtlServiceImpl(InStockReportDtlMapper inStockReportDtlMapper) {
        this.inStockReportDtlMapper = inStockReportDtlMapper;
    }

    /** 매입처별 상세내역서(상품별) - 조회 */
    @Override
    public List<DefaultMap<String>> getInStockReportDtlList(InStockReportDtlVO inStockReportDtlVO, SessionInfoVO sessionInfoVO) {
        String currentDay = currentDateString();

        inStockReportDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        inStockReportDtlVO.setToday(currentDay);

        return inStockReportDtlMapper.getInStockReportDtlList(inStockReportDtlVO);
    }
}
