package kr.co.solbipos.sale.anals.salePerfCompareBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.salePerfCompareBenson.service.SalePerfCompareBensonService;
import kr.co.solbipos.sale.anals.salePerfCompareBenson.service.SalePerfCompareBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : SalePerfCompareBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 대비기간 매출실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePerfCompareBensonService")
@Transactional
public class SalePerfCompareBensonServiceImpl implements SalePerfCompareBensonService {

    private final SalePerfCompareBensonMapper salePerfCompareBensonMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SalePerfCompareBensonServiceImpl(SalePerfCompareBensonMapper salePerfCompareBensonMapper, PopupMapper popupMapper) {
        this.salePerfCompareBensonMapper = salePerfCompareBensonMapper;
        this.popupMapper = popupMapper;
    }


    /** 대비기간 매출실적 - 전체점포탭 조회 */
    @Override
    public List<DefaultMap<Object>> getSalePerfCompareBensonList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareBensonVO.getEndDate() == null || salePerfCompareBensonVO.getEndDate().equals("")){
            salePerfCompareBensonVO.setEndDate(salePerfCompareBensonVO.getStartDate());
            salePerfCompareBensonVO.setCompEndDate(salePerfCompareBensonVO.getCompStartDate());
        }

        return salePerfCompareBensonMapper.getSalePerfCompareBensonList(salePerfCompareBensonVO);
    }

    /** 대비기간 매출실적 - 전체점포탭 채널별 조회 */
    @Override
    public List<DefaultMap<Object>> getSalePerfCompareBensonDtlList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareBensonVO.getEndDate() == null || salePerfCompareBensonVO.getEndDate().equals("")){
            salePerfCompareBensonVO.setEndDate(salePerfCompareBensonVO.getStartDate());
            salePerfCompareBensonVO.setCompEndDate(salePerfCompareBensonVO.getCompStartDate());
        }
        return salePerfCompareBensonMapper.getSalePerfCompareBensonDtlList(salePerfCompareBensonVO);
    }

    /** 대비기간 매출실적 - 선택점포탭 조회 */
    @Override
    public List<DefaultMap<Object>> getSalePerfCompareBensonStoreList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareBensonVO.getEndDate() == null || salePerfCompareBensonVO.getEndDate().equals("")){
            salePerfCompareBensonVO.setEndDate(salePerfCompareBensonVO.getStartDate());
            salePerfCompareBensonVO.setCompEndDate(salePerfCompareBensonVO.getCompStartDate());
        }

        if(!StringUtil.getOrBlank(salePerfCompareBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(salePerfCompareBensonVO.getStoreCd(), 3900));
            salePerfCompareBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return salePerfCompareBensonMapper.getSalePerfCompareBensonStoreList(salePerfCompareBensonVO);
    }
}
