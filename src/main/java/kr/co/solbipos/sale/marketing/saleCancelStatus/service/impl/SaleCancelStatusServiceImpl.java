package kr.co.solbipos.sale.marketing.saleCancelStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.saleCancelStatus.service.SaleCancelStatusService;
import kr.co.solbipos.sale.marketing.saleCancelStatus.service.SaleCancelStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleCancelStatusServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 취소현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.31
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("saleCancelStatusService")
@Transactional
public class SaleCancelStatusServiceImpl implements SaleCancelStatusService {

    private final SaleCancelStatusMapper saleCancelStatusMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SaleCancelStatusServiceImpl(SaleCancelStatusMapper saleCancelStatusMapper, PopupMapper popupMapper) {
        this.saleCancelStatusMapper = saleCancelStatusMapper;
        this.popupMapper = popupMapper;
    }

    /** 취소현황 - 전체점포 탭 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleCancelStatusList(SaleCancelStatusVO saleCancelStatusVO, SessionInfoVO sessionInfoVO) {
        saleCancelStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(saleCancelStatusVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleCancelStatusVO.getStoreCd(), 3900));
            saleCancelStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return saleCancelStatusMapper.getSaleCancelStatusList(saleCancelStatusVO);
    }

    /** 취소현황 - 전체점포 탭 상세조회 */
    @Override
    public List<DefaultMap<Object>> getSaleCancelStatusDtlList(SaleCancelStatusVO saleCancelStatusVO, SessionInfoVO sessionInfoVO) {

        saleCancelStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleCancelStatusMapper.getSaleCancelStatusDtlList(saleCancelStatusVO);
    }

    /** 취소현황 - 선택점포 탭 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleCancelStatusStoreList(SaleCancelStatusVO saleCancelStatusVO, SessionInfoVO sessionInfoVO) {
        saleCancelStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return saleCancelStatusMapper.getSaleCancelStatusStoreList(saleCancelStatusVO);
    }
}
