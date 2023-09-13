package kr.co.solbipos.common.popup.selectSdselGrp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectSdselGrp.service.SelectSdselGrpService;
import kr.co.solbipos.common.popup.selectSdselGrp.service.SelectSdselGrpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectSdselGrpServiceImpl.java
 * @Description : (공통) 선택그룹 팝업
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
@Service("selectSdselGrpService")
@Transactional
public class SelectSdselGrpServiceImpl implements SelectSdselGrpService {
    private final SelectSdselGrpMapper selectSdselGrpMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectSdselGrpServiceImpl(SelectSdselGrpMapper selectSdselGrpMapper) { this.selectSdselGrpMapper = selectSdselGrpMapper; }

    /** 선택그룹 공통 - 선택그룹 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectSdselGrpList(SelectSdselGrpVO selectSdselGrpVO, SessionInfoVO sessionInfoVO) {

        selectSdselGrpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = selectSdselGrpMapper.getSelectSdselGrpList(selectSdselGrpVO);
        }

        return resultList;
    }
}