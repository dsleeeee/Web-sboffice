package kr.co.solbipos.store.storeMoms.storeSaleArea.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaService;
import kr.co.solbipos.store.storeMoms.storeSaleArea.service.StoreSaleAreaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreSaleAreaServiceImpl.java
 * @Description : 맘스터치 > 점포관리 > 점포 영업 지역 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.11.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service
public class StoreSaleAreaServiceImpl implements StoreSaleAreaService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreSaleAreaMapper storeSaleAreaMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreSaleAreaServiceImpl(StoreSaleAreaMapper storeSaleAreaMapper, MessageService messageService) {
        this.storeSaleAreaMapper = storeSaleAreaMapper;
        this.messageService = messageService;
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSaleAreaMapper.getStoreList(storeSaleAreaVO);
    }

    /** 지사 조회(콤보박스용) */
    @Override
    public List<DefaultMap<String>> getBranchCombo(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSaleAreaMapper.getBranchCombo(storeSaleAreaVO);
    }

    /** 매장 조회(콤보박스용) */
    @Override
    public List<DefaultMap<String>> getStoreCombo(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSaleAreaMapper.getStoreCombo(storeSaleAreaVO);
    }

    /** 매장 영업지역 조회 */
    @Override
    public DefaultMap<String> getStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSaleAreaMapper.getStoreSaleArea(storeSaleAreaVO);
    }

    /** 매장 영업지역 저장 */
    @Override
    public int saveStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSaleAreaVO.setModDt(dt);
        storeSaleAreaVO.setModId(sessionInfoVO.getUserId());

        result = storeSaleAreaMapper.saveStoreSaleArea(storeSaleAreaVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 서울, 경기 매장 영업지역 조회 */
    @Override
    public List<DefaultMap<String>> getMetropolitanSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO) {

        storeSaleAreaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSaleAreaMapper.getMetropolitanSaleArea(storeSaleAreaVO);
    }
}
