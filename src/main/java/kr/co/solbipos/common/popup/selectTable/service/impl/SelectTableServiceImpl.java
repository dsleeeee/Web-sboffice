package kr.co.solbipos.common.popup.selectTable.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectTable.service.SelectTableService;
import kr.co.solbipos.common.popup.selectTable.service.SelectTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : SelectTableServiceImpl.java
 * @Description : (공통) 테이블 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("selectTableService")
@Transactional
public class SelectTableServiceImpl implements SelectTableService {
    private final SelectTableMapper selectTableMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectTableServiceImpl(SelectTableMapper selectTableMapper) {
        this.selectTableMapper = selectTableMapper;
    }

    /** 테이블 공통 - 테이블 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectTableList(SelectTableVO selectTableVO, SessionInfoVO sessionInfoVO) {

        selectTableVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (selectTableVO.getTableCd() != null && !"".equals(selectTableVO.getTableCd())) {
    		String[] arrTableCd = selectTableVO.getTableCd().split(",");

    		if (arrTableCd.length > 0) {
    			if (arrTableCd[0] != null && !"".equals(arrTableCd[0])) {
                    selectTableVO.setArrTableCd(arrTableCd);
    			}
    		}
    	} else {
    		String[] arrStoreCd = selectTableVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    selectTableVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

        if(!StringUtil.getOrBlank(selectTableVO.getStoreCd()).equals("")) {
            selectTableVO.setArrStoreCd(selectTableVO.getStoreCd().split(","));
        }

        return selectTableMapper.getSelectTableList(selectTableVO);
    }
}