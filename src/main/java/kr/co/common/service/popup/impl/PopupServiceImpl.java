package kr.co.common.service.popup.impl;

import kr.co.common.data.domain.AgencyVO;
import kr.co.common.data.domain.HqOfficeVO;
import kr.co.common.data.domain.VanVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.PopupService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PopupServiceImpl.java
 * @Description : 공통 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class PopupServiceImpl implements PopupService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PopupMapper popupMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PopupServiceImpl(PopupMapper popupMapper, MessageService messageService) {
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 벤사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getVanList(VanVO vanVO) {
        return popupMapper.getVanList(vanVO);
    }

    /** 대리점 목록 조회 */
    @Override
    public List<DefaultMap<String>> getAgencyList(AgencyVO agencyVO) {
        return popupMapper.getAgencyList(agencyVO);
    }

    /** 본사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getHqList(HqOfficeVO hqOfficeVO) {
        return popupMapper.getHqList(hqOfficeVO);
    }

    /** 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO) {
        return popupMapper.getStoreList(storeManageVO);
    }
}
