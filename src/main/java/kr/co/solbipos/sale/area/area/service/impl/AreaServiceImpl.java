package kr.co.solbipos.sale.area.area.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.area.area.service.AreaService;
import kr.co.solbipos.sale.area.area.service.AreaVO;
import kr.co.solbipos.sale.area.area.service.impl.AreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : AreaServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 지역별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("areaService")
@Transactional
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapper;
    private final PopupMapper popupMapper;

    public AreaServiceImpl(AreaMapper areaMapper, PopupMapper popupMapper) {
        this.areaMapper = areaMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getAreaList(AreaVO areaVO, SessionInfoVO sessionInfoVO) {

        areaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            areaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(areaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(areaVO.getStoreCds(), 3900));
            areaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return areaMapper.getAreaList(areaVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getAreaExcelList(AreaVO areaVO, SessionInfoVO sessionInfoVO) {

        areaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            areaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(areaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(areaVO.getStoreCds(), 3900));
            areaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return areaMapper.getAreaExcelList(areaVO);
    }
}