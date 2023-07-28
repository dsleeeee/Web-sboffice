package kr.co.solbipos.base.prod.tableOrderKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.tableOrderKeyMap.service.TableOrderKeyMapService;
import kr.co.solbipos.base.prod.tableOrderKeyMap.service.TableOrderKeyMapVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TableOrderKeyMapServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 테이블오더키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("tableOrderKeyMapService")
@Transactional
public class TableOrderKeyMapServiceImpl implements TableOrderKeyMapService {
    private final TableOrderKeyMapMapper tableOrderKeyMapMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TableOrderKeyMapServiceImpl(TableOrderKeyMapMapper tableOrderKeyMapMapper) { this.tableOrderKeyMapMapper = tableOrderKeyMapMapper; }

    /** 테이블오더키맵 매장적용 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTableOrderKeyMapStoreRegistList(TableOrderKeyMapVO tableOrderKeyMapVO, SessionInfoVO sessionInfoVO) {

        tableOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (tableOrderKeyMapVO.getStoreHqBrandCd() == "" || tableOrderKeyMapVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (tableOrderKeyMapVO.getUserBrands() != null && !"".equals(tableOrderKeyMapVO.getUserBrands())) {
                    String[] userBrandList = tableOrderKeyMapVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        tableOrderKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return tableOrderKeyMapMapper.getTableOrderKeyMapStoreRegistList(tableOrderKeyMapVO);
    }
}