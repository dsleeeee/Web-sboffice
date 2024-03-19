package kr.co.solbipos.store.storeMoms.lsmStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreService;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.impl.LsmStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : LsmStoreServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("lsmStoreService")
public class LsmStoreServiceImpl implements LsmStoreService {
    private final LsmStoreMapper lsmStoreMapper;
    private final MessageService messageService;

    @Autowired
    public LsmStoreServiceImpl(LsmStoreMapper lsmStoreMapper, MessageService messageService) {
        this.lsmStoreMapper = lsmStoreMapper;
        this.messageService = messageService;
    }

    /** 터치키 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return lsmStoreMapper.getLsmStoreList(lsmStoreVO);
    }

    /** 터치키 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return lsmStoreMapper.getLsmStoreExcelList(lsmStoreVO);
    }

    /** 키오스크 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getLsmKioskStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return lsmStoreMapper.getLsmKioskStoreList(lsmStoreVO);
    }

    /** 키오스크 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getLsmKioskStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return lsmStoreMapper.getLsmKioskStoreExcelList(lsmStoreVO);
    }

}