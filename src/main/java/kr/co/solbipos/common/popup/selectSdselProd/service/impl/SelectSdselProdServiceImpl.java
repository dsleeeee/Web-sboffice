package kr.co.solbipos.common.popup.selectSdselProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectSdselProd.service.SelectSdselProdService;
import kr.co.solbipos.common.popup.selectSdselProd.service.SelectSdselProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectSdselProdServiceImpl.java
 * @Description : (공통) 선택상품 팝업
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
@Service("selectSdselProdService")
@Transactional
public class SelectSdselProdServiceImpl implements SelectSdselProdService {
    private final SelectSdselProdMapper selectSdselProdMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectSdselProdServiceImpl(SelectSdselProdMapper selectSdselProdMapper) { this.selectSdselProdMapper = selectSdselProdMapper; }

    /** 선택상품 공통 - 선택상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectSdselProdList(SelectSdselProdVO selectSdselProdVO, SessionInfoVO sessionInfoVO) {

        selectSdselProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = selectSdselProdMapper.getSelectSdselProdList(selectSdselProdVO);
        }

        return resultList;
    }
}