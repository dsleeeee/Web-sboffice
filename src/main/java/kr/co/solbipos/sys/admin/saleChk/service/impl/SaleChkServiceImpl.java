package kr.co.solbipos.sys.admin.saleChk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.saleChk.service.SaleChkService;
import kr.co.solbipos.sys.admin.saleChk.service.SaleChkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : SaleChkServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > 매출점검
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("SaleChkServiceImpl")
@Transactional
public class SaleChkServiceImpl implements SaleChkService {
    private final SaleChkMapper saleChkMapper;

    public SaleChkServiceImpl(SaleChkMapper saleChkMapper) {
        this.saleChkMapper = saleChkMapper;
    }

    /** 매장별 포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getSaleList(SaleChkVO saleChkVO, SessionInfoVO sessionInfoVO) {
        return saleChkMapper.getSaleList(saleChkVO);
    }

    /** RESULT_MSG */
    @Override
    public int updateResultMsg(SaleChkVO[] saleChkVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;

        for(SaleChkVO saleChkVO : saleChkVOs) {

            resultCnt += saleChkMapper.updateResultMsg(saleChkVO);
        }

        return resultCnt;
    }
}
