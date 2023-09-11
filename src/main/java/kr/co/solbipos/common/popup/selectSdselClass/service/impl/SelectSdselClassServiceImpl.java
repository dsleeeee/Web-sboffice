package kr.co.solbipos.common.popup.selectSdselClass.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectSdselClass.service.SelectSdselClassService;
import kr.co.solbipos.common.popup.selectSdselClass.service.SelectSdselClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectSdselClassServiceImpl.java
 * @Description : (공통) 선택분류 팝업
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
@Service("selectSdselClassService")
@Transactional
public class SelectSdselClassServiceImpl implements SelectSdselClassService {
    private final SelectSdselClassMapper selectSdselClassMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectSdselClassServiceImpl(SelectSdselClassMapper selectSdselClassMapper) { this.selectSdselClassMapper = selectSdselClassMapper; }

    /** 선택분류 공통 - 선택분류 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectSdselClassList(SelectSdselClassVO selectSdselClassVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        selectSdselClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = selectSdselClassMapper.selectSdselClassList(selectSdselClassVO);
        }

        return resultList;
    }
}