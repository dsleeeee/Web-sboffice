package kr.co.solbipos.sale.status.payTemporary.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryService;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PayTemporaryServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 가승인-상품권결제차액
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("payTemporaryService")
public class PayTemporaryServiceImpl implements PayTemporaryService {
    private final PayTemporaryMapper payTemporaryMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public PayTemporaryServiceImpl(PayTemporaryMapper payTemporaryMapper, PopupMapper popupMapper) {
        this.payTemporaryMapper = payTemporaryMapper;
        this.popupMapper = popupMapper;
    }

    /** 가승인-상품권결제차액 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getPayTemporaryList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setEmpNo(sessionInfoVO.getEmpNo());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payTemporaryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payTemporaryVO.getStoreCds(), 3900));
            payTemporaryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryList(payTemporaryVO);
    }

    /** 가승인-상품권결제차액 탭 - 엑셀 다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getPayTemporaryExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setEmpNo(sessionInfoVO.getEmpNo());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payTemporaryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payTemporaryVO.getStoreCds(), 3900));
            payTemporaryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryExcelList(payTemporaryVO);
    }

    /** 가승인-상품권결제차액 탭 - 팝업 상세 조회 */
    @Override
    public List<DefaultMap<String>> getPayTemporaryDtlList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            payTemporaryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return payTemporaryMapper.getPayTemporaryDtlList(payTemporaryVO);
    }

    /** 가승인-상품권결제차액 상세내역 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPayTemporaryGiftList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {

        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payTemporaryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payTemporaryVO.getStoreCds(), 3900));
            payTemporaryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryGiftList(payTemporaryVO);
    }

    /** 가승인-상품권결제차액 상세내역 탭 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getPayTemporaryGiftExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {

        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payTemporaryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payTemporaryVO.getStoreCds(), 3900));
            payTemporaryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryGiftExcelList(payTemporaryVO);
    }

}