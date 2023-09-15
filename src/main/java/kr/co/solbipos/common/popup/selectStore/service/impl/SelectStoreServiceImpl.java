package kr.co.solbipos.common.popup.selectStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreService;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectStoreServiceImpl.java
 * @Description : (공통) 매장 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("selectStoreServiceImpl")
@Transactional
public class SelectStoreServiceImpl implements SelectStoreService {
    private final SelectStoreMapper selectStoreMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectStoreServiceImpl(SelectStoreMapper selectStoreMapper) { this.selectStoreMapper = selectStoreMapper; }

    /** 매장 공통 - 매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setEmpNo(sessionInfoVO.getEmpNo());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());
        // 매장브랜드가 '전체' 일때
        if (selectStoreVO.getStoreHqBrandCd() == "" || selectStoreVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = selectStoreVO.getUserBrands().split(",");
            selectStoreVO.setUserBrandList(userBrandList);
        }

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = selectStoreMapper.getSelectStoreList(selectStoreVO);
        }

        return resultList;
    }
}