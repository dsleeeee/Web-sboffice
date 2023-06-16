package kr.co.solbipos.base.prod.sideMenuStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreService;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SideMenuStoreServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 매장별사이드관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideMenuStoreService")
@Transactional
public class SideMenuStoreServiceImpl implements SideMenuStoreService {
    private final SideMenuStoreMapper sideMenuStoreMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SideMenuStoreServiceImpl(SideMenuStoreMapper sideMenuStoreMapper) { this.sideMenuStoreMapper = sideMenuStoreMapper; }

    /** 선택분류(매장별) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuClassStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuStoreMapper.getSideMenuClassStoreList(sideMenuStoreVO);
    }

    /** 선택분류(매장별) 탭 - 저장 */
    @Override
    public int getSideMenuClassStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setModDt(currentDt);
            sideMenuStoreVO.setModId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuStoreVO.getRegYn().equals("Y")) {
                sideMenuStoreVO.setRegDt(currentDt);
                sideMenuStoreVO.setRegId(sessionInfoVO.getUserId());

                procCnt = sideMenuStoreMapper.getSideMenuClassStoreSaveInsert(sideMenuStoreVO);

            } else if (sideMenuStoreVO.getRegYn().equals("N")) {
                procCnt = sideMenuStoreMapper.getSideMenuClassStoreSaveDelete(sideMenuStoreVO);
            }
        }

        return procCnt;
    }

    /** 선택분류(선택분류별) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuClassList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = sideMenuStoreVO.getStoreCds().split(",");
        sideMenuStoreVO.setStoreCdList(storeCds);

        return sideMenuStoreMapper.getSideMenuClassList(sideMenuStoreVO);
    }

    /** 선택상품(매장별) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuProdStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuStoreMapper.getSideMenuProdStoreList(sideMenuStoreVO);
    }

    /** 선택상품(매장별) 탭 - 저장 */
    @Override
    public int getSideMenuProdStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setModDt(currentDt);
            sideMenuStoreVO.setModId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuStoreVO.getRegYn().equals("Y")) {
                sideMenuStoreVO.setRegDt(currentDt);
                sideMenuStoreVO.setRegId(sessionInfoVO.getUserId());

                procCnt = sideMenuStoreMapper.getSideMenuProdStoreSaveInsert(sideMenuStoreVO);

            } else if (sideMenuStoreVO.getRegYn().equals("N")) {
                procCnt = sideMenuStoreMapper.getSideMenuProdStoreSaveDelete(sideMenuStoreVO);
            }
        }

        return procCnt;
    }

    /** 선택상품(선택상품별) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuProdList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = sideMenuStoreVO.getStoreCds().split(",");
        sideMenuStoreVO.setStoreCdList(storeCds);

        return sideMenuStoreMapper.getSideMenuProdList(sideMenuStoreVO);
    }
}