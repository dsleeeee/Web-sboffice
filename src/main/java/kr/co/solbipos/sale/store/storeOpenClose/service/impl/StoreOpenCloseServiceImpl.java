package kr.co.solbipos.sale.store.storeOpenClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseService;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseVO;
import kr.co.solbipos.sale.store.storeOpenClose.service.impl.StoreOpenCloseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeOpenCloseService")
@Transactional
public class StoreOpenCloseServiceImpl implements StoreOpenCloseService {

    private final StoreOpenCloseMapper storeOpenCloseMapper;
    private final PopupMapper popupMapper;

    public StoreOpenCloseServiceImpl(StoreOpenCloseMapper storeOpenCloseMapper, PopupMapper popupMapper) {
        this.storeOpenCloseMapper = storeOpenCloseMapper;
        this.popupMapper = popupMapper;
    }

    /** toreOpenClose - 일별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseDayList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseVO.getStoreCds(), 3900));
            storeOpenCloseVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseVO.getStoreHqBrandCd() == "" || storeOpenCloseVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseVO.getUserBrands().split(",");
                storeOpenCloseVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseMapper.getStoreOpenCloseDayList(storeOpenCloseVO);
    }

    /** toreOpenClose - 일별 상세 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseDayDtlList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseVO.getStoreCds(), 3900));
            storeOpenCloseVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseVO.getStoreHqBrandCd() == "" || storeOpenCloseVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseVO.getUserBrands().split(",");
                storeOpenCloseVO.setUserBrandList(userBrandList);
            }
        }

        if(storeOpenCloseVO.getGubun().equals("none")) {    // 미개점
            return storeOpenCloseMapper.getStoreOpenCloseDayDtlNoneList(storeOpenCloseVO);
        } else {    // 개점/마감
            return storeOpenCloseMapper.getStoreOpenCloseDayDtlList(storeOpenCloseVO);
        }
    }

    /** toreOpenClose - 월별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseMonthList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        /*String[] storeCds = storeOpenCloseVO.getStoreCds().split(",");
        storeOpenCloseVO.setStoreCdList(storeCds);*/

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseVO.getStoreHqBrandCd() == "" || storeOpenCloseVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseVO.getUserBrands().split(",");
                storeOpenCloseVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseMapper.getStoreOpenCloseMonthList(storeOpenCloseVO);
    }

    /** toreOpenClose - 월별 상세 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseMonthDtlList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseVO.getStoreCds(), 3900));
            storeOpenCloseVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseVO.getStoreHqBrandCd() == "" || storeOpenCloseVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseVO.getUserBrands().split(",");
                storeOpenCloseVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseMapper.getStoreOpenCloseMonthDtlList(storeOpenCloseVO);
    }
}
