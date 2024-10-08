package kr.co.solbipos.base.prod.sideMenuStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreService;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SideMenuStoreServiceImpl(SideMenuStoreMapper sideMenuStoreMapper, PopupMapper popupMapper) {
        this.sideMenuStoreMapper = sideMenuStoreMapper;
        this.popupMapper = popupMapper;
    }

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
            sideMenuStoreVO.setRegDt(currentDt);
            sideMenuStoreVO.setRegId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuStoreVO.getRegYn().equals("Y")) {
                procCnt = sideMenuStoreMapper.getSideMenuClassStoreSaveMerge(sideMenuStoreVO);

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
        if(!StringUtil.getOrBlank(sideMenuStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(sideMenuStoreVO.getStoreCds(), 3900));
            sideMenuStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

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
            sideMenuStoreVO.setRegDt(currentDt);
            sideMenuStoreVO.setRegId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuStoreVO.getRegYn().equals("Y")) {
                procCnt = sideMenuStoreMapper.getSideMenuProdStoreSaveMerge(sideMenuStoreVO);

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
        if(!StringUtil.getOrBlank(sideMenuStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(sideMenuStoreVO.getStoreCds(), 3900));
            sideMenuStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return sideMenuStoreMapper.getSideMenuProdList(sideMenuStoreVO);
    }

    /** 선택분류(적용매장) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuClassRegStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuStoreMapper.getSideMenuClassRegStoreList(sideMenuStoreVO);
    }

    /** 선택분류(적용매장) 탭 - 저장 */
    @Override
    public int getSideMenuClassRegStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setModDt(currentDt);
            sideMenuStoreVO.setModId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = sideMenuStoreMapper.getSideMenuClassRegStoreSaveUpdate(sideMenuStoreVO);
        }

        return procCnt;
    }

    /** 선택분류(적용매장) 탭 - 선택분류 적용매장 전체 삭제 */
    @Override
    public int getSideMenuClassRegStoreDeleteAll(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 적용매장구분이 변경된 경우만
            if(!sideMenuStoreVO.getRegStoreFg().equals(CmmUtil.nvl(sideMenuStoreVO.getOldRegStoreFg(), "0"))) {
                procCnt = sideMenuStoreMapper.getSideMenuClassRegStoreDeleteAll(sideMenuStoreVO);
            }
        }

        return procCnt;
    }

    /** 선택상품(적용매장) 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuProdRegStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO) {

        sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
        if (sideMenuStoreVO.getProdHqBrandCd() == "" || sideMenuStoreVO.getProdHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            if (sideMenuStoreVO.getUserProdBrands() != null && !"".equals(sideMenuStoreVO.getUserProdBrands())) {
                String[] userBrandList = sideMenuStoreVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    sideMenuStoreVO.setUserProdBrandList(userBrandList);
                }
            }
        }

        return sideMenuStoreMapper.getSideMenuProdRegStoreList(sideMenuStoreVO);
    }

    /** 선택상품(적용매장) 탭 - 저장 */
    @Override
    public int getSideMenuProdRegStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setModDt(currentDt);
            sideMenuStoreVO.setModId(sessionInfoVO.getUserId());

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = sideMenuStoreMapper.getSideMenuProdRegStoreSaveUpdate(sideMenuStoreVO);
        }

        return procCnt;
    }

    /** 선택상품(적용매장) 탭 - 선택상품 적용매장 전체 삭제 */
    @Override
    public int getSideMenuProdRegStoreDeleteAll(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SideMenuStoreVO sideMenuStoreVO : sideMenuStoreVOs) {

            sideMenuStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 적용매장구분이 변경된 경우만
            if(!sideMenuStoreVO.getRegStoreFg().equals(CmmUtil.nvl(sideMenuStoreVO.getOldRegStoreFg(), "0"))) {
                procCnt = sideMenuStoreMapper.getSideMenuProdRegStoreDeleteAll(sideMenuStoreVO);
            }
        }

        return procCnt;
    }
}