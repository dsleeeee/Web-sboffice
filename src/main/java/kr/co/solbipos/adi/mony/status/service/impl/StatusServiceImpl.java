package kr.co.solbipos.adi.mony.status.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.mony.status.service.StatusService;
import kr.co.solbipos.adi.mony.status.service.StatusVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Class Name : DrawHistServiceImpl.java
 * @Description : 부가서비스 > 금전처리 > 금전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.09  김태수      최초생성
 *
 * @author NHN한국사이버결제 KCP 김태수
 * @since 2018.09.09
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("StatusService")
@Transactional
public class StatusServiceImpl implements StatusService {

    private final StatusMapper statusMapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public StatusServiceImpl(StatusMapper statusMapper, PopupMapper popupMapper) {
        this.statusMapper = statusMapper;
        this.popupMapper = popupMapper;
    }

    /** 금전현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStatus(StatusVO statusVO, SessionInfoVO sessionInfoVO) {

        statusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        statusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            statusVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(statusVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(statusVO.getStoreCd(), 3900));
            statusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return statusMapper.selectStatus(statusVO);
    }

    /** 계정구분에 따른 계정 코드 조회(입금/출금 계정, 매장권한에서만 사용) */
    @Override
    public List<DefaultMap<String>> selectAccntList(StatusVO statusVO, SessionInfoVO sessionInfoVO) {

        statusVO.setStoreCd(sessionInfoVO.getOrgnCd());

        return statusMapper.selectAccntList(statusVO);
    }

}

