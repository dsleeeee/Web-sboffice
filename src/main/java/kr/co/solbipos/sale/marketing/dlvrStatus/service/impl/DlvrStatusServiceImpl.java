package kr.co.solbipos.sale.marketing.dlvrStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.dlvrStatus.service.DlvrStatusService;
import kr.co.solbipos.sale.marketing.dlvrStatus.service.DlvrStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : DlvrStatusServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 배달비
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("dlvrStatusService")
@Transactional
public class DlvrStatusServiceImpl implements DlvrStatusService {

    private final DlvrStatusMapper dlvrStatusMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public DlvrStatusServiceImpl(DlvrStatusMapper dlvrStatusMapper, PopupMapper popupMapper) {
        this.dlvrStatusMapper = dlvrStatusMapper;
        this.popupMapper = popupMapper;
    }

    /** 배달비 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrStatusList(DlvrStatusVO dlvrStatusVO, SessionInfoVO sessionInfoVO) {

        dlvrStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(dlvrStatusVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dlvrStatusVO.getStoreCd(), 3900));
            dlvrStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dlvrStatusMapper.getDlvrStatusList(dlvrStatusVO);
    }
}
