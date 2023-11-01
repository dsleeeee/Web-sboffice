package kr.co.solbipos.membr.anals.prepaidDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.prepaidDtl.service.PrepaidDtlService;
import kr.co.solbipos.membr.anals.prepaidDtl.service.PrepaidDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PrepaidDtlServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 선불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.27  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prepaidDtlService")
@Transactional
public class PrepaidDtlServiceImpl implements PrepaidDtlService {

    private final PrepaidDtlMapper mapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public PrepaidDtlServiceImpl(PrepaidDtlMapper mapper, PopupMapper popupMapper) {
        this.mapper = mapper;
        this.popupMapper = popupMapper;
    }

    /** 선불 회원 충전, 사용 내역 상세 */
    @Override
    public List<DefaultMap<Object>> getPrepaidDtlMemberList(PrepaidDtlVO prepaidDtlVO,
                                                            SessionInfoVO sessionInfoVO) {

        prepaidDtlVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        prepaidDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prepaidDtlVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prepaidDtlVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prepaidDtlVO.getStoreCds(), 3900));
            prepaidDtlVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mapper.getPrepaidDtlMemberList(prepaidDtlVO);
    }
}
