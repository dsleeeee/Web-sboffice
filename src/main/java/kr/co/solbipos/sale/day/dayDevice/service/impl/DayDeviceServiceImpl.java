package kr.co.solbipos.sale.day.dayDevice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayDevice.service.DayDeviceService;
import kr.co.solbipos.sale.day.dayDevice.service.DayDeviceVO;
import kr.co.solbipos.sale.day.dayDevice.service.impl.DayDeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayDeviceServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(기기별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayDeviceService")
@Transactional
public class DayDeviceServiceImpl implements DayDeviceService {
    private final DayDeviceMapper dayDeviceMapper;
    private final PopupMapper popupMapper;

    public DayDeviceServiceImpl(DayDeviceMapper dayDeviceMapper, PopupMapper popupMapper) {
        this.dayDeviceMapper = dayDeviceMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayDeviceList(DayDeviceVO dayDeviceVO, SessionInfoVO sessionInfoVO) {

        dayDeviceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayDeviceVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayDeviceVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayDeviceVO.getStoreCds(), 3900));
            dayDeviceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayDeviceMapper.getDayDeviceList(dayDeviceVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayDeviceExcelList(DayDeviceVO dayDeviceVO, SessionInfoVO sessionInfoVO) {

        dayDeviceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayDeviceVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayDeviceVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayDeviceVO.getStoreCds(), 3900));
            dayDeviceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayDeviceMapper.getDayDeviceExcelList(dayDeviceVO);
    }
}