package kr.co.solbipos.sale.marketing.salePerfCompare.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.salePerfCompare.service.SalePerfCompareService;
import kr.co.solbipos.sale.marketing.salePerfCompare.service.SalePerfCompareVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : SalePerfCompareServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 매출실적비교
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.08  김유승      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePerfCompareService")
@Transactional
public class SalePerfCompareServiceImpl implements SalePerfCompareService {

    private final SalePerfCompareMapper salePerfCompareMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SalePerfCompareServiceImpl(SalePerfCompareMapper salePerfCompareMapper, PopupMapper popupMapper) {
        this.salePerfCompareMapper = salePerfCompareMapper;
        this.popupMapper = popupMapper;
    }


    @Override
    public List<DefaultMap<Object>> getSalePerfCompareList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareVO.getEndDate() == null || salePerfCompareVO.getEndDate().equals("")){
            salePerfCompareVO.setEndDate(salePerfCompareVO.getStartDate());
            salePerfCompareVO.setCompEndDate(salePerfCompareVO.getCompStartDate());
        }

        return salePerfCompareMapper.getSalePerfCompareList(salePerfCompareVO);
    }

    @Override
    public List<DefaultMap<Object>> getSalePerfCompareDtlList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareVO.getEndDate() == null || salePerfCompareVO.getEndDate().equals("")){
            salePerfCompareVO.setEndDate(salePerfCompareVO.getStartDate());
            salePerfCompareVO.setCompEndDate(salePerfCompareVO.getCompStartDate());
        }
        return salePerfCompareMapper.getSalePerfCompareDtlList(salePerfCompareVO);
    }

    @Override
    public List<DefaultMap<Object>> getSalePerfCompareStoreList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO) {
        salePerfCompareVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // endDate 없으면 startDate로 설정
        if(salePerfCompareVO.getEndDate() == null || salePerfCompareVO.getEndDate().equals("")){
            salePerfCompareVO.setEndDate(salePerfCompareVO.getStartDate());
            salePerfCompareVO.setCompEndDate(salePerfCompareVO.getCompStartDate());
        }

        if(!StringUtil.getOrBlank(salePerfCompareVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(salePerfCompareVO.getStoreCd(), 3900));
            salePerfCompareVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return salePerfCompareMapper.getSalePerfCompareStoreList(salePerfCompareVO);
    }
}
