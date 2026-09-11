package kr.co.solbipos.sale.benson.dayCashSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.dayCashSaleBenson.service.DayCashSaleBensonService;
import kr.co.solbipos.sale.benson.dayCashSaleBenson.service.DayCashSaleBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayCashSaleBensonServiceImpl.java
 * @Description : 벤슨 > 매출현황2 > 일별(현금)현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("dayCashSaleBensonService")
@Transactional
public class DayCashSaleBensonServiceImpl implements DayCashSaleBensonService {
    private final DayCashSaleBensonMapper dayCashSaleBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayCashSaleBensonServiceImpl(DayCashSaleBensonMapper dayCashSaleBensonMapper, PopupMapper popupMapper) {
        this.dayCashSaleBensonMapper = dayCashSaleBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별(현금)현황 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(DayCashSaleBensonVO dayCashSaleBensonVO, SessionInfoVO sessionInfoVO) {
        dayCashSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dayCashSaleBensonMapper.getPayColList(dayCashSaleBensonVO);
    }

    /** 일별(현금)현황 - 할인 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(DayCashSaleBensonVO dayCashSaleBensonVO, SessionInfoVO sessionInfoVO) {
        return dayCashSaleBensonMapper.getDcColList(dayCashSaleBensonVO);
    }

    /** 일별(현금)현황 - 객수 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGuestColList(DayCashSaleBensonVO dayCashSaleBensonVO, SessionInfoVO sessionInfoVO) {

        dayCashSaleBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayCashSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayCashSaleBensonVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dayCashSaleBensonMapper.getGuestColList(dayCashSaleBensonVO);
    }

    /** 일별(현금)현황 - 일별종합(현금) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayCashTotalBensonList(DayCashSaleBensonVO dayCashSaleBensonVO, SessionInfoVO sessionInfoVO) {

        dayCashSaleBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayCashSaleBensonVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayCashSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayCashSaleBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayCashSaleBensonVO.getStoreCd(), 3900));
            dayCashSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        String payCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayCashSaleBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외 + payCd길이가 1인거 = 현금(.. 인애들 제외
            if(! (arrPayCol[i].length() == 1) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayCashSaleBensonVO.setPivotPayCol(pivotPayCol);
        dayCashSaleBensonVO.setArrPayCol(payCol.split(","));

        return dayCashSaleBensonMapper.getDayCashTotalBensonList(dayCashSaleBensonVO);
    }
}
