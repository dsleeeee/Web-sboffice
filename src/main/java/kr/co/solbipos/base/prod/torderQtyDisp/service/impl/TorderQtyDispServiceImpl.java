package kr.co.solbipos.base.prod.torderQtyDisp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.torderQtyDisp.service.TorderQtyDispService;
import kr.co.solbipos.base.prod.torderQtyDisp.service.TorderQtyDispVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TorderQtyDispServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > T오더수량변경표시관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("torderQtyDispService")
@Transactional
public class TorderQtyDispServiceImpl implements TorderQtyDispService {
    private final TorderQtyDispMapper torderQtyDispMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TorderQtyDispServiceImpl(TorderQtyDispMapper torderQtyDispMapper, PopupMapper popupMapper) {
        this.torderQtyDispMapper = torderQtyDispMapper;
        this.popupMapper = popupMapper;
    }

    /** T오더수량변경표시관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTorderQtyDispList(TorderQtyDispVO torderQtyDispVO, SessionInfoVO sessionInfoVO) {

        torderQtyDispVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            torderQtyDispVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        torderQtyDispVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(torderQtyDispVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(torderQtyDispVO.getStoreCds(), 3900));
            torderQtyDispVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (torderQtyDispVO.getProdCds() != null && !"".equals(torderQtyDispVO.getProdCds())) {
            String[] prodCdList = torderQtyDispVO.getProdCds().split(",");
            torderQtyDispVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (torderQtyDispVO.getStoreHqBrandCd() == "" || torderQtyDispVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (torderQtyDispVO.getUserBrands() != null && !"".equals(torderQtyDispVO.getUserBrands())) {
                    String[] userBrandList = torderQtyDispVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        torderQtyDispVO.setUserBrandList(userBrandList);
                    }
                }
            }
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (torderQtyDispVO.getProdHqBrandCd() == "" || torderQtyDispVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (torderQtyDispVO.getProdBrands() != null && !"".equals(torderQtyDispVO.getProdBrands())) {
                    String[] prodBrandList = torderQtyDispVO.getProdBrands().split(",");
                    if (prodBrandList.length > 0) {
                        torderQtyDispVO.setProdBrandList(prodBrandList);
                    }
                }
            }
        }

        return torderQtyDispMapper.getTorderQtyDispList(torderQtyDispVO);
    }

    /** T오더수량변경표시관리 - 저장 */
    @Override
    public int getTorderQtyDispSave(TorderQtyDispVO[] torderQtyDispVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(TorderQtyDispVO torderQtyDispVO : torderQtyDispVOs) {

            torderQtyDispVO.setRegDt(currentDt);
            torderQtyDispVO.setRegId(sessionInfoVO.getUserId());
            torderQtyDispVO.setModDt(currentDt);
            torderQtyDispVO.setModId(sessionInfoVO.getUserId());

            procCnt = torderQtyDispMapper.getTorderQtyDispSaveMerge(torderQtyDispVO);
        }

        return procCnt;
    }
}