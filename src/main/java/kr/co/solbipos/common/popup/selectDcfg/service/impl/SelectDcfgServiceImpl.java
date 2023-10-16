package kr.co.solbipos.common.popup.selectDcfg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.solbipos.common.popup.selectDcfg.service.SelectDcfgService;
import kr.co.solbipos.common.popup.selectDcfg.service.SelectDcfgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectDcfgServiceImpl.java
 * @Description : (공통) 할인구분 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("selectDcfgService")
@Transactional
public class SelectDcfgServiceImpl implements SelectDcfgService {
    private final SelectDcfgMapper selectDcfgMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectDcfgServiceImpl(SelectDcfgMapper selectDcfgMapper, PopupMapper popupMapper) {
        this.selectDcfgMapper = selectDcfgMapper;
        this.popupMapper = popupMapper;
    }

    /** 할인구분 공통 - 할인구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectDcfgList(SelectDcfgVO selectDcfgVO, SessionInfoVO sessionInfoVO) {

        selectDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(selectDcfgVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(selectDcfgVO.getStoreCd(), 3900));
            selectDcfgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if(!StringUtil.getOrBlank(selectDcfgVO.getDcCd()).equals("")) {
            selectDcfgVO.setArrDcCd(selectDcfgVO.getDcCd().split(","));
        }

        return selectDcfgMapper.getSelectDcfgList(selectDcfgVO);
    }
}