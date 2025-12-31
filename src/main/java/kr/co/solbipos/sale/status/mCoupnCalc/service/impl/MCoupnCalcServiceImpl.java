package kr.co.solbipos.sale.status.mCoupnCalc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.mCoupnCalc.service.MCoupnCalcService;
import kr.co.solbipos.sale.status.mCoupnCalc.service.MCoupnCalcVO;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MCoupnCalcServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.19  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mCoupnCalcService")
@Transactional
public class MCoupnCalcServiceImpl implements MCoupnCalcService {

    private final MCoupnCalcMapper mCoupnCalcMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MCoupnCalcServiceImpl(MCoupnCalcMapper mCoupnCalcMapper, PopupMapper popupMapper) {
        this.mCoupnCalcMapper = mCoupnCalcMapper;
        this.popupMapper = popupMapper;
    }

    /** 모바일쿠폰 정산 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnCalcList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO) {

        mCoupnCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mCoupnCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnCalcVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(mCoupnCalcVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mCoupnCalcVO.getStoreCds(), 3900));
            mCoupnCalcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (mCoupnCalcVO.getStoreHqBrandCd() == "" || mCoupnCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = mCoupnCalcVO.getUserBrands().split(",");
                mCoupnCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(mCoupnCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(mCoupnCalcVO.getPosNos(), 3900));
            mCoupnCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }


        return mCoupnCalcMapper.getMCoupnCalcList(mCoupnCalcVO);
    }

    /** 모바일쿠폰 정산 조회조건 엑셀다운로드 */
    @Override
    public List<DefaultMap<Object>> getMCoupnCalcExcelList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO) {

        mCoupnCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mCoupnCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnCalcVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(mCoupnCalcVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mCoupnCalcVO.getStoreCds(), 3900));
            mCoupnCalcVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (mCoupnCalcVO.getStoreHqBrandCd() == "" || mCoupnCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = mCoupnCalcVO.getUserBrands().split(",");
                mCoupnCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(mCoupnCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(mCoupnCalcVO.getPosNos(), 3900));
            mCoupnCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }

        return mCoupnCalcMapper.getMCoupnCalcExcelList(mCoupnCalcVO);
    }

    /** 모바일쿠폰 정산 상세화면 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnCalcDtlList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO) {

        mCoupnCalcVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mCoupnCalcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnCalcVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mCoupnCalcVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (mCoupnCalcVO.getStoreHqBrandCd() == "" || mCoupnCalcVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = mCoupnCalcVO.getUserBrands().split(",");
                mCoupnCalcVO.setUserBrandList(userBrandList);
            }
        }

        // 포스번호 셋팅
        if(!StringUtil.getOrBlank(mCoupnCalcVO.getPosNos()).equals("")) {
            ProdPosVO prodPosVO = new ProdPosVO();
            prodPosVO.setArrSplitPosNo(CmmUtil.splitText(mCoupnCalcVO.getPosNos(), 3900));
            mCoupnCalcVO.setPosNoQuery(popupMapper.getSearchMultiPosRtn(prodPosVO));
        }

        return mCoupnCalcMapper.getMCoupnCalcDtlList(mCoupnCalcVO);
    }

    /** 모바일쿠폰 조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getMCoupnComboList(SessionInfoVO sessionInfoVO) {

        return mCoupnCalcMapper.getMCoupnComboList(sessionInfoVO);
    }
}
