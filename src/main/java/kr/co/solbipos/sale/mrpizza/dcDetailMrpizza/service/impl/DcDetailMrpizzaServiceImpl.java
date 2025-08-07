package kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.DcDetailMrpizzaService;
import kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.DcDetailMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DcDetailMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 할인세부내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("dcDetailMrpizzaService")
@Transactional
public class DcDetailMrpizzaServiceImpl implements DcDetailMrpizzaService {

    private final DcDetailMrpizzaMapper dcDetailMrpizzaMapper;
    private final PopupMapper popupMapper;

    public DcDetailMrpizzaServiceImpl(DcDetailMrpizzaMapper dcDetailMrpizzaMapper, PopupMapper popupMapper) {
        this.dcDetailMrpizzaMapper = dcDetailMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /** 할인세부내역 - 전체점포 탭 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDcDetailMrpizzaAllStoreList(DcDetailMrpizzaVO dcDetailMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dcDetailMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return dcDetailMrpizzaMapper.getDcDetailMrpizzaAllStoreList(dcDetailMrpizzaVO);
    }

    /** 할인세부내역 - 선택점포 탭 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDcDetailMrpizzaSelectStoreList(DcDetailMrpizzaVO dcDetailMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dcDetailMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dcDetailMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcDetailMrpizzaVO.getStoreCds(), 3900));
            dcDetailMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dcDetailMrpizzaMapper.getDcDetailMrpizzaSelectStoreList(dcDetailMrpizzaVO);
    }

    /** 할인세부내역 - 할인구분 탭 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDcDetailMrpizzaDcTypeList(DcDetailMrpizzaVO dcDetailMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dcDetailMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dcDetailMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dcDetailMrpizzaVO.getStoreCds(), 3900));
            dcDetailMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dcDetailMrpizzaMapper.getDcDetailMrpizzaDcTypeList(dcDetailMrpizzaVO);
    }
}
